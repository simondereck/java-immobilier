$(function(){
    let pathname = window.location.pathname;
    $(".nav-partner-item").each(function (key,item){
        console.log($(item).attr("href"),pathname);
        if ($(item).attr("href")===pathname){
            $(item).addClass("active");
        }
    });
    $(".nav-partner-item").click(function (){
        let url = $(this).attr("href");
        if (url){
            window.location.href=url;
        }
    });


    $(".top-bar-div").on("click",".top-bar-item",function () {
        let url = $(this).attr("data-href");
        if (url){
            window.location.href = url;
        }
    });

    $(".top-bar-item").each(function (key,item){
       if($(item).attr("data-href") === pathname){
           $(item).addClass("active");
           $(".nav-partner-item").each(function (ke,val){
               if ($(val).attr("href")==="/partner/center"){
                   $(val).addClass("active");
               }
           });
       }

    });
});