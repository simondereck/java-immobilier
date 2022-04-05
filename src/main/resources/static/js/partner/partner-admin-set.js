$(function () {

    let CROPPER = null    //创建一个cropper的全局对象

    $(".select-img").click(function () {
        $("#imgReader").click();
        if(CROPPER){
            CROPPER.destroy();
        }
    });

    $("#imgReader").change(function () {
        let file = $(this).get(0).files[0];

        if (file){
            readFile(file);
            //readAsDataURL方法可以将File对象转化为data:URL格式的字符串（base64编码）
        }

    });
    var progressDiv = "<div id='hwwd-upload-progress'><progress max='100' value='0' id='hwwd-progress'>0</progress></div>";

    $("html").on('click','.btn-upload',function () {
        $("#modal .close").click();
        // Modal('hide');
        CROPPER.getCroppedCanvas({
            maxWidth: 4096,
            maxHeight: 4096,
            fillColor: '#fff',
            imageSmoothingEnabled: true,
            imageSmoothingQuality: 'high',
        }).toBlob((blob) => {

            const formdata = new FormData();
            // 第三个参数为文件名，可选填.
            let file = new File([blob], "headimage.png");
            $('html').append(progressDiv);
            compressImage(file,function (success) {
                console.log("headimagesize :",success.size);
                formdata.append("uploadFiles",success,success.fileName);
                $.ajax({
                    xhr: function() {
                        var xhr = new window.XMLHttpRequest();
                        xhr.upload.addEventListener("progress", function(evt) {
                            if (evt.lengthComputable) {
                                var percentComplete = evt.loaded / evt.total;
                                percentComplete = parseInt(percentComplete * 100);
                                $("#hwwd-progress").val(percentComplete);
                                if (percentComplete === 100) {
                                    $("#hwwd-upload-progress").remove();
                                    alert("图片上传结束");
                                    // $(".hwwd-image-background").toggle();
                                }
                            }
                        }, false);

                        return xhr;
                    },
                    url:'/partner/admin/uploadImage',
                    type: 'post',
                    data: formdata,
                    processData: false,
                    contentType: false,
                    success: function(data) {
                        console.log("upload image",data);
                        let id = $(".user-info").attr("data-id");
                        for(var k in data){
                            if(data[k].hasOwnProperty("relativePath")){
                                $("#head-img").attr("src","/partners/"+id+"/"+data[k].relativePath);
                                $("#cover").val(data[k].relativePath);
                            }
                        }
                        $(".btn-set-update").click();
                    },
                    error: function(err) {
                        $("#hwwd-upload-progress").remove();
                        alert("图片上传失败");
                    }
                });
            },function (error){
                console.error(error);
            });
        });

    });


    function compressImage(file,success,error){
        let name = file.name;
        let reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = e =>{
            let src = e.target.result;
            let img = new Image();
            img.src = src;
            img.onload = e =>{
                let w = img.width;
                let h = img.height;
                // let quality =  0.8;

                let canvas = document.createElement('canvas');
                let ctx = canvas.getContext('2d');

                let anw = document.createAttribute('width');
                anw.nodeValue = w;
                let anh = document.createAttribute('height');
                anh.nodeValue = h;
                canvas.setAttributeNode(anw);
                canvas.setAttributeNode(anh);

                ctx.fillStyle = "#fff";
                ctx.drawImage(img,0,0,w,h);

                let quality = caculateSize(file,canvas,src,1);

                let base64 = canvas.toDataURL('image/jpeg',quality);

                let bytes = window.atob(base64.split(',')[1]);

                let ab  = new ArrayBuffer(bytes.length);
                let ia = new Uint8Array(ab);

                for (let i=0;i<bytes.length;i++){
                    ia[i] = bytes.charCodeAt(i);
                }
                file = new Blob([ab],{type:'image/jpeg'});
                file.fileName = name;
                file.lastModifiedDate = new Date();
                success(file);
            }
            img.onerror = e =>{
                error(e);
            }
        }
        reader.onerror = e =>{
            error(e);
        }
    }

    function readFile(file){

        let reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = (e)=>{
            let dataURL = reader.result;
            //将img的src改为刚上传的文件的转换格式
            document.querySelector('#cropImg').src = dataURL;

            const image = document.getElementById('cropImg');

            //创建cropper实例-----------------------------------------
            CROPPER = new Cropper(image, {
                aspectRatio: 16 / 16,
                viewMode:0,
                minContainerWidth:400,
                minContainerHeight:300,
                dragMode:'move',
                preview:[ document.querySelector('.previewBox'),
                    document.querySelector('.previewBoxRound')]
            });
        }

    }

    function caculateSize(file,canvas,src,quality) {
        quality *= 0.8
        let base64 = canvas.toDataURL('image/jpeg',quality);
        let size = ((base64.length/1024).toFixed(2)/(src.length/1024).toFixed(2)).toFixed(2)*file.size;
        if (size/(1000*1000)>=1){
            return caculateSize(file,canvas,src,quality);
        }
        return quality;
    }

});