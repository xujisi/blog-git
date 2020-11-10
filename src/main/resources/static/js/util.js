$.util = {

    getURL: function (url) {
        var host = window.location.host;
        return host + url;
        //获取主机地址，如： http://localhost:8083
    },

    //表单提交
    submitForm: function (url, form, message) {
        form = form || document.forms[0];
        if (url) {
            alert($.util.getURL(url));
            form.action = url;
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
            error: function (respData) {
                $.layer.alertFailure(respData.message);
            }
        });
    },

};
