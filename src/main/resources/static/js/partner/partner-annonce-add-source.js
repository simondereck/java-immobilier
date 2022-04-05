$(function () {
    var imgUrl;
    var imageSize = 0;
    var dataFiles = {};

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }


    if (getCookie("user")){
        let user = getCookie("user");
        imgUrl = "/partners/"+ user.id +"/";
    }else{
        $.get("/partner/info",function (info) {
            if (info){
                imgUrl = "/partners/"+ info.id +"/";
            }
        });
    }



    $("html").on("click",".update-button",function () {
        let href = $(this).attr("data-href");
        window.location.assign(href);
    });


    $("html").on("click",".img-item",function () {
        let src = $(this).children("img").attr("src");
        hwwdImageEditor(this,src);
    });


    let uploadImageView = "<div id='hwwd-image-update'>" +
        "    <div id='hwwd-image-update-close'>x</div>" +
        "    <div id='hwwd-image-update-content'>" +
        "    </div>" +
        "    <div id='hwwd-image-update-button-group'>" +
        "        <button class='btn btn-info' id='hwwd-image-update-spin'>关闭</button>" +
        "        <button class='btn btn-success' id='hwwd-image-update-upload'>上传</button>" +
        "    </div>" +
        "</div>";

    $("html").append(uploadImageView);

    let fileInput = "<input type='file' name='files' multiple id='hwwd-input-update-files' hidden/>";
    $("html").append(fileInput);

    $("html").on("click",".add-image",function () {
        $("#hwwd-input-update-files").val("");
        $("#hwwd-input-update-files").click();
    });

    $("html").on("click","#hwwd-image-update-close",function () {
        $("#hwwd-image-update").toggle('slow');
    });
    $("html").on("click","#hwwd-image-update-spin",function () {
        $("#hwwd-image-update-canvas").css("transform","rotate(90deg)");
    });

    $("html").on('click','#hwwd-image-update-upload',function () {
        uploadImage();
    });



    $("#hwwd-input-update-files").change(function () {

        let files = $(this).get(0).files;
        let length = files.length;
        if (files.length>9){
            alert("您只能上传9张图片！");
            length = 9;
        }

        $("#hwwd-image-update").toggle('slow');

        for (let i=0;i<length;i++){
            let file = files[i];
            if (file.type){
                let fileType = file.type.split("/");
                if (fileType[1]){
                    if (fileType[1].match(/jpg|gif|png|jpeg/i)){
                        compressImage(file,function (success) {
                            readFile(success);
                            // $("#hwwd-image-update").toggle('slow');
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

                let canvas = document.createElement('canvas');
                let ctx = canvas.getContext('2d');

                let anw = document.createAttribute('width');
                let anh = document.createAttribute('height');


                anh.nodeValue = h;
                anw.nodeValue = w;
                //旋转180
                // anh.nodeValue = w;
                // anw.nodeValue = h;
                canvas.setAttributeNode(anw);
                canvas.setAttributeNode(anh);

                // const angle = 90 * Math.PI / 180;
                // ctx.rotate(angle);

                ctx.fillStyle = "#fff";
                // ctx.drawImage(img,0,0,h,w);
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
    }function compressImage(file,success,error){
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

                let canvas = document.createElement('canvas');
                let ctx = canvas.getContext('2d');

                let anw = document.createAttribute('width');
                let anh = document.createAttribute('height');

                anh.nodeValue = h;
                anw.nodeValue = w;
                //旋转180
                // anh.nodeValue = w;
                // anw.nodeValue = h;
                canvas.setAttributeNode(anw);
                canvas.setAttributeNode(anh);

                // const angle = 90 * Math.PI / 180;
                // ctx.rotate(angle);

                ctx.fillStyle = "#fff";
                // ctx.drawImage(img,0,0,h,w);
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

    function caculateSize(file,canvas,src,quality) {
        quality *= 0.8
        let base64 = canvas.toDataURL('image/jpeg',quality);
        let size = ((base64.length/1024).toFixed(2)/(src.length/1024).toFixed(2)).toFixed(2)*file.size;
        if (size/(1000*1000)>=1){
            return caculateSize(file,canvas,src,quality);
        }
        return quality;
    }

    function readFile(file){
        var reader = new FileReader();
        dataFiles[file.fileName] = file;
        reader.readAsDataURL(file);  //转成base64
        reader.fileName = file.fileName;
        reader.onload = function(e){
            var imgMsg = {
                name : this.fileName,
                base64 : this.result
            }
            dataImageItem = '<div class="hwwd-image-item">' +
                '<div class="image-item-title" data-id="'+this.fileName+'"><text>'+this.fileName+'</text><button class="btn btn-link" type="button">删除</button></div>'+
                '<div class="image-item-image"><img class="subPic" src="'+this.result+'" /> </div>'+
                '</div>';

            $("#hwwd-image-update-content").append(dataImageItem);
            // $("#hwwd-image-update-canvas").attr("src",this.result);
        }

    }

    function uploadImage(){
        var progressDiv = "<div id='hwwd-upload-progress'><progress max='100' value='0' id='hwwd-progress'>0</progress></div>";
        var formdata =  new FormData();
        if (Object.keys(dataFiles).length<=0){
            alert("请先选择图片");
            return;
        }
        $.each( dataFiles, function( key, value ) {
            formdata.append('uploadFiles',value,value.fileName);
        });

        $('html').append(progressDiv);
        $('body').addClass("hwwd-process-show");
        $('html').addClass("hwwd-process-show");
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
                            $("#hwwd-image-update").toggle();
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
                $('html').removeClass("hwwd-process-show");
                $('body').removeClass("hwwd-process-show");
                $(".annonce-item-imgs").html("");
                console.log(data);
                let count = 0;

                for(var k in data){
                    count++;
                    let $image = "";
                    if(data[k].hasOwnProperty("relativePath")){
                        $image = "<div class='img-item'>" +
                            "<img src='"+imgUrl+ data[k].relativePath+ "' />" +
                            "<input type='hidden' name='images["+(count)+"]' value='" + data[k].relativePath +
                            "' data-filename='"+data[k].fileName+"' class='img-item-input'/>"+
                            "</div>";
                        $(".annonce-item-imgs").append($image);
                    }
                    console.log("count++++",count);
                }
                $(".annonce-item-imgs").append("<div class='add-image'><img src='/img/add.png' /></div>");
            },

            error: function(err) {
                $("#hwwd-upload-progress").remove();
                alert("图片上传失败");
                console.log(err);
                $('html').removeClass("hwwd-process-show");
            }
        });
    }
});