/*
 * cpc global JavaScript Library v1.2.0
 * Copyright 2011-2014, Guang.com
 * @contain: 前端基础功能插件
 * @depends: jquery.js
 * Includes json2.js
 */
var JSON;
if (!JSON) {
    JSON = {};
}
(function() {
    'use strict';

    function f(n) {
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function(key) {
            return isFinite(this.valueOf()) ? this.getUTCFullYear() + '-' +
                f(this.getUTCMonth() + 1) + '-' +
                f(this.getUTCDate()) + 'T' +
                f(this.getUTCHours()) + ':' +
                f(this.getUTCMinutes()) + ':' +
                f(this.getUTCSeconds()) + 'Z' : null;
        };

        String.prototype.toJSON =
            Number.prototype.toJSON =
            Boolean.prototype.toJSON = function(key) {
                return this.valueOf();
            };
    }

    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        gap,
        indent,
        meta = { // table of character substitutions
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"': '\\"',
            '\\': '\\\\'
        },
        rep;


    function quote(string) {

        escapable.lastIndex = 0;
        return escapable.test(string) ? '"' + string.replace(escapable, function(a) {
            var c = meta[a];
            return typeof c === 'string' ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' : '"' + string + '"';
    }


    function str(key, holder) {
        var i, // The loop counter.
            k, // The member key.
            v, // The member value.
            length,
            mind = gap,
            partial,
            value = holder[key];

        if (value && typeof value === 'object' &&
            typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }

        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }

        switch (typeof value) {
            case 'string':
                return quote(value);

            case 'number':
                return isFinite(value) ? String(value) : 'null';

            case 'boolean':
            case 'null':
                return String(value);

            case 'object':
                if (!value) {
                    return 'null';
                }

                gap += indent;
                partial = [];

                if (Object.prototype.toString.apply(value) === '[object Array]') {

                    length = value.length;
                    for (i = 0; i < length; i += 1) {
                        partial[i] = str(i, value) || 'null';
                    }

                    v = partial.length === 0 ? '[]' : gap ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']' : '[' + partial.join(',') + ']';
                    gap = mind;
                    return v;
                }

                if (rep && typeof rep === 'object') {
                    length = rep.length;
                    for (i = 0; i < length; i += 1) {
                        if (typeof rep[i] === 'string') {
                            k = rep[i];
                            v = str(k, value);
                            if (v) {
                                partial.push(quote(k) + (gap ? ': ' : ':') + v);
                            }
                        }
                    }
                } else {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = str(k, value);
                            if (v) {
                                partial.push(quote(k) + (gap ? ': ' : ':') + v);
                            }
                        }
                    }
                }

                v = partial.length === 0 ? '{}' : gap ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}' : '{' + partial.join(',') + '}';
                gap = mind;
                return v;
        }
    }

    if (typeof JSON.stringify !== 'function') {
        JSON.stringify = function(value, replacer, space) {
            var i;
            gap = '';
            indent = '';

            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }

            } else if (typeof space === 'string') {
                indent = space;
            }

            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                (typeof replacer !== 'object' ||
                    typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }

            return str('', {
                '': value
            });
        };
    }

    if (typeof JSON.parse !== 'function') {
        JSON.parse = function(text, reviver) {
            var j;

            function walk(holder, key) {
                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }

                return reviver.call(holder, key, value);
            }

            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function(a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }

            if (/^[\],:{}\s]*$/
                .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                    .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                    .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

                j = eval('(' + text + ')');

                return typeof reviver === 'function' ? walk({
                    '': j
                }, '') : j;
            }
            throw new SyntaxError('JSON.parse');
        };
    }
}());
(function($) {
    $.cpc = $.cpc || {
        version: 'v1.0.0'
    };
    $.extend($.cpc, {
        util: {
            // 校验手机号码合法性
            isPhoneNumber: function(chars) {
                // var re = /^(0|86|17951)?(13[0-9]|15[0-9]|17[678]|18[0-9]|14[57])[0-9]{8}$/;
                var re = /^1[0-9]{10}$/;
                if (!re.test(chars)) {
                    return false;
                } else {
                    return true;
                }
            },

            // 获取url参数名
            getUrlParam: function(paramName) {
                var reg = new RegExp('(^|&)' + paramName + '=([^&]*)(&|$)');
                var r = window.location.search.substr(1).match(reg);
                if (r != null) {
                    return window.unescape(r[2]);
                }

                return null;
            },

            // 判断是否ie6
            isIE6: function() {
                return ($.browser.msie && $.browser.version == '6.0') ? true : false;
            },

            // 判断是否Chrome
            isChrome: function() {
                return navigator.userAgent.toLowerCase().match(/chrome/) != null;
            },

            // 判断是否是苹果手持设备
            isIOS: function() {
                return /\((iPhone|iPad|iPod)/i.test(navigator.userAgent);
            },

            // 获取字符串中文长度
            getStrLength: function(str) {
                str = $.trim(str);
                var theLen = 0,
                    strLen = str.replace(/[^\x00-\xff]/g, '**').length;
                theLen = parseInt(strLen / 2) == strLen / 2 ? strLen / 2 : parseInt(strLen / 2) + 0.5;
                return theLen;
            },

            // 截取一定长度的中英文字符串并转全角
            substring4ChAndEn: function(str, maxLength) {
                var strTmp = str.substring(0, maxLength * 2);
                while ($.cpc.util.getStrLength(strTmp) > maxLength) {
                    strTmp = strTmp.substring(0, strTmp.length - 1);
                }

                return strTmp;
            },

            // 将<>"'&符号转换成全角
            htmlToTxt: function(str) {
                var RexStr = /\<|\>|\"|\'|\&/g;
                str = str.replace(RexStr, function(MatchStr) {
                    var newstr = '';
                    switch (MatchStr) {
                        case '<':
                            newstr = '＜';
                            break;
                        case '>':
                            newstr = '＞';
                            break;
                        case '\"':
                            newstr = '＼';
                            break;
                        case '\'':
                            newstr = '＇';
                            break;
                        case '&':
                            newstr = '＆';
                            break;
                        default:
                            break;
                    }
                    return newstr;
                });
                return str;
            },

            // 截取一定长度的字符串
            ellipse: function(str, len) {
                var boolLimit = $.cpc.util.getStrLength(str) * 2 > len;
                if (str && boolLimit) {
                    return str.replace(new RegExp('([\\s\\S]{' + len + '})[\\s\\S]*'), '$1…');
                }

                return str;
            },

            // 校验是否为空
            isEmpty: function(v) {
                return $.trim(v) == '' ? true : false;
            },

            // 校验邮箱是否合法
            isEmail: function(v) {
                return /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(v);
            },

            // 校验昵称是否合法
            isNick: function(v) {
                return /^[a-zA-Z\d\u4e00-\u9fa5_-]*$/.test(v);
            },
            nickMin: function(v) {
                var l = $.cpc.util.getStrLength(v) * 2;
                return l < 4 ? false : true;
            },
            nickMax: function(v) {
                var l = $.cpc.util.getStrLength(v) * 2;
                return l > 30 ? false : true;
            },

            // 校验字符长度不超过某个长度
            tooShort: function(v, l) {
                return v.length < l ? false : true;
            },

            // 校验是否包含网址
            noLink: function(v) {
                var matchURL = v.match(/(http[s]?:\/\/)?[a-zA-Z0-9-]+(\.[a-zA-Z0-9]+)+/);
                return matchURL == null ? true : false;
            },

            // 获取dom对象的当前位置
            getPosition: function(ele) {
                var top = ele.offset().top,
                    left = ele.offset().left,
                    bottom = top + ele.outerHeight(),
                    right = left + ele.outerWidth(),
                    lmid = left + ele.outerWidth() / 2,
                    vmid = top + ele.outerHeight() / 2;

                // iPad position fix
                if (/iPad/i.test(navigator.userAgent)) {
                    top -= $(window).scrollTop();
                    bottom -= $(window).scrollTop();
                    vmid -= $(window).scrollTop();
                }

                var position = {
                    leftTop: function() {
                        return {
                            x: left,
                            y: top
                        };
                    },
                    leftMid: function() {
                        return {
                            x: left,
                            y: vmid
                        };
                    },
                    leftBottom: function() {
                        return {
                            x: left,
                            y: bottom
                        };
                    },
                    topMid: function() {
                        return {
                            x: lmid,
                            y: top
                        };
                    },
                    rightTop: function() {
                        return {
                            x: right,
                            y: top
                        };
                    },
                    rightMid: function() {
                        return {
                            x: right,
                            y: vmid
                        };
                    },
                    rightBottom: function() {
                        return {
                            x: right,
                            y: bottom
                        };
                    },
                    MidBottom: function() {
                        return {
                            x: lmid,
                            y: bottom
                        };
                    },
                    middle: function() {
                        return {
                            x: lmid,
                            y: vmid
                        };
                    }
                };
                return position;
            },

            // 获取根域名
            getDomain: function(url) {
                var domain = 'null';
                var regex = /[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\.?/;
                var match = regex.exec(url);
                if (typeof match != 'undefined' && null != match) {
                    domain = match[0];
                }

                return domain;
            },
            openWin: function(url) {
                var top = 190;
                var left = (document.body.clientWidth - 580) / 2;
                window.open(url, 'connect_window', 'height=420, width=580, toolbar=no, menubar=no, scrollbars=yes, resizable=no,top=' + top + ',left=' + left + ', location=no, status=no');
            },
            submitByEnter: function(e, clk) {
                e = e || window.event;
                var key = e ? (e.charCode || e.keyCode) : 0;
                if (key == 13) {
                    clk();
                }
            },

            // 校验数据是否为空
            isNULL: function(chars) {
                if (chars == null) {
                    return true;
                }

                if ($.trim(chars).length == 0) {
                    return true;
                }

                return false;
            },

            // 校验URL的合法性
            isValidURL: function(chars) {
                var re = /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(\S+\.\S+)$/;
                if (!$.cpc.util.isNULL(chars)) {
                    chars = $.trim(chars);
                    if (chars.match(re) == null) {
                        return false;
                    } else {
                        return true;
                    }
                }

                return false;
            },

            // 制保留2位小数，如：2，会在2后面补上00.即2.00
            toDecimal2: function(x) {
                var f = parseFloat(x);
                if (isNaN(f)) {
                    return false;
                }

                f = Math.round(x) / 100;
                var s = f.toString();
                var rs = s.indexOf('.');
                if (rs < 0) {
                    rs = s.length;
                    s += '.';
                }

                while (s.length <= rs + 2) {
                    s += '0';
                }

                return s;
            },

            // 是否是数字
            isNum: function(x) {
                var f = parseFloat(x);
                if (isNaN(f)) {
                    return false;
                } else {
                    return true;
                }
            },

            // 加密
            encrypt: function(pwd) {
                var aeskey = 'YWl4dWVkYWk0MDAtODY3MQ==',
                    key = CryptoJS.enc.Base64.parse(aeskey),
                    iv = CryptoJS.enc.Base64.parse(aeskey);

                return CryptoJS.AES.encrypt(pwd, key, {
                    iv: iv,
                    mode: CryptoJS.mode.CBC,
                    padding: CryptoJS.pad.ZeroPadding
                }).toString();
            }
        }
    });

    $.fn.extend({
        // 返回顶部
        returntop: function() {
            if (!this[0]) {
                return;
            }

            var backToTopEle = this.click(function() {
                $('html, body').animate({
                    scrollTop: 0
                }, 500);
                var topH = $(window).height() + 80 + 'px';
                backToTopEle.data('isClick', true);
                backToTopEle.css('bottom', topH);
            });
            var showEle = function() {
                if (backToTopEle.data('isClick')) {} else {
                    backToTopEle.css({
                        opacity: 1,
                        bottom: '200px'
                    });
                }
            };

            var timeDelay = null;
            var backToTopFun = function() {
                var docScrollTop = $(document).scrollTop();
                var winowHeight = $(window).height();
                (docScrollTop > 0) ? showEle(): backToTopEle.css({
                    opacity: 0,
                    bottom: '-200px'
                }).data('isClick', false);

                // IE6下的定位
                if ($.cpc.util.isIE6()) {
                    backToTopEle.hide();
                    clearTimeout(timeDelay);
                    timeDelay = setTimeout(function() {
                        backToTopEle.show();
                        clearTimeout(timeDelay);
                    }, 1000);
                    backToTopEle.css('top', docScrollTop + winowHeight - 125);
                }
            };

            $(window).bind('scroll', backToTopFun);
        },

        // 等比例缩放图片
        resizeImage: function(width, height) {
            this.each(function() {
                var obj = $(this)[0];
                var w = obj.width;
                var h = obj.height;
                if (w <= width && h <= height) {
                    return;
                } else if (w <= width && h > height) {
                    obj.width = w * height / h;
                    obj.height = height;
                } else if (w > width && h <= height) {
                    obj.width = width;
                    obj.height = h * width / w;
                } else {
                    obj.width = width;
                    obj.height = h * width / w;
                    var temp = h * width / w;
                    if (temp > height) {
                        obj.width = w * height / h;
                        obj.height = height;
                    }
                }
            });
        },

        // 文本框高度自适应
        textareaAutoHeight: function() {
            var obj = this;
            var h = obj.outerHeight();
            var func = function() {
                h < 0 && (h = obj.outerHeight());
                if ($.browser.mozilla || $.browser.safari) {
                    obj.height(h);
                }

                var sh = obj[0].scrollHeight,
                    autoHeight = sh < h ? h : sh;
                autoHeight = autoHeight < h * 1.5 ? h : sh;
                obj.height(autoHeight);
            };

            obj.bind('keyup input propertychange focus', func);
        },

        // 按钮置为灰色
        disableBtn: function(str) {
            var $btn = this;
            $btn[0].disabled = 'disabled';
            $btn.removeClass(str).addClass('disabled');
        },

        // 开启按钮
        enableBtn: function(str) {
            var $btn = this;
            $btn[0].disabled = '';
            $btn.removeClass('disabled').addClass(str);
        },

        // 下拉菜单
        dropDown: function(options) {
            var settings = {
                event: 'mouseover',
                classNm: '.dropdown',
                timer: null,
                fadeSpeed: 100,
                duration: 500,
                offsetX: 82,
                offsetY: 8,
                isLocation: false
            };
            if (options) {
                $.extend(settings, options);
            }

            var triggers = this,
                $dropDown = $(settings.classNm);

            triggers.each(function() {
                var $this = $(this);
                $this.hover(function() {
                    clearTimeout(settings.timer);
                    $('.dropdown:not(' + settings.classNm + ')').hide();
                    if (settings.isLocation) {
                        var position = $.cpc.util.getPosition($(this)).rightBottom();
                        $dropDown.css({
                            left: position.x - settings.offsetX + 'px',
                            top: position.y + settings.offsetY + 'px'
                        });
                    }

                    $dropDown.fadeIn(settings.fadeSpeed);
                }, function() {
                    settings.timer = setTimeout(function() {
                        $dropDown.fadeOut(settings.fadeSpeed);
                    }, settings.duration);
                });
                $dropDown.hover(function() {
                    clearTimeout(settings.timer);
                    $dropDown.show();
                }, function() {
                    settings.timer = setTimeout(function() {
                        $dropDown.fadeOut(settings.fadeSpeed);
                    }, settings.duration);
                });
            });
        }
    });
})(jQuery);

/*
 * ajax封装
 */
/*(function($) {
    $.cpc.ajax = {
        post: function(url,json,callback) {
            $.ajax({
                url: url,
                type : "post",
                dataType: "json",
                data: json,
                beforeSend: function(){
                    $.cpc.dialog.waiting();
                },
                success: function(d){
                    $("#J_WaitingDialog").data("overlay").close();
                    callback(d);
                },
                error: function(xhr){

                }
            });
        }
    }

})(jQuery);*/

/*
 * 对话框
 * $.cpc.dialog.init({
 *     id: "J_RechargeD",
 *     html: ""
 * });
 */
(function($) {
    $.cpc.dialog = {
        conf: {
            id: 'J_GCRM',
            title: '提示',
            className: '',
            html: '',
            width: '',
            height: '',
            default: true,
            buttonTxt: {
                save: '提 交',
                cancel: '取 消'
            },
            draggable: true,
            delDialog: false,
            callback: function() {},
            submit: function() {
                return false;
            },
            cancel: function() {
                return false;
            }
        },
        init: function(opt) {
            var conf = $.cpc.dialog.conf;
            if (opt) {
                $.extend(conf, opt);
            }

            var $body = $('body'),
                dialogId = conf.id,
                $dialog = $('#' + dialogId);

            if (conf.diaReload == true) { // 重新加载弹出层
                $dialog.remove();
            }

            if (!$dialog.length) {
                var html = '';
                html += '<div id="' + dialogId + '" class="g-dialog ' + conf.className + '">';
                html += '<div class="con bigfs">';
                html += '<div class="hd">';
                html += '<h3>' + conf.title + '</h3>';
                html += '<a class="close J_Close" href="javascript:;" title="关闭"></a>';
                html += '</div>';
                html += '<div class="bd">';
                if (conf.default) {
                    html += conf.html;
                    html += '<div class="action tac">';
                    if (conf.buttonTxt.save) {
                        html += '<input type="submit" value="' + conf.buttonTxt.save + '" class="btn confirm J_Save">';
                    }
                    if (conf.buttonTxt.cancel) {
                        html += '<input type="reset" value="' + conf.buttonTxt.cancel + '" class="btn cancel ml20 J_Cancel">';
                    }
                    html += '</div>';
                } else {
                    html += conf.html;
                }

                html += '</div>';
                html += '</div>';
                html += '</div>';

                $body.addClass('overflow-hidden').append(html);

                // 重新页面获取
                $dialog = $('#' + dialogId);

                if (conf.width !== '') {
                    $dialog.width(conf.width);
                }

                if (conf.height !== '') {
                    $dialog.find('.bd').height(conf.height);
                }

                $dialog.overlay({
                    top: 'center',
                    mask: {
                        color: '#000',
                        loadSpeed: 200,
                        opacity: 0.3
                    },
                    closeOnClick: false,
                    load: true
                });

                $.cpc.dialog.closedialog = function() {
                    $dialog.data('overlay').close();
                    $body.removeClass('overflow-hidden');

                    if (conf.delDialog) {
                        $dialog.remove();
                    }
                };
                $dialog.find('.J_Close').on('click', function() {
                    $.cpc.dialog.closedialog();
                });
                $dialog.find('.J_Cancel').on('click', function() {
                    if (conf.cancel() == false) {
                        $.cpc.dialog.closedialog();
                    }
                });

                $dialog.find('.J_Save').on('click', function() {
                    if (conf.submit()) {
                        $.cpc.dialog.closedialog();
                    }
                });

                if (conf.draggable) {
                    $dialog.drags({
                        handle: '.hd'
                    });
                }

                // 回调函数
                conf.callback();
            } else {
                $dialog.data('overlay').load();
            }
        }
    };
})(jQuery);

/*
 * 提示框
 * $.cpc.tip.conf.tipClass = "tipmodal-ok";
 * $.cpc.tip.show($this,'<span class="icon-ok"></span>成功提示!');
 */
(function($) {
    $.cpc.tip = {
        conf: {
            timer: null,
            timerLength: 3000,
            tipClass: ''
        },
        show: function(o, text) {
            clearTimeout($.cpc.tip.conf.timer);
            var position = $.cpc.util.getPosition(o).topMid();
            if (!$('.g-tip')[0]) {
                $('body').append('<div class="g-tip"></div>');
            }

            $('.g-tip').attr('class', 'g-tip ' + $.cpc.tip.conf.tipClass);
            $('.g-tip').html(text);
            var W = $('.g-tip').outerWidth(),
                H = $('.g-tip').outerHeight();

            $('.g-tip').css({
                left: position.x - W / 2 + 'px',
                top: position.y - H - 5 + 'px'
            }).fadeIn();
            $.cpc.tip.conf.timer = setTimeout(function() {
                $('.g-tip').fadeOut();
            }, $.cpc.tip.conf.timerLength);
        }
    };
})(jQuery);
/*
 * ajaxt调试模式
 */
(function($) {
    $.cpc.ajax = {
        conf: {
            test: false,
            callback: function() {
                alert('操作成功');
                location.reload();
            }
        },
        init: function(data, url, fn, test) {
            if (test) {
                data.url = url;
                console.log(JSON.stringify(data));
                alert(JSON.stringify(data));
                return false;
            }
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                data: data,
                success: function(data) {
                    var result = data.ret || data;
                    if (result.success) {
                        if ($.isFunction(fn)) {
                            fn(data);
                        } else {
                            $.cpc.ajax.conf.callback();
                        }
                    } else {
                        alert(result.resultDes);
                    }
                },
                error: function() {
                    alert('服务器出错了');
                }
            });
        }
    };
})(jQuery);

