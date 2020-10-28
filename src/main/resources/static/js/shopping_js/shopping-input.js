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

$(".delete").on('click', function () {
    var id = $(this).attr("data-id");//获得参数的值
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
})


function page(obj) {
    //下一页上一页 按钮
    $("[name='page']").val($(obj).data("page"));
    loaddata();
}

$("#search-btn").click(function () {
    //查询按钮
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
    $("#table-container").load(/*[[@{/admin/blogs/search}]]*/"/shopping/search",{
        goodsName : $("[name='goodsName']").val(),
        status : $("[name='status']").val(),
        page : $("[name='page']").val()
    });
}
