$(function () {

    $.get("/partner/detail/data",function (partner) {
        if (partner){
            if (!partner.cover){
                alert("你还没有头像，暂时不能发布买卖房源");
                throw new Error("NO HEAD IMAGE");
            }
        }
    });

    $('.search-input').focus(function () {
        $('.search-input').attr("autocomplete","off")
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


    $.get("/data/pays",function (data) {
        if (data){
            let dataString  = '<label for="pays" class="control-label">国家</label>';
            dataString += '<div><select name="pay" id="pays" class="form-control">';
            data.forEach(function (item,key) {
                dataString += '<option value="'+item.id+'">'+item.name+'</option>';
            })
            dataString += '</select></div>';
            $(".pays-set").html(dataString);
        }
    });

    $(".select-item").each(function (key,item) {
        if ($(item).attr("data-id")==0){
            $(item).addClass("active");
        }
    });

    $(".selects").on("click",".select-item",function () {
        let parents = $(this).parents(".selects");
        let id = $(this).attr("data-id");
        if (parents){
            let name = parents.attr("data-name");
            let children = parents.children(".select-item");
            children.each(function (key,item){
                if($(item).attr("data-id")==id){
                    $(item).addClass("active");
                    $("input[name="+name+"]").val(id);
                }else{
                    $(item).removeClass("active");
                }
            });
        }
    });

    $("select[name=types]").change(function () {
        let val = $(this).val();
        $(".house-type").each(function (key,item) {
            if ($(item).attr("data-type")==val){
                $(item).show();
            } else{
                $(item).hide();
            }
            //todo set house type
        });
    });

    $(".energy-item-group").click(function (){
        let checked = $(this).find("input[type=radio]").prop("checked");
        if (checked){
            $(this).find("input[type=radio]").prop("checked",false);
        }else{
            $(this).find("input[type=radio]").prop("checked",true);
        }
    });

    $(".next-step").click(function () {
        let parent = $(this).parents(".steps");
        let id = parent.attr("data-id");
        let flag = false;
        switch (parseInt(id)) {
            case 1:
                flag = stepOne();
                break;
            case 2:
                flag = stepTwo();
                break;
            case 3:
                flag = stepThree();
                break;
            case 4:
                flag = true;
                break;
            case 5:
                flag = stepFive();
                if (flag){
                    $("#postAnnonce").submit();
                }
                break;
        }

        if (flag){
            $(parent).toggle();
            $(".step-"+(parseInt(id)+1)).toggle();
            $("#step-progress").attr("aria-valuenow",20*parseInt(id));
            $("#step-progress").css("width",20*parseInt(id)+"%");
        }

    });

    $(".btn-before").click(function () {
        let parent = $(this).parents(".steps");
        let id = parent.attr("data-id");
        $(parent).toggle();
        $(".step-"+(parseInt(id)-1)).toggle();
        $("#step-progress").attr("aria-valuenow",20*(parseInt(id)-2));
        $("#step-progress").css("width",20*(parseInt(id)-2)+"%");
    });

    function stepOne() {
        let area = $("input[name=area]").val();
        let location = $("input[name=location]").val();
        let address = $("input[name=address]").val();
        let size = $("input[name=size]").val();
        let price = $("input[name=price]").val();


        if(!location){
            alert("地区必须填写");
            return false;
        }

        if(!address){
            alert("详细地址必须填写");
            return false;
        }

        if(!size){
            alert("房屋的大小必须填写");
            return false;
        }

        if(!price){
            alert("价格必须填写");
            return false;
        }

        return true;
    }


    function stepTwo(){
        let roomType = $("input[name=roomType]").val();
        let cave = $("input[name=cave]").prop("checked");
        let caveSize = $("input[name=caveSize]").val();
        let balcon = $("input[name=balcon]").prop("checked");
        let balconSize = $("input[name=balconSize]").val();


        if (cave){
            if (!caveSize){
                alert("地窖的大小必须填写");
            }
        }

        if (balcon){
            if (!balconSize){
                alert("阳台的大小必须填写");
            }
        }

        return true;
    }


    function stepThree(){
        let descripiton =  $(".description").val();
        let trans = $("input[name=trans]").val();

        if(!descripiton){
            alert("描述必须填写");
            return false;
        }

        if (trans){

        }

        return true;
    }


    function stepFive(){
        let nom = $("input[name=nom]").val();
        let prenom = $("input[name=prenom]").val();
        let wechat = $("input[name=wechat]").val();
        let email = $("input[name=email]").val();
        let telephone = $("input[name=telephone]").val();

        if (!nom||!prenom){
            alert("请填写姓名");
            return false;
        }

        if (!wechat){
        }

        if (!email){
            alert("邮件必须填写")
            return false;
        }

        if (!telephone){
            alert("电话必须填写")
            return false;
        }

        return true;
    }




});