$(function() {
    // 全选
    $('.checkAll').click(function() {
        if ($(this).attr('checked') == 'checked') {
            $('[name=product]:checkbox').attr('checked', true);
            $('[name=productAll]:checkbox').attr('checked', true);
        } else {
            $('[name=product]:checkbox').attr('checked', false);
            $('[name=productAll]:checkbox').attr('checked', false);
        }
    });

    // 多选
    var trLen = $('.table tbody').find('tr').length - 1;
    $('[name=product]:checkbox').click(function() {
        var checkLen = $('input[name=product]:checked').length;
        if (trLen == checkLen) {
            $('[name=productAll]:checkbox').attr('checked', true);
        } else {
            $('[name=productAll]:checkbox').attr('checked', false);
        }
    });
});


/**
 * 排序通用
 * by Min 2015/06/23
 * usage:
 * HTML: <input class="J_sortInput" type="text" data-id="xxx" />
 * JS：$(".J_sortInput").updateSort({ saveUrl: "/app/saveSort.do", AutoRefesh: false });
 */

+ (function($) {
    'use strict';

    var updateSort = function(element, options) {
        this.$elem = $(element);
        this.options = $.extend({}, updateSort.DEFAULTS, options);

        this.init();
    };

    updateSort.DEFAULTS = {
        saveUrl: '#',
        AutoRefesh: true
    };

    updateSort.prototype.init = function() {
        var self = this;

        var url = self.options.saveUrl;

        self.$elem.on('change', function(e) {
            var id = self.$elem.data('id');
            var orderby = $(this).val();
            if (!orderby.match(/^\d+$/)) {
                $(this).val('');
                return;
            }

            var data = {
                id: id,
                orderby: orderby
            };
            saveData(self, url, data);
        });
        self.$elem.on('blur', function(e) {
            var id = self.$elem.data('id');
            var orderby = $(this).val();
            if ($(this).val() === '') {
                alert('请输入排序号');

                // $(this).focus();
                return;
            }
        });
    };

    function saveData(self, url, data) {

        $.ajax({
            url: url,
            type: 'post',
            dataType: 'json',
            data: data,
            success: function(data) {
                if (data.ret.success) {
                    if (self.options.AutoRefesh) {
                        window.location.reload();
                    }

                    alert('保存排序成功');
                } else {
                    alert('保存排序失败');
                }
            },
            error: function() {
                alert('服务器出错了');
            }
        });
    }

    $.fn.updateSort = function(option) {
        return this.each(function() {
            var $this = $(this);
            var data = $this.data('axd.updateSort');
            var options = typeof option == 'object' && option;

            if (!data) {
                $this.data('axd.updateSort', (data = new updateSort(this, options)));
            }
        });
    };

})(jQuery);

