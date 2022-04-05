let contentView = "<div id='hwwd-img-display-sets'><div id='hwwd-img-dispaly-sets-close'>x</div><img id='hwwd-img-display-img'/></div>";
function initImageDisplay(src) {
    $("html").append(contentView);
    $("#hwwd-img-display-img").attr("src",src);
}

$("html").on("click","#hwwd-img-dispaly-sets-close",function () {
    $("#hwwd-img-display-sets").remove();
});


function Loadding(){
    let loaddingDiv = "<div class='hwwd-loadding-background'><img src='/img/loading/loading.gif' class='hwwd-loadding-img'/></div>";
    $("html").append(loaddingDiv);
    $("body").addClass("hwwd-loadding-show");
    $("html").addClass("hwwd-loadding-show");
}


function stopLoadding(){
    $(".hwwd-loadding-background").remove();
    $("body").removeClass("hwwd-loadding-show");
    $("html").removeClass("hwwd-loadding-show");
}