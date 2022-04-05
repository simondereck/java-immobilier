function hwwdImageEditor(item,src) {
    let contentEditorView = "<div id='hwwd-img-editor-display-sets'>" +
        "<div id='hwwd-img-editor-dispaly-sets-close'>x</div>" +
        "<div style='width: 100%;height: 80%;background-color: rgba(0,0,0,0.2);position: relative;text-align: center'><img id='hwwd-img-editor-display-img'/></div>" +
        "<div style='background-color: white;padding: 20px;height:20%;' class='form-group'>" +
        "   <button class='btn btn-danger' id='hwwd-delete-image'>删除</button>" +
        "   <button class='btn btn-success' id='hwwd-set-cover'>设置为封面</button></div>"
    "</div>";

    $("html").append(contentEditorView);

    $("body").addClass("hwwd-plugin-display");
    $("#hwwd-img-editor-display-img").attr("src",src);

    $("html").on("click","#hwwd-img-editor-dispaly-sets-close",function () {
        $("body").removeClass("hwwd-plugin-display");
        $("#hwwd-img-editor-display-sets").remove();
    });

    $("html").on("click","#hwwd-delete-image",function (){
        $(item).remove();
        $("#hwwd-img-editor-display-sets").remove();
        $("body").removeClass("hwwd-plugin-display");

    });

    $("html").on("click","#hwwd-set-cover",function () {
        let attr = $(item).children("input").val();
        $("#cover").val(attr);
        $(".cover").children("img").attr("src",src);
        $("#hwwd-img-editor-display-sets").remove();
        $("body").removeClass("hwwd-plugin-display");
    });

}