/**
 * 删除, 下线，置顶
 * by Min 2015/06/23
 * usage:
 * HTML: <a href="javascript:;" data-id="20010" data-name="是否删除：2名称" class="btn del mb10 J_Del">删除</a>
 * JS：$(".J_Del, .J_DownLine, .J_CommonOperate").CommonOperate({ Url: '/app/delete.do', AutoRefesh: 'true' });
 *
 * OR
 * * HTML: <a href="javascript:;" axd-type="CommonOperate" data-url="/app/delete.do" data-autorefresh="true" data-id="20010" data-name="是否删除：2名称" class="btn del mb10 J_Del">删除</a>
 */
+ (function($) {
    'use strict';

    var CommonOperate = function(element, options) {
        this.$elem = $(element);
        this.options = $.extend({}, CommonOperate.DEFAULTS, options);

        if (!options.go) {
            this.init();
        }

    };

    CommonOperate.DEFAULTS = {
        Url: '#',
        AutoRefesh: true
    };

    CommonOperate.prototype.init = function() {
        var self = this;
        self.$elem.on('click', function(e) {
            self.go();
        });

    };

    CommonOperate.prototype.go = function() {
        var self = this,
            data = {},
            url = self.options.Url;
        var text = self.$elem.data('name');
        data.id = self.$elem.data('id');

        if (confirm(text)) {
            // $.cpc.ajax.conf.callback = function() {
            //     location.reload();
            // };
            $.cpc.ajax.init(data, url);
        }
    };

    var Plugin = function(option) {
        return this.each(function() {
            var $this = $(this);
            var data = $this.data('axd.CommonOperate');
            var options = typeof option == 'object' && option;

            if (!data) {
                $this.data('axd.CommonOperate', (data = new CommonOperate(this, options)));
            }

            if (typeof option == 'string') {
                data[option]();
            } else if (options.go) {
                data.go();
            }

        });
    };

    $.fn.CommonOperate = $.fn.CommonDel = Plugin;

    $(document).on('click', '[axd-type="CommonOperate"]', function() {
        var options = {
            go: true,
            Url: $(this).data('url'),
            AutoRefesh: $(this).data('autorefresh')
        };
        Plugin.call($(this), options);
    });

})(jQuery);


