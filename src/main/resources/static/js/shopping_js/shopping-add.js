$("#save-btn").on("click", function () {
    var data = $("#blog-form").serialize();
    $.util.ajaxPost("保存中", "/shopping/save", data, function (resdata) {
        //返回上一个界面并刷新
        if (resdata.code == 20000) {
            $.layer.alertSuccess("保存成功", function () {
                self.location = document.referrer;
            })
        } else {
            $.layer.alertWarning(resdata.message);
        }
    });
})


$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});

$('.ui.dropdown').dropdown({
    on: 'hover'
});

// $('#save-btn').click(function () {
//     $('[name="published"]').val(false);
//     $('#blog-form').submit();
// });



