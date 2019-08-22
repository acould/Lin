/**
 * js时间对象的格式化;
 * eg:format="yyyy-MM-dd hh:mm:ss";
 */
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,  //month   
        "d+": this.getDate(),     //day   
        "h+": this.getHours(),    //hour   
        "m+": this.getMinutes(),  //minute   
        "s+": this.getSeconds(), //second   
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter   
        "S": this.getMilliseconds() //millisecond   
    }
    var week = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    if (/(w+)/.test(format)) {
        format = format.replace(RegExp.$1, week[this.getDay()]);
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}

/**
 *js中更改日期
 * y年， m月， d日， h小时， n分钟，s秒
 */
Date.prototype.add = function (part, value) {
    value *= 1;
    if (isNaN(value)) {
        value = 0;
    }
    switch (part) {
        case "y":
            this.setFullYear(this.getFullYear() + value);
            break;
        case "m":
            this.setMonth(this.getMonth() + value);
            break;
        case "d":
            this.setDate(this.getDate() + value);
            break;
        case "h":
            this.setHours(this.getHours() + value);
            break;
        case "n":
            this.setMinutes(this.getMinutes() + value);
            break;
        case "s":
            this.setSeconds(this.getSeconds() + value);
            break;
        default:

    }
    return this
}

//alert(new Date().add("m", -1).format('yyyy-MM-dd hh:mm:ss')); //时间格式化使用方法 
$(function () { 
    $('.surebtn').on({
        click: function () {
            if (!$(this).hasClass('btn-disabled')) {
                rule.checkstate();//检查所有状态
                if (rule.allright) {

                    if (typeof(getStatesFn) != 'undefined') {
                        getStatesFn()
                    } else {
                        rule.getStatesFn()
                    }
                    return
                }
            }
        }
    }, '.btn');

})


;(function ($) {
    $.getQueryString = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]);
        return null;
    },
        $.enterSend = function (arg) {
            arg.main.keyup(function (e) {//回车搜索
                var keycode = (e.keyCode ? e.keyCode : e.which);
                if (keycode == '13') {
                    arg.action()
                }
            });
        }

})(Zepto);