/*
 * 封装drag拖动
 * by Min 2015/06/27
 * usage: $('.myDiv').drags();
 */
(function($) {
    'use strict';
    $.fn.drags = function(opt) {
        opt = $.extend({
            handle: '',
            cursor: 'move',
            draggableClass: 'draggable',
            activeHandleClass: 'active-handle'
        }, opt);

        var $selected = null;
        var self = this;
        var $elements = (opt.handle === '') ? this : this.find(opt.handle);

        $elements.css('cursor', opt.cursor).on('mousedown', function(e) {
            if (opt.handle === '') {
                $selected = $(this);
                $selected.addClass(opt.draggableClass);
            } else {
                $selected = $(self);
                $selected.addClass(opt.draggableClass).find(opt.handle).addClass(opt.activeHandleClass);
            }

            var drgH = $selected.outerHeight(),
                drgW = $selected.outerWidth(),
                posY = $selected.offset().top + drgH - e.pageY,
                posX = $selected.offset().left + drgW - e.pageX;
            $(document).on('mousemove', function(e) {
                $selected.offset({
                    top: e.pageY + posY - drgH,
                    left: e.pageX + posX - drgW
                });
            }).on('mouseup', function() {
                $(this).off('mousemove'); // Unbind events from document
                if ($selected !== null) {
                    $selected.removeClass(opt.draggableClass);
                    $selected = null;
                }
            });
            e.preventDefault(); // disable selection
        }).on('mouseup', function() {
            if (opt.handle === '') {
                $selected.removeClass(opt.draggableClass);
            } else {
                $selected.removeClass(opt.draggableClass).find(opt.handle).removeClass(opt.activeHandleClass);
            }

            $selected = null;
        });

        return this;

    };
})(jQuery);

