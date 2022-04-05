$(function () {
    let $workSpace = $("#hwwd-upload-images");
    if ($workSpace){
        let items = "<button class='btn btn-success hwwd-upload-button' type='button'>上传图片</button>";
        let fileInput = "<input type='file' name='files' multiple id='hwwd-input-files' hidden/>";
        let imageSets = "<div id='hwwd-images'><input name='cover' id='cover' hidden='hidden'/></div>";
        let displayImage = "<div id='hwwd-images-display'><div class='hwwd-images-set'></div></div>";
        $workSpace.append(items);
        $workSpace.append(fileInput);
        $workSpace.append(imageSets);
        $workSpace.append(displayImage);
        let contentDiv;
        var dataFiles = {};
        var progressDiv = "<div id='hwwd-upload-progress'><progress max='100' value='0' id='hwwd-progress'>0</progress></div>";
        $("html").on("click",".hwwd-upload-button",function () {
            if (!contentDiv){
                contentDiv = "<div class='hwwd-image-background'>" +
                    "<div class='hwwd-image-contents'>" +
                    "<div class='hwwd-image-close'><button class='btn btn-link btn-lg'>关闭</button></div>"+
                    "<div class='hwwd-image-contains'>" +
                    "<div class='hwwd-images-set'></div>"+
                    "<div class='hwwd-button-groups'>" +
                    "<button class='btn btn-info btn-lg hwwd-select-img' type='button'>选择图片</button>"+
                    "<button class='btn btn-success btn-lg hwwd-upload-img' type='button'>上传图片</button>"+
                    "</div>"+
                    "</div>" +
                    "</div>";
                $("html").append(contentDiv);
            }else{
                $(".hwwd-image-background").toggle();
            }
        });

        $("html").on("click",".hwwd-image-close",function () {
            console.log($("#hwwd-images").children().length);
            let size = $("#hwwd-images").children().length;
            let dataSize = Object.keys(dataFiles).length;
            if (dataSize>size){
                let exitUpload = confirm("你还有图片未上传,确认退出吗？");
                if (exitUpload){
                    $(".hwwd-image-background").toggle();
                }
            }else{
                $(".hwwd-image-background").toggle();
            }
        });

        $("html").on('click',".hwwd-select-img",function (){
            if (Object.keys(dataFiles).length>=9){
                alert("您只能上传9张图片！");
                return;
            }
            $("#hwwd-input-files").click();
        });

        $('html').on('click','.image-item-title',function (){
            let name = $(this).attr('data-id');
            delete dataFiles[name];
            $(".image-item-title").each(function (key,item) {
                if ($(item).attr("data-id")==name){
                    $(item).parent(".hwwd-image-item").remove();
                }
            });
            let images = $("#hwwd-images").children("input");
            if (images){
                images.each(function (key,item) {
                    if ($(item).attr("data-filename")==name){
                        $(item).remove();
                    }
                });
            }
        });

        $("html").on('click','.hwwd-upload-img',function () {
            var formdata =  new FormData();
            if (Object.keys(dataFiles).length<=0){
                alert("请先选择图片");
                return;
            }
            $.each( dataFiles, function( key, value ) {
                formdata.append('uploadFiles',value,value.fileName);
            });

            $('html').append(progressDiv);
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
                                $(".hwwd-image-background").toggle();
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
                    for(var k in data){
                        if(data[k].hasOwnProperty("relativePath")){
                            $image = "<input type='hidden' name='images["+k+"]' value='" +
                                data[k].relativePath +
                                "' data-filename='"+data[k].fileName+"'/>";
                            $("#hwwd-images").append($image);

                        }
                    }
                },
                error: function(err) {
                    $("#hwwd-upload-progress").remove();
                    alert("图片上传失败");
                }
            });
        });


        $("#hwwd-input-files").change(function () {
            let length =  $(this).get(0).files.length;
            if (length>9){
                alert("您只能上传9张图片！");
                length = 9;
            }

            for (var i=0;i<length;i++){
                let file = $(this).get(0).files[i];
                if (file.type){
                    let fileType = file.type.split("/");
                    if (fileType[1]){
                        if (fileType[1].match(/jpg|gif|png|jpeg/i)){
                            compressImage(file,function (success) {
                                readFile(success);
                            },function (error){
                                console.error(error);
                            });
                        }else{
                            alert("图片格式不正确，只能上传 jpg,gif,png,jpeg 格式的图片");
                        }
                    }
                }
            }
        });

        $("html").on("click",".image-item-image",function () {
            let setCover = confirm("设置为封面？");
            if (setCover){
                let id = $(this).parents(".hwwd-image-item").children(".image-item-title").attr("data-id");
                let images = $("#hwwd-images").children("input");
                if (images){
                    images.each(function (key,item) {
                        if ($(item).attr("data-filename")==id){
                            let coverUrl = $(item).val();
                            $("#cover").val(coverUrl);
                        }
                    });
                }
            }
        });

    }

    function readFile(file){
        var reader = new FileReader();
        dataFiles[file.fileName] = file;
        reader.readAsDataURL(file);  //转成base64
        reader.fileName = file.fileName;
        reader.onload = function(e){
            var imgMsg = {
                name : this.fileName,//获取文件名
                base64 : this.result   //reader.readAsDataURL方法执行完后，base64数据储存在reader.result里
            }

            dataImageItem = '<div class="hwwd-image-item">' +
                '<div class="image-item-title" data-id="'+this.fileName+'"><text>'+this.fileName+'</text><button class="btn btn-link" type="button">删除</button></div>'+
                '<div class="image-item-image"><img class="subPic" src="'+this.result+'" /> </div>'+
                '</div>';

            $(".hwwd-images-set").append(dataImageItem);

        }

    }


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
                let size = ((base64.length/1024).toFixed(2)/(src.length/1024).toFixed(2)).toFixed(2)*file.size;

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
                console.log(file.size/(1024*1024));

            }
            img.onerror = e =>{
                error(e);
            }
        }
        reader.onerror = e =>{
            error(e);
        }
    }


    function caculateSize(file,canvas,src,quality) {
        quality *= 0.8
        let base64 = canvas.toDataURL('image/jpeg',quality);
        let size = ((base64.length/1024).toFixed(2)/(src.length/1024).toFixed(2)).toFixed(2)*file.size;
        if (size/(1024*1024)>=1){
            return caculateSize(file,canvas,src,quality);
        }
        return quality;
    }


});

