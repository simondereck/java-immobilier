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
        var data = $('#user-needs').serializeArray();
        console.log(data);
        // $("#user-needs").submit();
        $(".step-3").toggle();
        $(".step-4").toggle("slow");
        $("#step-3").removeClass("active");
        $("#step-4").addClass("active");
    });

    $(".garantie-1").hide();
    $(".garantie-2").hide();
    $("#garantie").change(function (){
        let val = $(this).val();
        for (i=0;i<3;i++){
            let name = ".garantie-" + i;
            // $(name).toggle();
            if (val==i){
                $(name).show()
            }else{
                $(name).hide();
            }
        }
    });

    $(".btn-pre").click(function (){
        $(".step-4").toggle();
        $(".step-3").toggle("slow");
        $("#step-4").removeClass("active");
        $("#step-3").addClass("active");
    });

    $(".imm-item").click(function (){
        let checked = $(this).find("input[type=checkbox]").prop("checked");
        if (checked){
            $(this).find("input[type=checkbox]").prop("checked",false);
            $(this).removeClass("active");
        }else{
            $(this).find("input[type=checkbox]").prop('checked',true);
            $(this).addClass("active");
        }
    });

});