/*
 * 图片放大
 * by Min 2015/07/20
 * usage: $('.myDiv').enlarge();
 */
(function($) {
    'use strict';
    var DEFAULT = {

        layerwidth: 500,
        layerheight: 500,
        layerborder: '#DDD',
        fade: true
    };

    $.fn.enlarge = function(options) {
        var option = $.extend({}, DEFAULT, options);
        return this.each(function() {

            var self = $(this).css('position', 'relative');
            var img = self;

            var $layer = $('#J_enlarge_Div');
            if (!$layer[0]) {

                var $large = $('<img>').css({
                    position: 'absolute',
                    display: 'block',
                    'max-width': option.layerwidth,
                    'max-height': option.layerwidth
                });
                $layer = $('<div>').css({
                    position: 'absolute',
                    left: self.width() + 5,
                    top: 0,
                    'z-index': 10001,
                    padding: '8px',
                    'background-color': '#fff',
                    overflow: 'hidden',
                    width: option.layerwidth,
                    height: option.layerheight,
                    'border-radius': '3px',
                    border: '5px solid ' + option.layerborder
                });

                $large.attr('src', img.attr('src'));
                $layer.attr('id', 'J_enlarge_Div');
                $large.appendTo($layer);
                $layer.hide().appendTo('body');
            }


            var offset = self.offset();
            var show = function() {
                offset = self.offset();

                var x = offset.left;
                var y = offset.top;

                $layer.css({
                    left: (x + 150),
                    top: (y - 100)
                });
                $layer.find('img').attr('src', img.attr('src'))
                    .on('load', function() {
                        $(this).data('width', this.width);
                        $(this).data('height', this.height);

                        $layer.css({
                            width: this.width,
                            height: this.height
                        });

                    });
                if (option.fade) {
                    $layer.stop().fadeIn(300);
                } else {
                    $layer.show();
                }
            };

            var hide = function() {
                $layer.hide();
            };

            self.mousemove().mouseenter(show).mouseleave(hide);
        });
    };
})(jQuery);

