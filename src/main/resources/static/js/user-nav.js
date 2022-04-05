$(function(){
    let pathname = window.location.pathname;
    $(".nav-user-item").each(function (key,item){
        console.log($(item).attr("href"),pathname);
        if ($(item).attr("href")===pathname){
            $(item).addClass("active");
        }
    });
    $(".nav-user-item").click(function (){
        let url = $(this).attr("href");
        if (url){
            window.location.href=url;
        }
    });
});