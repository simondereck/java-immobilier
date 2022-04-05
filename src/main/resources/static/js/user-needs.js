$(function (){

    var data = function (){
        $.get("/data/garanties/professions",function (result){
            // return result;
            console.log(result);
        });
    };

    $("#slider").slider();
    $("#slider").slider('option',{min: 0, max: 5000});
    $("#slider").slider({
        change: function() {
            var value = $("#slider").slider("option","value");
            $("input[name=budget]").val(value);
        },
        slide: function() {
            var value = $("#slider").slider("option","value");
            $("input[name=budget]").val(value);
        }
    });
    $("input[name=budget]").keyup(function (){
        let val = $(this).val();
        $("#slider").slider("value",val);
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

    $(".form-horizontal").on('click',".select-ville",function (){
        let key = $(this).attr("data-key");
        let id = $(this).attr("data-id");
        $("input[name=location]").val(id);
        let value = villes[key].postcode + " -- " + villes[key].name + " -- " + villes[key].code;
        $("input[name=area]").val(value);
        $("#search-table").remove();
    });

    $(".btn-garantie").click(function (){
        //check data or something
        // var data = $('#user-needs').serializeArray();
        let $budget = $("input[name=budget]").val();
        let $area = $("input[name=area]").val();
        let $location = $("input[name=location]").val();
        let $sdate = $("input[name=sdate]").val();

        if (!$budget) {
            $(".price-error").text("请一定要填写您的预算！");
        }else{
            $(".price-error").text("");
        }

        if (!$location){
            if ($area){
                $(".location-error").text("请选择的区域不存在！");
            }else{
                $(".location-error").text("请一定要选择您的地区！");
            }
        }else{
            $(".location-error").text("");
        }

        if (!$sdate){
            $(".sdate-error").text("请选择你想入住的时间！")
        }else{
            $(".sdate-error").text();
        }

        if ($budget&&$sdate&&$sdate) {
            $(".step-3").toggle();
            $(".step-4").toggle("slow");
            $("#step-3").removeClass("active");
            $("#step-4").addClass("active");
        }
    });

    $(".garantie-1").hide();
    $(".garantie-2").hide();
    $("#garantie").change(function (){
        let val = $(this).val();
        for (i=0;i<3;i++){
            let name = ".garantie-" + i;
            if (val==i){
                $(name).show()
            }else{
                $(name).hide();
            }
        }
    });

    $(".btn-pre").click(function (){
        //before next check data;

        $(".step-4").toggle();
        $(".step-3").toggle("slow");
        $("#step-4").removeClass("active");
        $("#step-3").addClass("active");


    });


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


});