/**
 * 自动根据axd-form=“ajax” 自动提交表单
 * @param  {[type]} $ [description]
 * @return {[type]}   [description]
 */
(function($) {
    'use strict';
    $(function() {
        if ($('[axd-form="ajax"]').length > 0) {
            $('[axd-form="ajax"]').ajaxForm(function(data) {
                if (data.ret.success) {
                    alert(data.ret.resultDes);
                    window.location.href = document.referrer;
                } else {
                    alert(data.ret.resultDes);
                }
            });
        }
    });
})(jQuery);

(function($) {
    /**
     * by wzy 2016/11/02
     * [tipAjax 提示交互事件]
     * @param  {[Object]} opts [传参]
     * tip  提示文字，默认空字符串无提示弹窗
     * param  ajax的data
     * url    ajax的url
     * callback  回调函数，默认执行刷新页面reload
     */
    $.fn.tipAjax = function(opts) {
        var def = {
            tip: '',
            param: {},
            url: '',
            callback: null
        };
        opts = $.extend({}, def, opts);
        return this.each(function() {
            var $that = $(this);
            if (opts.tip == '') {
                $.cpc.ajax.init(opts.param, opts.url, opts.callback);
            } else {
                if (confirm(opts.tip)) {
                    $.cpc.ajax.init(opts.param, opts.url, opts.callback);
                }
            }
        });
    };

    /*
     *时间区间
     *  $start  开始时间input的jquery对象
     *  $end    结束时间input的jquery对象
     *时间区间有个bug未解决，暂时隐藏区间效果
     */
    $.cpc.timeZone = function($start, $end) {
        $start.datetimepicker({
            timeFormat: 'HH:mm:ss',
            dateFormat: 'yy-mm-dd'
            /*onSelect: function(selectedDateTime) {
                $end.datetimepicker('option', 'minDate', $start.datetimepicker('getDate'));
            }*/
        });
        $end.datetimepicker({
            timeFormat: 'HH:mm:ss',
            dateFormat: 'yy-mm-dd'
            /*onSelect: function(selectedDateTime) {
                $start.datetimepicker('option', 'maxDate', $end.datetimepicker('getDate'));
            }*/
        });
    };
    /*
     *图片上传
     *$btn      上传按钮jquery对象
     *$img      图片jquery对象
     *callback  回调函数
     */
    $.cpc.uploadify = function($btn, $img, callback) {
        $btn.fileupload({
            url: '/uploadimg',
            dataType: 'json',
            submit: function(e, data) {
                // 上传图片的参数
                var size = $btn.data('imgsize');
                if (size) {
                    var imgwidth = size.split('-')[0];
                    var imgheight = size.split('-')[1];
                    data.formData = {
                        imgwidth: imgwidth,
                        imgheight: imgheight
                    };
                }

                data.paramName = 'imageFile';
            },
            done: function(e, data) {
                if (data.result.ret.success) {
                    $.cpc.tip.conf.tipClass = 'tipmodal-ok';
                    var imgUrl = data.result.ret.result;
                    $img.attr('src', imgUrl);
                    if (callback) {
                        callback(imgUrl);
                    }
                } else {
                    var tipText = data.result.ret.resultDes;
                    $.cpc.tip.conf.tipClass = 'tipmodal-error';
                    $.cpc.tip.show($(this), '<span class="icon-ok"></span>' + tipText + '!');
                }
            },
            fail: function(e, data) {
                $.cpc.tip.conf.tipClass = 'tipmodal-error';
                $.cpc.tip.show($(this), '<span class="icon-fail"></span>上传失败!');
            }
        });
    };
})(jQuery);

