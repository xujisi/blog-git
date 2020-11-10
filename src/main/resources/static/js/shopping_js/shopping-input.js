$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});

$('.ui.dropdown').dropdown({
    on: 'hover'
});

//消息提示关闭初始化
$('.message .close')
    .on('click', function () {
        $(this)
            .closest('.message')
            .transition('fade');
    });

$('#clear-btn')
    .on('click', function () {
        $('.ui.type.dropdown')
            .dropdown('clear')
        ;
    });


function loaddata() {
    //局部渲染某个DIV，JQ里面的AJAX
    $("#table-container").load("/shopping/search", {
        goodsName: $("[name='goodsName']").val(),
        status: $("[name='status']").val(),
    });
}

function deleteBtn(obj) {
    var id = $(obj).data("id");//获得参数的值
    $.layer.confirm("确定无效化吗", function () {
        $.util.ajaxPost("正在操作", "/shopping/delete?id=" + id, function (respData) {
            if (respData.code == 20000) {
                $.layer.alertSuccess(respData.message, function () {
                    window.location.href = "/shopping/input"
                });
            } else {
                $.layer.alertFailure(respData.message);
            }
        })
    });
}


function page(obj) {
    //下一页上一页 按钮
    $("[name='page']").val($(obj).data("page"));
    loaddata();
}

//搜索按钮
$("#search-btn").click(function () {
    $("[name='page']").val(0);
    loaddata();
});


function loaddata() {
    upload = layer.msg('加载中。。。', {
        icon: 16,
        shade: 0.2,
        time: 200
    });
    //局部渲染某个DIV，JQ里面的AJAX
    $("#table-container").load("/shopping/search", {
        goodsName: $("[name='goodsName']").val(),
        status: $("[name='status']").val(),
        page: $("[name='page']").val()
    });
}

//上传按钮
$("#upload-btn").click(function () {
    $.layer.openWindow($("#hiddenDiv").html(), "上传界面");
})

//下载模板
$(document).on('click', '#download', function () {
    window.location.href = "/shopping/downloadExcel";
});

//添加文件
$(document).on('click', '#add', function () {
    $("#files").click();
});

//上传文件
$(document).on('click', '#upload', function () {
    $("#submits").click();
});

//重置文件选择
$(document).on('click', '#reset', function () {
    $("#files").val('');
    $(".excelName").attr("value", "");
    return false;
});

//关闭弹窗
$(document).on('click', '#close', function () {
    layer.closeAll();
    return false;
});

//显示选择的文件
$(document).on('change', '#files', function () {
    var files = $("#files").get(0).files;
    var filesName = files[0].name;
    $(".excelName").attr("value", filesName);
});
