$.layer = {

    //初始化一个layer对象
    layerInit: function (layer0) {
        layer = layer0;
    },

    //tip提示层
    tips: function (content, target, offset, time) {
        if (time == 0) {
            time = 0;
        } else {
            time = time || 3000;
        }
        return layer.tips(content, target, {
            tips: offset,
            time: time
        });
    },

    //关闭一个层
    close: function (index) {
        layer.close(index);
    },

    loadMsg: function (message){
        message= message || "加载中";
        var index = layer.msg(message,{
            time:false,
            icon:16,
            shade:[0.5,"#fff"]
        });
        return index;
    },

    //加载 提示
    load: function (time, style) {
        time = time || false;
        style = style || 1;
        return layer.load(style, {
            shade: [0.5, '#fff'],
            time: time
        });
    },

    //关闭遮罩层
    closeLoading: function () {
        layer.closeAll('loading');
    },

    //弹出提示 icon类型 0：带！的提示 1：√ 2：x 3：？ 4：锁 5：难过表情 6：微笑脸
    msg: function (content, icon, callback) {
        icon = icon || 0;
        layer.msg(content, {
            icon: icon,
            resize: false,
            anim: 6
        }, function () {
            if (callback) {
                callback();
            }
        });
    },

    //alert弹出
    alertBtn: function (content, icon, btnText, title, callback) {
        if (btnText) {
            layer.alert(content, {
                closeBtn: 1,
                icon: icon,
                btn: [btnText],
                title: title,
                resize: false
            }, function (index) {
                $.layer.close(index);
                if (callback) {
                    callback();
                }
            });
        } else {
            layer.alert(content, {
                closeBtn: 1,
                title: title,
                resize: false
            }, function (index) {
                $.layer.close(index);
                if (callback) {
                    callback();
                }
            });
        }
    },

    //alert弹出信息（icon类型 0：带！的提示 1：√ 2；x 3:? 4:锁 5:难过脸 6：微笑脸）
    alert: function (content, icon, callabck, title) {
        icon = icon || 0;
        layer.alert(content, {
            closeBtn: 1,
            icon: icon,
            title: title,
            resize: false
        }, function (index) {
            $.layer.close(index);
            if (callback) {
                callabck();
            }
        });
    },

    //alert成功提示
    alertSuccess: function (content, callback, title) {
        layer.alert(content, {
            closeBtn: 1,
            icon: 6,
            title: title,
            resize: false
        }, function (index) {
            $.layer.close(index);
            if (callback) {
                callback();
            }
        });
    },

    //alert失败提示
    alertFailure: function (content, callback, title) {
        layer.alert(content, {
            closeBtn: 1,
            icon: 5,
            title: title,
            resize: false
        }, function (index) {
            $.layer.close(index);
            if (callback) {
                callback();
            }
        });
    },

    //alert错误提示
    alertError: function (content, callback, title) {
        layer.alert(content, {
            closeBtn: 1,
            icon: 3,
            title: title,
            resize: false
        }, function (index) {
            $.layer.close(index);
            if (callback) {
                callback();
            }
        });
    },

    //alert警告提示
    alertWarning: function (content, callback, title) {
        layer.alert(content, {
            closeBtn: 1,
            icon: 7,
            title: title,
            resize: false
        }, function (index) {
            $.layer.close(index);
            if (callback) {
                callback();
            }
        });
    },

    //confirm提示1
    confirm: function (content, sureFunction, closeFunction) {
        layer.confirm(content, {
            icon: 3,
            resize: false
        }, function (index) {//确定回调
            if (sureFunction) {
                sureFunction();
            }
           // layer.close(index);
        }, function (index) {//cancel回调
            if (closeFunction) {
                closeFunction();
            }
           // layer.close(index);
        });
    },

    //confirm提示2
    confirmPrd: function (content, title, sureText, closeText, closeFunction) {
        title = title || "";
        layer.confirm(content, {
            icon: 3,
            btn: [sureText, closeText],
            resize: false
        }, function (index) {//确定回调
            if (sureFuction) {
                sureFuction();
            }
            layer.close(index);
        }, function (index) {//cancel回调
            if (closeFunction) {
                closeFunction();
            }
            layer.close(index);
        });
    },

    //打开窗口
    openWindow: function (content, title, width, height, closeBtn) {
        title = title || "";
        width = width || '500';
        height = height || '500';
        closeBtn = closeBtn || 1; //0不显示 1,2两种不同的关闭按钮
        if (width.indexOf('%') == -1) {
            width = width + 'px';
        }
        if (height.indexOf('%') == -1) {
            height = height + 'px';
        }
        closeBtn = parseInt(closeBtn);
        return layer.open({
            title: title,
            closeBtn: closeBtn,
            content: content,
            type: 1,
            resize: false,
            area: [width, height]
        });
    },
    /*//打开Iframe窗口
    openIframe : function(url,title,width,height,closeBtn){
        title = title || false;
        width = width || '500';
        height = height || '500';
        closeBtn = closeBtn || 1; //0不显示 1，2两种不同的关闭按钮
        if(width.indexOf('%') ==-1 ){
            width = width +'px';
        }
        if(height.indexOf('%') ==-1){
            height = height+'px';
        }
        closeBtn = parseInt(closeBtn);
        url =$.util.getURL(url);
        var show_title = false;
        if(title !='' && title !=false){
            title = title.replace("&nbsp;","");
            title = title.replace(" ","");
            show_title = "<i class=\"fa fa-list-alt\"></i> "+title;
        }
        layer.config({
            extend:"/skin/blue/style.css" //加载扩展样式
        });
        return layer.open({
            title:show_title,
            closeBtn:closeBtn,
            content:url,
            type:2,
            resize:false,
            area:[width,height],
            skin:"layui-layer-ext-blue"
        });
    },*/

    //window打开新窗口
    cwinopen: function (url, title, width, height) {
        title = title || "无定义";
        width = width || screen.availWidth - 200;
        height = height || screen.availHeight - 200;
        var wx = (screen.availWidth - 10 - width) / 2;
        var wy = (screen.availHeight - 100 - height) / 2;
        url = $.util.getURL(url);
        //打开新窗口
        title = title.replace(/&nbsp;/g, "");
        title = title.replace(/\s+/g, "");
        window.open(url, title, "status = yes, menubar = yes,toolbar=yes,left = " + wx + ",top=" + wy + ",width=" + width + ",height=" + height);
    },
    //window打开模式话对话框窗口
    /*cwinModalOpen: function(url,title,width,height){
        title = title || "无定义";
        width = width ||screen.availWidth -200;
        height = height || screen.availHeight-200;
        url=$.util.getURL(url);
        title=title.replace(/&nbsp;/g,"");
        title = title.replace(/\s+/g,"");
        var rtnValue = window.showModalDialog(url,title,"dialogWidth:"+width+"px;dialogHeight:"+height+"px:edge:Raised;center:Yes;help:no;resizable:Yes;status:no;");
        return rtnValue;
    }*/
};

