$(function (){

    $("html").on("click",".imm-item",function (){
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

    $("html").on("click",".base-item",function (){
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

    $("html").on("click",".energy-item-group",function (){
        let checked = $(this).find("input[type=radio]").prop("checked");
        if (checked){
            $(this).find("input[type=radio]").prop("checked",false);
        }else{
            $(this).find("input[type=radio]").prop("checked",true);
        }
    });

    var villes;

    $("html").on("keyup","#area",function (){
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


    $("html").on('click',".select-ville",function (){
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

});