(function($) {
    $.cpc.uploadImg = function(uploader, callback) {
        var uploadAction = function(uploader) {
            $(uploader).fileupload({
                url: '/uploadimg',
                dataType: 'json',
                start: function(event, data) {
                    var $uploadBox = $(this).closest('div'),
                        $loading = $uploadBox.next('.J_loading');

                    if (!$loading[0]) {
                        $('<p class="dib vt J_loading" style="margin:7px 0 0 10px;color:#ff8600;">正在提交，请稍等...</p>').insertAfter($uploadBox);
                    }
                },
                submit: function(event, data) {
                    // 上传的图片的参数
                    var size = $(this).data('imgsize'),
                        imgwidth,
                        imgheight;

                    if (size) {
                        imgwidth = size.split('-')[0];
                        imgheight = size.split('-')[1];

                        data.formData = {
                            imgwidth: imgwidth,
                            imgheight: imgheight
                        };
                    }

                    data.paramName = 'imageFile';
                },
                done: function(event, data) {
                    var $this = $(this),
                        $imgField = $this.next('input[type=hidden]')[0] ? $this.next('input[type=hidden]') : $this.next('input[type=text]'),
                        imgUrl = '',
                        tipText = '';

                    $this.closest('div').next('.J_loading').remove();

                    if (data.result.ret.success) {
                        imgUrl = data.result.ret.result;
                        $imgField.val(imgUrl).parent().siblings('.J_imgPreview, .J-imgPreview').attr('src', imgUrl);
                    } else {
                        tipText = data.result.ret.resultDes;
                        $.cpc.tip.conf.tipClass = 'tipmodal-error';
                        $.cpc.tip.show($this, tipText + '！');
                    }

                    if (typeof callback === 'function') {
                        callback.call(this, data);
                    }
                },
                fail: function(event, data) {
                    $(this).closest('div').next('.J_loading').remove();
                    $.cpc.tip.conf.tipClass = 'tipmodal-error';
                    $.cpc.tip.show($(this), '上传失败！');
                }
            });
        };

        if (typeof uploader === 'string') {
            $(document).on('click', uploader, function() {
                uploadAction(this);
            });
        } else {
            $(uploader).on('click', function() {
                uploadAction(this);
            });
        }
    };
}(jQuery));

