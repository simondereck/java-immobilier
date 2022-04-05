var imgUrl;
var imageSize = 0;
var dataDetail;
var dataFiles = {};

$(function () {
    let id = $(".annonce-item").attr("data-id");
    let updateId = $(".annonce-item").attr("data-uid");
    $.get("/partner/bussiness/data/annonce/bussiness/detail/"+id,function (data) {
        console.log(data);
        if (data.status==1){
            dataDetail = data;
            imgUrl = data.imgUrl;
            data.annonce.images;
            data.annonce.houseLabel;

            let imageHouse = "<div class='house-images annonce-item-item' data-id='0'>";
            if (data.annonce.cover){
                imageHouse += "<div class='form-group'><label>封面</label>" +
                    "<div class='cover'>" +
                    "<img src='"+data.imgUrl+data.annonce.cover+"'/>" +
                    "<input name='cover' value='"+data.annonce.cover+"' hidden id='cover'/>"+
                    "</div></div>";
            }else{
                imageHouse += "<div class='form-group'><label>封面</label><div class='cover'>" +
                    "<img src='/img/default.jpg'/>" +
                    "<input name='cover' hidden id='cover'/>"+
                    "</div></div>";
            }

            let imgsDiv = "<div class='form-group'><div class='form-group'><label>图片</label></div><div class='annonce-item-imgs'>";
            if (data.annonce.imgsJson){
                let imgs = JSON.parse(data.annonce.imgsJson);
                imageSize = imgs.length;
                for (let i=0;i<imgs.length;i++){
                    imgsDiv += "<div class='img-item'>" +
                        "<img src='"+data.imgUrl+ imgs[i]+ "' />" +
                        "<input name='images["+i+"]' value='"+imgs[i]+"' hidden/>"+
                        "</div>";
                }

            }
            imgsDiv += "</div></div>";
            imageHouse += imgsDiv;

            imageHouse += "<div class='add-image'><img src='/img/add.png' /></div>";

            imageHouse += "</div>";

            $("#annonce-detail").append(imageHouse);


            let houseBase = "<div class='house-base annonce-item-item' data-id='1'>";


            houseBase += "<div class='form-group pays-set location-item'></div>";

            if (data.annonce.area){
                houseBase += "<div class='form-group'>" +
                    "   <label>地区：</label>" +
                    "   <div id='area-div'>" +
                    "       <input type='text' class='form-control search-input' name='area'  id='area' placeholder='地段' value='"+data.annonce.area+"'/>" +
                    "       <input type='text' name='location' hidden='hidden' id='location' value='"+data.annonce.location+"'/>"+
                    "   </div>" +
                    "</div>";
            }else{
                houseBase += "<div class='form-group'>" +
                    "   <label for='area'>地区：</label>" +
                    "   <div id='area-div'>" +
                    "       <input type='text' class='form-control search-input' name='area'  id='area' placeholder='地段' />" +
                    "       <input type='text' name='location' hidden='hidden' id='location' />"+
                    "   </div>" +
                    "</div>";
            }

            houseBase += "<div class='form-group'><label>地址：</label><input name='address' value=\""+data.annonce.address+"\" class='form-control'/></input></div>";

            houseBase += "<div class='form-group'><label>面积：</label>" +
                "<div class='input-group'>" +
                "<input name='size' type='number' class='form-control' aria-describedby='basic-addon1' value='"+data.annonce.size+"'/>" +
                "<div class='input-group-append'>" +
                "<span class='input-group-text' id='basic-addon1'>平米</span>" +
                "</div></div>";

            if(data.annonce.middle){
                houseBase += "<div class='form-group'>" +
                    "<label>层数</label>" +
                    "<div class='form-group'><input name='middle' type='checkbox' checked/><label>中间层</label></div>" +
                    "</div>";
            }else{
                houseBase += "<div class='form-group'>" +
                    "<label>层数</label>" +
                    "<div class='form-group'><input name='middle' type='checkbox' checked/><label>中间层</label></div>" +
                    "</div>";
            }

            houseBase += "<div class='form-group'><label>价格：</label><div><input name='price' value='"+data.annonce.price+"' class='form-control'/></div></div>";
            houseBase += "</div>";

            $("#annonce-detail").append(houseBase);


            let houseDetail = "<div class='house-detail annonce-item-item' data-id='2'>";


            houseDetail += "<div class='form-group'>" +
                "<label>类别:</label>";
            houseDetail += "<div class='form-group'>" +
                "<select name='types' class='form-control'>";
            for (let key in data.types){
                if (data.annonce.types==key){
                    houseDetail += "<option value='"+key+"' selected>"+data.types[key]+"</option>";
                }else{
                    houseDetail += "<option value='"+key+"'>"+data.types[key]+"</option>";
                }
            }
            houseDetail += "</select></div>";


            houseDetail += "<div class='form-group'>" +
                "<label>房源类型:</label>";
            houseDetail += "<div class='form-group'>" +
                "<select name='houseType' class='form-control'>";

            for (let key in data.houseType){
                if (data.annonce.houseType==key){
                    houseDetail += "<option value='"+key+"' selected>"+data.houseType[key]+"</option>";
                }else{
                    houseDetail += "<option value='"+key+"'>"+data.houseType[key]+"</option>";
                }
            }

            houseDetail += "</select></div>";

            houseDetail += "<div class='form-group'>" +
                "<label>房屋类型:</label>";
            houseDetail += "<div class='form-group'>" +
                "<select name='roomType' class='form-control'>";
            for (let key in data.roomType){
                if (data.annonce.roomType==key){
                    houseDetail += "<option value='"+key+"' selected>"+data.roomType[key]+"</option>";
                }else{
                    houseDetail += "<option value='"+key+"'>"+data.roomType[key]+"</option>";
                }
            }
            houseDetail += "</select></div>"


            if (data.annonce.lift){
                houseDetail += "<div class='form-group'><label>电梯</label><input type='checkbox'  checked name='lift'/></div>";
            }else{
                houseDetail += "<div class='form-group'><label>电梯</label><input type='checkbox' name='lift' /></div>";
            }


            if (data.annonce.balcon){
                houseDetail += "<div class='form-group'><label>阳台</label><input type='checkbox'  checked name='balcon'/></div>";
                houseDetail += "<div class='input-group form-group'>" +
                    "<input name='balconSize' type='number' class='form-control' aria-describedby='basic-addon2' value='"+data.annonce.balconSize+"' />" +
                    "<div class='input-group-append'>" +
                    "   <span class='input-group-text' id='basic-addon2'>平米</span>" +
                    "</div> </div>";
            }else{
                houseDetail += "<div class='form-group'><label>阳台</label><input type='checkbox'  name='balcon'/></div>";
                houseDetail += "<div class='input-group form-group'>" +
                    "<input name='balconSize' type='number' class='form-control' aria-describedby='basic-addon2' />" +
                    "<div class='input-group-append'>" +
                    "   <span class='input-group-text' id='basic-addon2'>平米</span>" +
                    "</div> </div>";
            }

            if (data.annonce.cave){
                houseDetail += "<div class='form-group'><label>地窖</label><input type='checkbox'  checked name='cave'/></div>";
                houseDetail += "<div class='input-group form-group'>" +
                    "<input name='caveSize' type='number' class='form-control' aria-describedby='basic-addon3' value='"+data.annonce.caveSize+"'/>" +
                    "<div class='input-group-append'>" +
                    "   <span class='input-group-text' id='basic-addon3'>平米</span>" +
                    "</div> </div>";
            }else{
                houseDetail += "<div class='form-group'><label>地窖</label><input type='checkbox' name='cave' /></div>";
                houseDetail += "<div class='input-group form-group'>" +
                    "<input name='caveSize' type='number' class='form-control' aria-describedby='basic-addon3' />" +
                    "<div class='input-group-append'>" +
                    "   <span class='input-group-text' id='basic-addon3'>平米</span>" +
                    "</div> </div>";
            }

            if (data.annonce.parking){
                houseDetail += "<div class='form-group'><label>停车场</label><input type='checkbox'  checked name='parking'/></div>";
            }else{
                houseDetail += "<div class='form-group'><label>停车场</label><input type='checkbox' name='parking'  /></div>";
            }
            houseDetail += "</div>";


            $("#annonce-detail").append(houseDetail);




            let houseDescription = "<div class='house-description annonce-item-item' data-id='3'>";

            houseDescription += "<div class='form-group'>" +
                "<label>房源标签 *</label>" +
                "<div>" +
                "   <select name='houseLabel' class='form-control'>";

            let arrayHouses = ["个人","房管","中介","加盟商","平台自营"];

            for (let i in arrayHouses){
                if (data.annonce.houseLabel==i){
                    houseDescription += "<option value='"+i+"' selected>"+arrayHouses[i]+"</option>";
                }else{
                    if (i>=3 && data.model.type!=i){
                        houseDescription += "<option value='"+i+"' disabled>"+arrayHouses[i]+"</option>";
                    }else if (i>=3 && data.model.type==i){
                        houseDescription += "<option value='"+i+"' >"+arrayHouses[i]+"</option>";
                    }else{
                        houseDescription += "<option value='"+i+"' >"+arrayHouses[i]+"</option>";
                    }
                }
            }



            houseDescription += "   </select>" +
                "</div>" +
                "</div>"

            houseDescription += "<div class='form-group'>" +
                "<label>地铁，公交*</label>" +
                "<div><input name='trans' class='form-control' value=\""+data.annonce.trans+"\"/></div>"+
                "</div>";
            houseDescription += "<div class='form-group'>" +
                "<label>房屋描述 *</label>" +
                "<textarea class='form-control description' name='description'>"+data.annonce.description+"</textarea>" +
                "</div>";


            houseDescription += "</div>";

            $("#annonce-detail").append(houseDescription);


            let contactDetail = "<div class='contact-deatail annonce-item-item' data-id='4'>";
            contactDetail += "<div class='form-group'>";
            contactDetail += "<select class='form-control' name='sex'>";



            if (data.annonce.sex==0){
                contactDetail += "<option value='0' selected>先生</option>";
                contactDetail += "<option value='1'>女生</option>";
            }else{
                contactDetail += "<option value='0'>先生</option>";
                contactDetail += "<option value='1' selected>女生</option>";
            }
            contactDetail += "</select></div>";


            contactDetail += "<div class='form-group'>" +
                "<label>* 姓：</label>" +
                "<div><input name='nom' class='form-control' value='"+data.annonce.nom+"'/></div>"+
                "</div>";


            contactDetail += "<div class='form-group'>" +
                "<label>* 名：</label>" +
                "<div><input name='prenom' value='"+data.annonce.prenom+"' class='form-control'/></div>" +
                "</div>";


            contactDetail += "<div class='form-group'>" +
                "<label>微信：</label>" +
                "<div><input name='wechat' value='"+data.annonce.wechat+"' class='form-control'/></div>" +
                "</div>";


            contactDetail += "<div class='form-group'>" +
                "<label>邮箱：</label>" +
                "<div><input name='email' value='"+data.annonce.email+"' class='form-control'/></div>" +
                "</div>";

            contactDetail += "<div class='form-group'>" +
                "<label>电话：</label>" +
                "<div><input name='telephone' value='"+data.annonce.telephone+"' class='form-control' /></div>" +
                "</div>";


            contactDetail += "</div>";

            $("#annonce-detail").append(contactDetail);
            setView();
            setPays();
        }else {
            alert("你请求的房源信息不存在");
        }
    });


    $(".annonce-nav span").each(function (key,item) {
        if ($(item).attr("data-id")==updateId){
            $(item).addClass("active");
        }
    });

    function setView(){
        $(".annonce-item-item").each(function (key,item) {
            if ($(item).attr("data-id")==updateId){
                $(item).show();
            }else{
                $(item).hide();
            }
        });
    }

    function setPays(){
        $.get("/data/pays",function (data) {
            if (data){
                let dataString  = '<label for="pays" class="control-label">国籍</label>';
                dataString += '<div><select name="pay" id="pays" class="form-control">';
                data.forEach(function (item,key) {
                    if (dataDetail.annonce.pay&&dataDetail.annonce.pay==item.id){
                        dataString += '<option value="'+item.id+'" selected>'+item.name+'</option>';
                    }else{
                        dataString += '<option value="'+item.id+'">'+item.name+'</option>';
                    }
                });
                dataString += '</select></div>';
                $(".pays-set").html(dataString);
            }
        });
    }

    $(".annonce-nav span").click(function () {
        let id = $(this).attr("data-id");
        $(".annonce-nav span").each(function (key,item) {
            if($(item).attr("data-id")==id){
                $(item).addClass("active");
            }else{
                $(item).removeClass("active");
            }
        });
        $(".annonce-item-item").each(function (key,item) {
            if($(item).attr("data-id")==id){
                $(item).show();
            }else{
                $(item).hide();
            }
        });
    });

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
        "        <button class='btn btn-info' id='hwwd-image-update-spin'>旋转</button>" +
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

                // if (roate==0){
                //
                // }else if (roate==1){
                //
                // }else if (roate==2){
                //
                // }

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
                $(".annonce-item-imgs").html("");
                console.log(data);
                let count = 0;

                if (dataDetail.annonce.imgsJson){
                    let imgs = JSON.parse(dataDetail.annonce.imgsJson);
                    let $imgsDiv = "";
                    for (let i=0;i<imgs.length;i++){
                        count++;
                        $imgsDiv = "<div class='img-item'>" +
                            "<img src='"+dataDetail.imgUrl+ imgs[i]+ "' />" +
                            "<input name='images["+count+"]' value='"+imgs[i]+"' hidden/>"+
                            "</div>";
                        $(".annonce-item-imgs").append($imgsDiv);

                        console.log("count++++",count);
                    }

                }
                for(var k in data){
                    count++;
                    let $image = "";
                    if(data[k].hasOwnProperty("relativePath")){
                        $image = "<div class='img-item'>" +
                            "<img src='"+imgUrl+ data[k].relativePath+ "' />" +
                            "<input type='hidden' name='images["+(count)+"]' value='" + data[k].relativePath +
                            "' data-filename='"+data[k].fileName+"'/>"+
                            "</div>";
                        $(".annonce-item-imgs").append($image);
                    }
                    console.log("count++++",count);
                }
            },

            error: function(err) {
                $("#hwwd-upload-progress").remove();
                alert("图片上传失败");
                console.log(err);
            }
        });
    }
});

$("html").on("keyup","#area",function (){
    let val = $(this).val();
    var data = {keyword:val};
    $.get("/user/location/keywords",data,function (result){
        console.log(result);
        if (result) {
            villes = result;
            var $table = "<table id='search-table'>" +
                " <tr class='header'>" +
                "<th style='width:30%' >邮编</th>" +
                "<th style='width:30%' >城市</th>" +
                "<th style='width:30%' >省</th></tr>";

            villes.forEach(function (ville, key) {
                $table += "<tr class='select-ville' data-id='"+ville.id+"' data-key='"+key+"'>" +
                    "<td>"+ville.postcode+"</td>"+
                    "<td>"+ville.name+"</td>"+
                    "<td>"+ville.code+"</td>"+
                    "</tr>"
            });
            $table += "</table>";
            let old = $("#search-table");
            if (old){
                old.remove();
            }
            $("#area-div").append($table);
        }
    });
});

$(".form-horizontal").on('click',".select-ville",function (){
    let key = $(this).attr("data-key");
    let id = $(this).attr("data-id");
    $("input[name=location]").val(id);
    let value = villes[key].postcode + " -- " + villes[key].name + " -- " + villes[key].code;
    $("input[name=area]").val(value);
    $("#search-table").remove();
});