var rule = {
    getStatesFn: function () {
        var form = $('.g-form');
        var formsubmit = $('.surebtn .btn');
        var formtext = formsubmit.html();
        var defaults = {
            dialogmes: null,
            gotourl: '#'
        };
        var options = $.extend(defaults, {
            dialogmes: form.data('dialogmes'),
            gotourl: $.getQueryString('fromurl') || form.data('gotourl')
        })

        function removeDis() {
            setTimeout(function () {
                formsubmit.removeClass('btn-disabled').html(formtext);
            }, 1000)
        };
        $.ajax({
            type: "POST",
            url: form.data('posturl'),
            data: form.serialize(),
            beforeSend: function () {
                formsubmit.addClass('btn-disabled').html('提交中...');
            },
            success: function (data) {
                removeDis();
                data.message = data.data || data.message;
                if (data.message == "success") {
                    if (options.dialogmes) {
                    	message(options.dialogmes, true, function () {
                        	sessionStorage.setItem("game-refresh", true);
                            location.href = options.gotourl;
                        }, 5000);
                        return;
                    }
                    window.location.href = options.gotourl;
                } else {
                    var mess = "请求失败";
                    if (data.message == 'code') {
                        mess = '图片验证码有误';
                        $('#getcodeJs img').trigger('click');
                    } else if (data.message == 'telCode') {
                        mess = '短信验证码有误';
                    } else if (data.message == 'paypwd') {
                        mess = '交易密码错误';
                    } else if (data.message == 'aready') {
                        mess = '已报名';
                    }
                    message(mess);
                }
            },
            error: function () {
            	message("系统繁忙，请稍后再试");
                removeDis();
            }
        });
    },
    allright: true,
    erroralert: function (obj, text) {
        obj.data('group-state', false);
        message(text);
    },
    success: function (obj) {
        obj.data('group-state', true);
    },
    checkstate: function () {//检查所有状态
        rule.allright = true;
        $.each($('*[data-group-state]'), function (item) {
            if ($(this).attr('onblur')) {
                eval($(this).attr('onblur'));
            } else {
                eval($(this).data('isblur'));
            }
            if (!$(this).data('group-state')) {
                rule.allright = false;
                return false;
            }

        })
        return rule.allright;
    },
    empty: function (obj, mess) {//不能为空
        var str = obj.val().replace(/(^\s+)|(\s+$)/g, "");
        if (str == '') {
            this.erroralert(obj, mess)
        } else {
            this.success(obj);
        }
    },
    phone: function (obj, callback) {//手机号校验
        var myReg = /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/; 
        if (!myReg.test(obj.val())){
            this.erroralert(obj, '手机号格式不正确')
        } else {
            if (callback){
                callback(obj)
            }else {
                this.success(obj);
            }
            ;
        }
    },
    password: function (obj, obj2) {//密码校验
        var myReg = /^[0-9a-zA-Z]{8,20}$/;
        if (!myReg.test(obj.val())) {
            this.erroralert(obj, '密码格式不正确')
        } else {
            this.success(obj);
        }
        if (obj2 && obj2.val() != '') {
            eval(obj2.attr('onblur'));
        }
    },
    tradingPassword: function (obj, obj2, callback) {//交易密码校验
        var myReg = /^[0-9]{6}$/;
        if (!myReg.test(obj.val())) {
            this.erroralert(obj, '交易密码格式不正确')
        } else {
            if (callback) {
                callback(obj)
            } else {
                this.success(obj);
            }
            ;
        }
        if (obj2 && obj2.val() != '') {
            eval(obj2.attr('onblur'));
        }
    },
    repassword: function (obj, obj2) {//重复密码检验
        if (obj.val() != obj2.val()) {
            this.erroralert(obj, '两次密码不一致')
        }else{
            this.success(obj);
        }
    },
    bankcard: function (obj) {//银行卡校验
        var myReg = /^(\d{16}|\d{19}|\d{18})$/;
        if (!myReg.test(obj.val().replace(/\s+/g, ""))) {
            this.erroralert(obj, '银行卡格式不正确')
        } else {
            this.success(obj);
        }
    },
    idcard: function (obj) {//身份证号校验
        var myReg = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
        if (!myReg.test(obj.val())) {
            this.erroralert(obj, '身份证格式不正确')
        } else {
            this.success(obj);
        }
    },
    custom: function (obj, reg, mess, callback) {
        var myReg = eval(reg);
        if (!myReg.test(obj.val())) {
            this.erroralert(obj, mess)
        } else {
            if (callback) {
                callback(obj)
            } else {
                this.success(obj);
            }
            ;
        }
    },
    dateFormatter: function (val, type) {
        if (type == 1) {
            return new Date(val).format('yyyy-MM-dd hh:mm');
        }
        else {
            return new Date(val).format('yyyy-MM-dd');
        }
    }

}

var getcode = {//获取手机验证码
    wait: 60,
    ajax: function (o) {
        var obj = $('#phone');
        var _this = this;
        var phone = obj.val();
        eval(obj.attr('onblur'));
        if (obj.data('group-state')){
            var code = '';
            if($('#code').length) {
                code = '&code=' + $('#code').val();
            }
            this.time(o);
            $.ajax({
                url: o.data('url') + code,
                type: 'post',
                dataType: "json",
                data: {phone: phone},
                success: function (data) {
                	message(data.result);
                },
                error: function (){
                	message('网络连接失败');
                }
            });
        }
    },
    time: function (o) {
        if (this.wait == 0) {
            o.removeClass('btn-disabled');
            o.text('获取验证码')
            this.wait = 60;
        } else {
            o.addClass('btn-disabled');
            o.text('重新发送(' + this.wait + ')');
            this.wait--;
            var _this = this;
            setTimeout(function () {
                _this.time(o)
            }, 1000)
        }
    }
}

$(function () {
    $('#getcode').on({
        click: function () {
            if (!$(this).hasClass('btn-disabled')) {
                getcode.ajax($(this))
            }
        }
    }, '.btn-send');
})


var _addDataonce = true, page_pathname, totalheight = 0;
maxnum = 2, range = 50, this_page = 1;
;

