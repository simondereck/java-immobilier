$(function () {
    // console.log(navigator.userAgent);
    function IsMobile() {
        var isMobile = {
            Android: function () {
                return navigator.userAgent.match(/Android/i) ? true : false;
            },
            BlackBerry: function () {
                return navigator.userAgent.match(/BlackBerry/i) ? true : false;
            },
            iOS: function () {
                return navigator.userAgent.match(/iPhone|iPad|iPod/i) ? true : false;
            },
            Windows: function () {
                return navigator.userAgent.match(/IEMobile/i) ? true : false;
            },
            any: function () {
                return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS()
                    || isMobile.Windows());
            }
        };

        return isMobile.any(); //是移动设备
    }

    let isMobilePhone = IsMobile();
    if (isMobilePhone){
        let searchIcon = "<div id='hwwd-search-icon'>🔍</div>";
        $("html").append(searchIcon);

        $("html").on("click","#hwwd-search-icon",function () {
            $("#search-div").toggle();
            if ($("#search-div").is(":visible")){
                $("html").addClass("search-icon-show");
            }else{
                $("html").removeClass("search-icon-show");
            }
        });

        $(".btn-search").click(function () {
            if ($("#search-div").is(":visible")){
                $("html").remove("search-icon-show");
                $("#search-div").hide();
            }
        });
    }

    $('.search-input').focus(function () {
        $('.search-input').attr("autocomplete","off")
    });


    var lat = 0;
    var lng = 0;
    var villes;
    var dataVille;
    let vkey;
    var markers = [];

    var houseType;

    function showLocation(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;
        lat = latitude;
        lng = longitude;
        var data = {};
        data['lat'] = lat;
        data['lng'] = lng;
        lat = position.coords.latitude;
        lng = position.coords.longitude;
        setMap();
    }

    function errorHandler(err) {
        if(err.code == 1) {
            // alert("Error: Access is denied!");
        } else if( err.code == 2) {
            // alert("Error: Position is unavailable!");
        }
        lat = 48.8588376;
        lng = 2.2768486;
        setMap();
    }

    function getLocation() {
        if(navigator.geolocation) {
            // timeout at 60000 milliseconds (60 seconds)
            var options = {timeout:60000};
            navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);
        } else {
            alert("Sorry, browser does not support geolocation!");
        }
    }


    getLocation();


    mapboxgl.accessToken = 'pk.eyJ1IjoiaGFpd2FpaHVhbmdkaSIsImEiOiJja2huZGpmZmUyZW5pMnJxcXA0bHlycmc0In0.Wh2Kzu-CfQYA0D91x5YyNg';
    var dataArray;
    var map;

    function setMap(){

        map = new mapboxgl.Map({
            container: 'map',
            style: 'mapbox://styles/mapbox/streets-v11', // stylesheet location
            center:[lng,lat],
            zoom:13
        });

        var nav = new mapboxgl.NavigationControl();
        map.addControl(nav, 'top-left');

        var marker = new mapboxgl.Marker({color:"#dc0000"})
            .setLngLat([lng,lat]);
        marker.addTo(map);

        let params = {};
        params["maxLng"] = parseFloat(lng.toFixed(1)) + 0.1;
        params["minLng"] = parseFloat(lng.toFixed(1)) - 0.1;
        params["maxLat"] = parseFloat(lat.toFixed(1)) + 0.1;
        params["minLat"] = parseFloat(lat.toFixed(1))- 0.1;

        $.post("/site/map/bussiness/annonces",params,function (data) {
            console.log(data);
            if (data.status==1){
                let latlngs = data.latlngs;
                let annonces = data.annonces;
                houseType = data.houseType;
                dataArray = latlngs;
                for (let i in latlngs) {
                    for (let j in annonces){
                        if (latlngs[i].bid === annonces[j].id){
                            latlngs[i].annonce = annonces[j];
                            addLatLng(latlngs[i]);
                            let annonce = annonces[j];
                            let annonceDiv =  "<div class='location-detail-item' data-id='"+annonce.id+"' data-key='"+i+"' id='annonce-"+annonce.id+"'>";
                            if (annonce.cover){
                                annonceDiv += "<div>" +
                                    "<img src='/partners/"+annonce.pid+"/"+annonce.cover+"'>\n"+
                                    "</div>";
                            }else{
                                annonceDiv += "<div><img src='/img/default.jpg'></div>";
                            }
                            annonceDiv += "<div class='location-detail-item-desc'>";

                            annonceDiv += "<div class='title'> " + annonce.area +" </div>";


                            annonceDiv += "<div  class='title'>" + houseType[annonce.types][annonce.houseType] +"</div>";

                            annonceDiv += "<div class='title'> " + annonce.size +" 平</div>";


                            annonceDiv += "<div class='price'>"+annonce.price+" EURO</div>" +
                                "<a href='/bussiness/house/detail/"+annonce.id+".html'>详情</a>" +
                                "</div></div>";

                            $("#annonces").append(annonceDiv);
                        }
                    }
                }
            }
        });
    }




    function addLatLng(latlng){
        var obj = jQuery.parseJSON(latlng.centerJson);
        var marker = new mapboxgl.Marker()
            .setLngLat(obj);
        marker.getElement().addEventListener('click',function () {
            let annonce = latlng.annonce;
            let popWindow =  "<div class='location-detail-item' data-id='"+annonce.id+"'>";
            if (annonce.cover){
                popWindow += "<div>" +
                    "<img src='/partners/"+annonce.pid+"/"+annonce.cover+"'>\n"+
                    "</div>";
            }else{
                popWindow += "<div><img src='/img/default.jpg'></div>";
            }
            popWindow += "<div class='location-detail-item-desc'>";



            popWindow += "<div class='title'> " + annonce.area +" </div>";


            popWindow += "<div  class='title'>" + houseType[annonce.types][annonce.houseType] +"</div>";

            popWindow += "<div class='title'> " + annonce.size +" 平</div>";


            popWindow += "<div class='price'><h4>"+annonce.price+" EURO</h4></div>" +
                "<a href='/bussiness/house/detail/"+annonce.id+".html'>详情</a>" +
                "</div></div>";

            addPop(obj,popWindow);
        });
        marker.addTo(map);
        markers.push(marker);
        map.resize();
    }


    function addPop(latlng,popWindow){
        var popup = new mapboxgl.Popup({ closeOnClick: false })
            .setLngLat(latlng)
            .setHTML(popWindow)
            .addTo(map);
    }



    $("#search-nav").on("click","span",function () {
        let href = $(this).attr("data-href");
        let pathname = location.pathname;
        if (href!=pathname){
            location.assign(href);
        }
    });


    $("#map").on("click",".location-detail-item",function () {
        let id = $(this).attr("data-id");
        let labelA = document.createElement("a");
        labelA.href = "#annonce-"+id;
        labelA.click();
    });

    $("#annonces").on("click",".location-detail-item",function () {
        let key = $(this).attr("data-key");
        let latlng = dataArray[key];
        var obj = jQuery.parseJSON(latlng.centerJson);
        map.flyTo({
            center: obj,
            zoom:13
        });
    });

    $("#search").click(function () {
        if ($("#search-div").is(":visible")){
            $("#search-div").slideUp('slow');
        }else{
            $("#search-div").slideDown("slow");
        }
    });


    $.get("/data/pays",function (data) {
        if (data){
            let dataString = '<select name="pays" id="pays" class="form-control">';
            data.forEach(function (item,key) {
                dataString += '<option value="'+item.id+'">'+item.name+'</option>';
            })
            dataString += '</select>';
            $(".pays-set").html(dataString);
        }
    });



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
        $("#area-div").hide();
        map.flyTo({
            center: [villes[key].lng,villes[key].lat],
            zoom:13
        });
        $("#area-name").text(" : "+villes[key].name);
        vkey = key;
        initPage();
    });


    $(".select-item").click(function () {
        let label = $(this).attr("data-label");
        $(".select-item-content").each(function (key,item) {
            if($(item).hasClass(label)){
                $(item).toggle();
            }else{
                $(item).hide();
            }
        });
    });

    $(".btn-confirm").click(function () {
        let label = $(this).attr("data-label");
        $("."+label).toggle();
        if(label=="price"){
            let price1 = $("input[name=price1]").val();
            let price2 = $("input[name=price2]").val();

            if (price1&&price2){
                $("#price-text").text(" : "+price1 + " ~ " +price2 );
            }else if (price1){
                $("#price-text").text(" : > "+price1);
            }else if (price2){
                $("#price-text").text(" : < "+price2);
            }else{
                $("#price-text").text("");
            }
        }else if (label=="size"){
            let size1 = $("input[name=size1]").val();
            let size2 = $("input[name=size2]").val();

            if (size1&&size2){
                $("#size-text").text(" : "+size1 + " ~ " +size2 );
            }else if (size1){
                $("#size-text").text(" : > "+size1);
            }else if (size2){
                $("#size-text").text(" : < "+size2);
            }else{
                $("#size-text").text("");
            }
        }

        initPage();
    });

    $("#order").on("click",".order-item",function () {
        let id = $(this).attr("data-id");
        $("input[name=orderValue]").val(1);
        if($(this).hasClass("active")){
            if ($(this).hasClass("order-desc")){
                $(this).removeClass("order-desc");
                $(this).addClass("order-asc");
                $("input[name=orderValue]").val(2);
            }else{
                $(this).removeClass("order-asc");
                $(this).addClass("order-desc");
            }
        }else{
            $(this).addClass("active");
            $(this).removeClass("order-asc");
            $(this).addClass("order-desc");
        }
        $(".order-item").each(function (key,item) {
            if ($(item).attr("data-id")!=id){
                $(item).removeClass("active");
            }
        });

        $("input[name=orderType]").val(id);
        initPage();
    });




    function initPage(url) {
        let urlPath = url ? url : "/site/bussiness/map/search";
        let dataParams = $("#searchAnnonces").serializeArray();
        let params = {};
        Loadding();
        dataParams.forEach(function (item,key) {
            if (item.value){
                params[item.name] = item.value;
            }
        });

        $("#annonces").html("");
        for (let i in markers){
            markers[i].remove();
        }
        $.post(urlPath,params,function (data) {
            if (data.status===1){
                let latlngs = data.latlngs;
                dataArray = latlngs;
                let annonces = data.annonces;
                houseType = data.houseType;
                for (let i in annonces) {
                    for (let j in latlngs){
                        if (annonces[i].id  === latlngs[j].bid){
                            latlngs[j].annonce = annonces[i];
                            addLatLng(latlngs[j]);
                            let annonce = annonces[i];
                            let annonceDiv =  "<div class='location-detail-item' data-id='"+annonce.id+"' data-key='"+i+"' id='annonce-"+annonce.id+"'>";
                            if (annonce.cover){
                                annonceDiv += "<div>" +
                                    "<img src='/partners/"+annonce.pid+"/"+annonce.cover+"'>\n"+
                                    "</div>";
                            }else{
                                annonceDiv += "<div><img src='/img/default.jpg'></div>";
                            }
                            annonceDiv += "<div class='location-detail-item-desc'>";

                            annonceDiv += "<div class='title'> " + annonce.area +" </div>";


                            annonceDiv += "<div  class='title'>" + houseType[annonce.types][annonce.houseType] +"</div>";

                            annonceDiv += "<div class='title'> " + annonce.size +" 平</div>";


                            annonceDiv += "<div class='price'>"+annonce.price+" EURO</div>" +
                                "<a href='/bussiness/house/detail/"+annonce.id+".html'>详情</a>" +
                                "</div></div>";

                            $("#annonces").append(annonceDiv);
                        }
                    }
                }
            }
            stopLoadding();
        });
    }
});