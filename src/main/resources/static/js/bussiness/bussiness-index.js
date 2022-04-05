$(function () {
    $("#navbar-custom").removeClass("bg-light");

    $(".button-type").click(function () {
        $(".button-type").each(function (key,item) {
            $(this).removeClass("button-active");
        });
        $(this).addClass("button-active");
        let id = $(this).attr("data-id");

        if (id==0){
            $(".search-box-right").css("background-color","#0f6674");
            $("#search-img").attr("src","/img/bussiness/search.png")
        }else if(id==1){
            $(".search-box-right").css("background-color","#b21f2d");
            $("#search-img").attr("src","/img/bussiness/search-1.jpg")
        }else if (id==2){
            $(".search-box-right").css("background-color","#c69500");
            $("#search-img").attr("src","/img/bussiness/search-2.jpg")
        }else if (id==3){
            $(".search-box-right").css("background-color","#1d2124");
            $("#search-img").attr("src","/img/bussiness/search-3.png")
        }else if (id==4){
            $(".search-box-right").css("background-color","#1c7430");
            $("#search-img").attr("src","/img/bussiness/search-4.png")
        }
    });


    $(".house-div-title-item").click(function () {
        let id = $(this).attr("data-id");
        $(".house-div-title-item").each(function (key,item) {
            $(this).removeClass("house-active");
        })
        $(this).addClass("house-active");
    });

    $("#calcu-range").change(function(){
        let val = $(this).val();
        let number1 = (parseInt(val)*(1-0.0487)).toFixed(2);
        let number2 = (parseInt(val)-680);
        let result = number2 - number1;
        $("#calcu-paltform").text(number2+" €");
        $("#calcu-agents").text(number1+" €");
        $("#calcu-gar").text(result.toFixed(2));
    });
    $("#calcu-agents").text("0 €");
    $("#calcu-paltform").text("0 €");

    $(".advance-item").click(function () {
        window.location.href = "/bussiness/platform";
    });

    $.get("/bussiness/data/lastDemande",function (data) {
        if (data.status==1){
            let annonces = data.annonces;
            let annonceDiv = "<div class='scroll-items'>";
            for (let k in annonces){
                let buyItem = "<div class='buy-item' data-id='"+annonces[k].id+"'>";
                buyItem += "<div class='form-group price'>预算："+annonces[k].budget+" EURO</div>"
                buyItem += "<div class='form-group'>地段："+annonces[k].area+"</div>";
                buyItem += "<div class='form-group'>面积："+annonces[k].size+" 平米</div>";
                if (annonces[k].payMethod==0){
                    buyItem += "<div class='form-group'>方式：全款</div>";
                }else{
                    buyItem += "<div class='form-group'>方式：首付</div>";
                }
                buyItem += "<div class='form-group'>职业："+data.profession[annonces[k].profession]+"</div>";

                if (annonces[k].buyTime==0){
                    buyItem += "<div class='form-group'>时间：立刻购买</div>";

                }else if (annonces[k].buyTime==1){
                    buyItem += "<div class='form-group'>时间：3个月内购买</div>";

                }else if (annonces[k].buyTime==2){
                    buyItem += "<div class='form-group'>时间：6个月内购买</div>";

                }else {
                    buyItem += "<div class='form-group'>时间：将来购买</div>";
                }


                buyItem +="</div>";
                annonceDiv += buyItem;
            }

            annonceDiv += "</div>";
            annonceDiv += "<div style='text-align: right;'><a href='/site/demande/more' style='margin-right: 50px;'>更多买家>></a></div>"
            $(".buy-house-scroll").append(annonceDiv);

        }
    });

    $.get("/bussiness/data/lastInsert",function (data) {
        if(data.status==1){
            let annonceDiv="<div class='scroll-items'>";
            for (let i=0;i<data.annonces.length;i++){
                let annonce = data.annonces[i];
                annonceDiv += "<div class='location-detail-item' data-id='"+annonce.id+"'>";
                annonceDiv += "<div>";

                if (annonce.cover){
                    annonceDiv += "<img src='"+"/partners/"+annonce.pid+"/"+annonce.cover+"'/>";
                }else{
                    annonceDiv += "<img src='/img/default.jpg'/>";
                }


                switch (annonce.houseLabel){
                    case 0:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>个人</div></div>";
                        break;
                    case 1:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>房管</div></div>";

                        break;
                    case 2:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>中介</div></div>";

                        break;
                    case 3:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>加盟商</div></div>";

                        break;
                    case 4:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>平台自营</div></div>";
                        break;
                }

                annonceDiv += "</div>";


                annonceDiv += "<div class='location-detail-item-desc'>";
                if(annonce.area){
                    annonceDiv += "<div class='title'>"+annonce.area+"</div>"
                }

                annonceDiv += "<div class='title'>"+data.houseType[annonce.types][annonce.houseType]+"</div>";


                if(annonce.size){
                    annonceDiv += "<div class='title'>"+annonce.size+" 平米</div>";
                }

                if(annonce.roomType>0){
                    annonceDiv += "<div class='title'>"+data.roomType[annonce.roomType]+"</div>";
                }


                annonceDiv += "<div class='title price'>"+annonce.price+" EURO</div>";

                annonceDiv += "</div></div>";
            }

            annonceDiv += "</div>";
            annonceDiv += "<div style='text-align: right;'><a href='/site/bussiness/more' style='margin-right: 50px;'>更多房源>></a></div>"
            $(".house-scroll").append(annonceDiv);
        }
    });


    $.get("/bussiness/data/platform",function (data) {
        if(data.status==1){
            let annonceDiv="<div class='scroll-items'>";
            for (let i=0;i<data.annonces.length;i++){
                let annonce = data.annonces[i];
                annonceDiv += "<div class='location-detail-item' data-id='"+annonce.id+"'>";
                annonceDiv += "<div>";

                if (annonce.cover){
                    annonceDiv += "<img src='"+"/partners/"+annonce.pid+"/"+annonce.cover+"'/>";
                }else{
                    annonceDiv += "<img src='/img/default.jpg'/>";
                }


                switch (annonce.houseLabel){
                    case 0:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>个人</div></div>";
                        break;
                    case 1:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>房管</div></div>";

                        break;
                    case 2:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>中介</div></div>";

                        break;
                    case 3:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>加盟商</div></div>";

                        break;
                    case 4:
                        annonceDiv += "<div class='cover'><div class='location-item-label'>平台自营</div></div>";
                        break;
                }

                annonceDiv += "</div>";


                annonceDiv += "<div class='location-detail-item-desc'>";
                if(annonce.area){
                    annonceDiv += "<div class='title'>"+annonce.area+"</div>"
                }

                annonceDiv += "<div class='title'>"+data.houseType[annonce.types][annonce.houseType]+"</div>";


                if(annonce.size){
                    annonceDiv += "<div class='title'>"+annonce.size+" 平米</div>";
                }

                if(annonce.roomType>0){
                    annonceDiv += "<div class='title'>"+data.roomType[annonce.roomType]+"</div>";
                }


                annonceDiv += "<div class='title price'>"+annonce.price+" EURO</div>";

                annonceDiv += "</div></div>";
            }

            annonceDiv += "</div>";
            annonceDiv += "<div style='text-align: right;'><a href='/site/bussiness/more' style='margin-right: 50px;'>更多房源>></a></div>"
            $(".platform-house-scroll").append(annonceDiv);
        }
    });

    $("html").on("click",".location-detail-item",function () {
        let id = $(this).attr("data-id");
        window.location.assign("/bussiness/house/detail/"+id+".html")
    });

    $("html").on("click",".buy-item",function () {
        let id = $(this).attr("data-id");
        window.location.assign("/bussiness/demande/detail/"+id+".html");
    });

    $("html").on("click",".house-div-title-item",function () {
        let id = $(this).attr("data-id");
        $(".select-div-item").each(function (key,item) {
            if ($(item).attr("data-id")==id){
                $(item).addClass("active");
                $(item).show();
            }else{
                $(item).removeClass("active");
                $(item).hide();
            }
        });
    });
});