function addData(arg, callback) {
    var _this = this
    _this.prototype = {
        getPathName: function () {
            page_pathname = location.pathname.replace(/\/.*\/(.*)\..*/g, '$1');
        },
        getHistory: function (name) {
            return localStorage[page_pathname + name] || false;
        },
        setHistory: function (name, str) {
            localStorage[page_pathname + name] = str;
        },
        setAllHistory: function (html, res, page, maxnum, more) {
            var isTop = document.body.scrollTop;
            _this.prototype.setHistory("isTop", isTop);
            _this.prototype.setHistory("mainBoxHtml", html);
            _this.prototype.setHistory("isRes", res);
            _this.prototype.setHistory("isPage", page);
            _this.prototype.setHistory("isMaxnum", maxnum);
            _this.prototype.setHistory("isMore", more);
            _this.prototype.setHistory("isJump", '1');
        },
        clearHistory: function () {
            _this.prototype.setHistory("isJump", '0');
        },
        throttle: function (method, context) {
            clearTimeout(method.tId);
            method.tId = setTimeout(function () {
                method.call(context);
            }, 120);
        },
        func: function () {
            _this.prototype.throttle(_this.prototype.myFunc, window);
        },
        ajax: function (arg, _page) {
            var _obj = arg.mainbox.next('.loading_more'), _data = $.extend(arg.res, {pageIndex: _page});
            $.ajax({
                url: arg.url,
                type: 'post',
                dataType: 'json',
                data: _data,
                beforeSend: function () {
                    _obj.remove();
                    arg.mainbox.after('<div class="loading_more"><i class="icon-loading"></i>数据加载中...</div>');
                    _obj = $(".loading_more");
                },
                success: function (result) {
                    arg.success(result, arg.mainbox, arg.isrefresh);
                    arg.isrefresh = false;
                    if (!result.data.totalCount) {
                        _obj.html('<div class="gray">暂无数据</div>')
                    } else {
                        maxnum = result.data.totalPage;
                        if (this_page < maxnum) {
                            _obj.html('<i class="icon-loading"></i>数据加载中...');
                        } else {
                            _obj.html('<div class="gray">已经到底了</div>')

                        }
                    }
                    if (callback) callback();
                },
                error: function (data) {
                	message('网络连接失败', '', '', 1200, {className: 'favorpop'}, false)

                }
            })
        },
        scrolled: function () {
            totalheight = parseFloat($(window).scrollTop()) + parseFloat($(window).height());
            if (totalheight >= ($(document).height() - range) && this_page < maxnum) {
                _this.prototype.func()
            }
        },
        myFunc: function () {
            this_page++;
            _this.prototype.ajax(arg, this_page)
        },
        setclick: function () {
            arg.mainbox.on("click", "a", function () {
                event.preventDefault();
                var href = $(this).attr("href");
                href ? location.href = href : null;
                _this.prototype.setAllHistory(arg.mainbox.html(), JSON.stringify(arg.res), this_page, maxnum, arg.mainbox.next().html());
            })
        },
        init: function () {
            if (_addDataonce) {
                if (localStorage[page_pathname + 'isJump'] == '1') {
                    var isMore;
                    _this.prototype.getHistory('isMore') == "undefined" ? isMore = '' : isMore = _this.prototype.getHistory('isMore');
                    arg.mainbox.html(_this.prototype.getHistory('mainBoxHtml')).after('<div class="loading_more">' + isMore + '</div>');
                    this_page = parseInt(_this.prototype.getHistory('isPage'));
                    var isTop = parseInt(_this.prototype.getHistory('isTop'));
                    $(window).scrollTop(isTop);
                    maxnum = parseInt(_this.prototype.getHistory('isMaxnum'));
                    arg.res = JSON.parse(_this.prototype.getHistory("isRes"));
                    delete arg.res["pageIndex"];
                    $(function () {
                        for (var key in arg.res) {
                            $.each($('ul li[data-' + key + ']'), function (n, v) {
                                if ($(this).data(key.toLowerCase()) == arg.res[key]) {
                                    $(this).addClass('active');
                                } else {
                                    $(this).removeClass('active');
                                }
                            })

                        }
                    })

                    _this.prototype.clearHistory()

                } else {
                    _this.prototype.ajax(arg, this_page)
                }

                $(document).scroll(function () {
                    _this.prototype.scrolled()
                });
                _addDataonce = false;

            } else {

                this_page = 1
                _this.prototype.ajax(arg, this_page)
            }

        }

    };

    this.prototype.getPathName();
    this.prototype.setclick();
    this.prototype.init();
    return this;

}