(function($) {
    /*
     * 设置 开始时间 与 结束时间（结束时间必须大于开始时间至少一分钟）组
     * 参数：settings  类型：对象
     *                 start  类型：对象（开始时间的相关设置）
     *                        element（DOM元素或jquery对象），开始时间输入框；
     *                        minDate （字符串或日期对象），开始时间的最小值
     *                        dateFormat （字符串），开始时间的格式（可设置为固定值）
     *                 end    类型：对象（结束时间的相关设置）
     *                        element（DOM元素或jquery对象），结束时间输入框；
     *                        minDate （字符串或日期对象），结束时间的最小值
     *                        dateFormat （字符串），结束时间的格式（可设置为固定值）
     * 用法：
     * $.cpc.setTimePickerGroup({
     *     start: {
     *         element: '[name=startTime]',
     *         dateFormat: 'Y-m-d 00:00:00',
     *         minDate: new Date()
     *     },
     *     end: {
     *         element: '[name=endTime]',
     *         dateFormat: 'Y-m-d 23:59:59',
     *         minDate: '2017-01-20'
     *     }
     * });
     *
     * 注意：该函数是基于日期插件 flatpickr.js，确保使用前引入了相关的 css 与 js
     *
     * by CaiHuaguang
     */
    $.cpc.setTimePickerGroup = function(settings) {
        var start = settings.start,
            startInput = start.element,
            startDateFormat = start.dateFormat,
            startMinDate = start.minDate,
            startOnChange = start.onChange,
            startTime = $(startInput)[0] && $(startInput)[0].flatpickr({
                enableTime: !startDateFormat,
                enableSeconds: !startDateFormat,
                minuteIncrement: 1,
                dateFormat: startDateFormat || 'Y-m-d H:i:S',
                minDate: startMinDate
            }),
            end = settings.end,
            endInput = end.element,
            endDateFormat = end.dateFormat,
            endMinDate = end.minDate,
            endOnChange = end.onChange,
            endTime = $(endInput)[0] && $(endInput)[0].flatpickr({
                enableTime: !endDateFormat,
                enableSeconds: !endDateFormat,
                minuteIncrement: 1,
                dateFormat: endDateFormat || 'Y-m-d H:i:S',
                minDate: endMinDate
            }),
            oneMinute = 60000,
            formatTimeNumber = function(num) {
                return num < 10 ? '0' + num : num;
            },

            reset = function() {
                startTime.set('maxDate', '');
                endTime.set('minDate', '');
            },
            formatDate = function(dateStr) {
                if (typeof dateStr !== 'string') {
                    return dateStr;
                }

                return dateStr.replace(/-/g, '/');
            };

        if (startTime) {
            var setEndMinTime = function(endMinTime, theStartTime) {
                if (endMinTime && endMinTime < theStartTime) {
                    endTime.set('minDate', theStartTime);
                }
            };

            setEndMinTime(new Date(endMinDate).getTime(), new Date(formatDate($(startInput).val())).getTime() + oneMinute);

            startTime.config.onChange = function(dateObj, dateStr, instance) {
                dateStr = formatDate(dateStr);

                var startTimeMilliseconds = new Date(dateStr).getTime(),
                    date = new Date(startTimeMilliseconds + oneMinute),
                    year = date.getFullYear(),
                    month = formatTimeNumber(date.getMonth() + 1),
                    day = formatTimeNumber(date.getDate()),
                    hours = formatTimeNumber(date.getHours()),
                    minutes = formatTimeNumber(date.getMinutes()),
                    seconds = formatTimeNumber(date.getSeconds()),
                    endTimeMilliseconds = new Date(formatDate($(endInput).val())).getTime();

                setEndMinTime(new Date(endMinDate).getTime(), startTimeMilliseconds + oneMinute);

                if (endTimeMilliseconds && endTimeMilliseconds < startTimeMilliseconds + oneMinute) {
                    $(endInput).val(year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds);
                }

                if (typeof startOnChange === 'function') {
                    startOnChange(dateObj, dateStr, instance);
                }
            };
        }

        if (endTime) {
            var setStartMaxTime = function(theEndTime) {
                if (theEndTime) {
                    startTime.set('maxDate', theEndTime);
                }
            };

            setStartMaxTime(new Date(formatDate($(endInput).val())).getTime() - oneMinute);

            endTime.config.onChange = function(dateObj, dateStr, instance) {
                dateStr = formatDate(dateStr);

                var endTimeMilliseconds = new Date(dateStr).getTime(),
                    date = new Date(endTimeMilliseconds - oneMinute),
                    year = date.getFullYear(),
                    month = formatTimeNumber(date.getMonth() + 1),
                    day = formatTimeNumber(date.getDate()),
                    hours = formatTimeNumber(date.getHours()),
                    minutes = formatTimeNumber(date.getMinutes()),
                    seconds = formatTimeNumber(date.getSeconds()),
                    startTimeMilliseconds = new Date(formatDate($(startInput).val())).getTime();

                setStartMaxTime(endTimeMilliseconds - oneMinute);

                if (startTimeMilliseconds && startTimeMilliseconds > endTimeMilliseconds - oneMinute) {
                    $(startInput).val(year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds);
                }

                if (typeof endOnChange === 'function') {
                    endOnChange(dateObj, dateStr, instance);
                }
            };
        }

        return {
            reset: reset
        };
    };
}(jQuery));

(function($) {
    /*
     * 模拟浏览器“后退事件”
     * 参数：callback，点击浏览器后退按钮触发的事件的回调函数
     * 用法：
     * $.cpc.onBrowserBack(function() {
     *     // 你的代码
     * });
     *
     * by CaiHuaguang
     */
    $.cpc.onBrowserBack = function(callback) {
        var userAgent = navigator.userAgent.toLowerCase(),
            isSafari = userAgent.indexOf('safari') > -1 && userAgent.indexOf('chrome') < 1,
            referrer = '';

        if (window.history && window.history.pushState) {
            if (isSafari) {
                window.onload = function() {
                    window.isLoaded = 'isLoaded';
                };
            }

            referrer = document.referrer;

            history.pushState('historyPushState', null, null);

            window.onpopstate = function(event) {
                history.pushState(null, null, null);

                if (window.isLoaded === 'isLoaded') {
                    window.isLoaded = '';
                } else {
                    if (history.state === 'historyPushState') {
                        history.forward(); // 自动“前进”一步，否则“前进”按钮可点击
                    } else {
                        if (typeof callback === 'function') {
                            callback(event);
                        }
                    }
                }
            };
        }
    };
}(jQuery));
