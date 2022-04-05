$(function (){

    $(".imm-item").click(function (){
        let checked = $(this).hasClass("active");
        if (checked){
            $(this).removeClass("active");
            $(this).find("input").remove();
        }else{
            $(this).addClass("active");
            let id = $(this).attr("data-id");
            let $input = "<input name='envir["+id+"]' type='hidden' value='"+id+"'/>";
            $(this).append($input);
        }
    });

    $(".base-item").click(function (){
       let checked = $(this).hasClass("active");
       if (checked){
           $(this).removeClass("active");
           $(this).find("input").remove();
       }else{
           $(this).addClass("active");
           let id = $(this).attr("data-id");
           let $input = "<input name='base["+id+"]' type='hidden' value='"+id+"'/>";
           $(this).append($input);
       }
    });

    $(".energy-item-group").click(function (){
        let checked = $(this).find("input[type=radio]").prop("checked");
        if (checked){
            $(this).find("input[type=radio]").prop("checked",false);
        }else{
            $(this).find("input[type=radio]").prop("checked",true);
        }
    });

    var villes;

    $("#area").keyup(function (){
        let val = $(this).val();
        var data = {keyword:val};
        $.get("/user/location/keywords",data,function (result){
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


    $(".location-item").on('click',".select-ville",function (){
        let key = $(this).attr("data-key");
        let id = $(this).attr("data-id");
        $("input[name=location]").val(id);
        let value = villes[key].postcode + " -- " + villes[key].name + " -- " + villes[key].code;
        $("input[name=area]").val(value);
        $("#search-table").remove();
    });

    var loyer = 0;
    var charges = 0;

    $("input[name=loyer]").change(function (){
        loyer = $(this).val();
        $(".price").text(parseInt(loyer)+parseInt(charges));
    });

    $("input[name=charges]").change(function (){
        charges = $(this).val();
        $(".price").text(parseInt(loyer)+parseInt(charges));
    });



    var input = document.getElementById("pic");
    var result;
    var dataArr = []; // 储存所选图片的结果(文件名和base64数据)
    var dataFiles = [];
    // var fd = new FormData($('#form')[0]);  //FormData方式发送请求 $('#form')[0]
    var oSelect = document.getElementById("select");
    var oAdd = document.getElementById("add");
    var oSubmit = document.getElementById("submit");
    var oInput = document.getElementById("pic");

    if(typeof FileReader==='undefined'){
        alert("抱歉，你的浏览器不支持 FileReader");
        input.setAttribute('disabled','disabled');
    }else{
        input.addEventListener('change',readFile,false);
    }

    function readFile(){
        fd = new FormData();
        var iLen = this.files.length;
        for(var i=0;i<iLen;i++){
            if (!input['value'].match(/.jpg|.gif|.png|.jpeg|.bmp/i)){　　//判断上传文件格式
                return alert("上传的图片格式不正确，请重新选择");
            }
            var reader = new FileReader();
            dataFiles.push(this.files[i])
            // fd.append(i,this.files[i]);

            reader.readAsDataURL(this.files[i]);  //转成base64
            reader.fileName = this.files[i].name;

            reader.onload = function(e){
                var imgMsg = {
                    name : this.fileName,//获取文件名
                    base64 : this.result   //reader.readAsDataURL方法执行完后，base64数据储存在reader.result里
                }
                dataArr.push(imgMsg);
                result = '<div class="image-item">' +
                    '<div class="image-item-title"><text class="delete">x</text></div>' +
                    '<div class="result"><img class="subPic" src="'+this.result+'" alt="'+this.fileName+'"/></div>' +
                    '</div>';
                var div = document.createElement('div');
                div.innerHTML = result;
                div['className'] = 'float';
                document.getElementById('images').appendChild(div);  　　//插入dom树
                var img = div.getElementsByTagName('img')[0];
                img.onload = function(){
                    var nowHeight = ReSizePic(this); //设置图片大小
                    this.parentNode.style.display = 'block';
                    var oParent = this.parentNode;
                    if(nowHeight){
                        oParent.style.paddingTop = (oParent.offsetHeight - nowHeight)/2 + 'px';
                    }
                }
                div.onclick = function(){
                    $(this).remove();// 在页面中删除该图片元素
                    dataArr.forEach(function (item,key) {
                        if (item.name==imgMsg.name){
                            delete dataArr[key];
                            delete dataFiles[key];
                            return;
                        }
                    });
                    dataFiles.filter(item => item!==undefined);
                    dataArr.filter(item => item !== undefined);
                }
            }
        }
    }


    oSelect.onclick=function(){
        oInput.value = "";   // 先将oInput值清空，否则选择图片与上次相同时change事件不会触发
        //清空已选图片
        $('.float').remove();
        dataArr = [];
        oInput.click();
    }


    oAdd.onclick=function(){
        oInput.value = "";   // 先将oInput值清空，否则选择图片与上次相同时change事件不会触发
        oInput.click();
    }


    oSubmit.onclick=function(){
        if(!dataArr.length){
            return alert('请先选择文件');
        }
        send();
    }


    function ReSizePic(ThisPic) {
        var RePicWidth = 360; //这里修改为您想显示的宽度值

        var TrueWidth = ThisPic.width; //图片实际宽度
        var TrueHeight = ThisPic.height; //图片实际高度

        if(TrueWidth>TrueHeight){
            //宽大于高
            var reWidth = RePicWidth;
            ThisPic.width = reWidth;
            //垂直居中
            var nowHeight = TrueHeight * (reWidth/TrueWidth);
            return nowHeight;  //将图片修改后的高度返回，供垂直居中用
        }else{
            //宽小于高
            var reHeight = RePicWidth;
            ThisPic.height = reHeight;
        }
    }

    function send(){
        // dataFiles.forEach(function (item,key){
        //     console.log(item);
        // });
        var formdata =  new FormData();
        for (var i = 0; i<dataFiles.length; i++) {
            if (dataFiles[i]){
                formdata.append('uploadFiles', dataFiles[i]);
            }
        }
        $.ajax({
            url: 'uploadImage',
            type: 'post',
            data: formdata,
            processData: false,
            contentType: false,
            success: function(data) {
                alert(data.length+"个上传结果");

                for(var k in data){
                    var num=Number(k)+1;
                    alert("第"+num+"张上传结果："+data[k].result_msg);
                    if(data[k].hasOwnProperty("relativePath")){
                        // alert("第"+num+"张相对路径："+data[k].relativePath);
                        $image = "<input type='hidden' name='images["+k+"]' value='" +
                            data[k].relativePath +
                            "'/>";
                        $("#images").append($image);
                    }
                }
                // submitData();
            },
            error: function(err) {
                alert("上传失败");
            }
        });
    }


    // function submitData(){
    //     $("#addSource").submit();
    //
    // }
});