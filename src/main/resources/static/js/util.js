$.util = {

    //表单提交
    submitForm: function (url, form, message) {
        form = form || document.forms[0];
        message = message || '加载中';
        $.layer.loadMsg(message);
        if (url) {
            form.action = $.util.getURL(url);
        }
        form.submit();
    },

    //ajax提交表单
    ajaxPost: function (message, arg1, arg2, arg3, async) {
        var url, data, successFn;
        async = async || true; //async:false同步、true:异步
        if (arguments.length == 3) {
            url = arg1;
            data = null;
            successFn = arg2;
        } else if (arguments.length >= 4) {
            url = arg1;
            data = arg2;
            successFn = arg3;
        }
        var index;
        $.ajax({
            type: "post",
            url: url,
            data: data,
            dataType: "json",
            cache: false,
            async: async,
            beforeSend: function (XMLHttpRequest) {
                index = $.layer.loadMsg(message);
            },
            success: function (respData) {
                successFn(respData);
            },
            complete: function (XMLHttpRequest, textStatus) {
                $.layer.close(index);
            },
            error: function () {
                $.layer.msg("操作异常", 2);
            }
        });
    },
};
