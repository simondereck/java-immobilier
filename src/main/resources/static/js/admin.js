$(function (){

    var lat = 0;
    var lng = 0;

    var user;

    function showLocation(position) {
        var latitude = position.coords.latitude;
        var longitude = position.coords.longitude;

        lat = latitude;
        lng = longitude;
        console.log(lat,lng);
        var data = {};
        data['lat'] = lat;
        data['lng'] = lng;
        $.post("/admin/location",data,function (result){
            console.log(result);
            if (result.status==1){
                console.log(result.message);
            }else if (result.status==-1){
                confirm("您的登录位置不在系统设置范围内，系统将会自动退出！");
                $.get("/admin/logout",function (res) {
                    window.location.assign("/admin");
                    // window.close();
                });
            }
        });

        let params = {};
        let d = new Date();
        let month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2)
            month = '0' + month;
        if (day.length < 2)
            day = '0' + day;

        let date = [year, month, day].join('-');
        params["date"] = date;
        let hour = '' + d.getHours(),
            mins = '' + d.getMinutes(),
            seconds = '' + d.getSeconds();
        if (hour.length<2)
            hour = '0' + hour;
        if (mins.length<2)
            mins = '0' + mins;
        if (seconds.length<2)
            seconds = '0' + seconds;

        params["etime"] = [hour,mins,seconds].join(':');

        console.log(params);
        $.post("/admin/setLoginInfo",params,function (res) {
            console.log(res);
        });

    }

    function errorHandler(err) {
        if(err.code == 1) {
            alert("Error: Access is denied!");
        } else if( err.code == 2) {
            alert("Error: Position is unavailable!");
        }else{
            alert("Error: 请打开你的系统定位!");
            console.log(err);
        }

        if (user){
            if(user.permission<9){
                confirm("你必须打开您的定位，否则您的登录将会自动退出！");
                $.get("/admin/logout",function (res) {
                    // console.log(res);
                    window.close();
                });
            }
        }

    }

    function getLocation() {
        if(navigator.geolocation) {
            // timeout at 10000 milliseconds (10 seconds)
            var options = {timeout:10000};
            navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);
        } else {
            alert("Sorry, browser does not support geolocation!");
        }
    }

    if (lat == 0 || lng == 0){
        getLocation();
    }

    $(".client-button").click(function (){
        window.open("/admin/clients","hwwd 客服平台","height=800,width=1180,top=100,left=200,status=no,scrollbars=no");
    });
    
    $.get("/admin/alarm/detail",function (data) {
        if (data.status==1){
            $(".total").text(data.alarm.totalAnnonces);
            $(".success").text(data.alarm.successAnnonces);
            $(".contract").text(data.alarm.contractAnnonces);
            $(".empty").text(data.alarm.emptyAnnonces);
            console.log(data);
            user = data.user;
        }
    });


});