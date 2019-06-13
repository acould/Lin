/**
 * aiyoumi 选择省市
 * author:xiaocpc
 */

(function($) {
    $.cpc.city = {
        conf: {
            targetNode: $('.J_Provs'),
            getProvincesUrl: '/common/provinces',
            getCityUrl: '/common/citys',
            ids: $('.J_ids'),
            targetCode: $('.J_divCode')
        },

        init: function(opt) {
            var self = $.cpc.city,
                conf = self.conf;

            if (opt) {
                $.extend(self.conf, opt);
            }

            $.cpc.city.initEvent(conf);
            $('.J_Provs input[name=city]').live('click', function(event) {
                var _this = $(this);
                var id = _this.val();
                var name = _this.parent('label').text();

                if (_this.attr('checked') == undefined) {
                    $.cpc.city.delIds(id, conf);
                    _this.parents('li').find('a').fadeIn();
                } else {
                    _this.parents('li').find('a').fadeOut();
                    $.cpc.city.getId(id, name, conf);
                }

                event.stopPropagation();
            });

            $(document).on('click', '.J_cityCode', function(event) {
                var _this = $(this),
                    id = _this.data('id');

                event.stopPropagation();
                $.cpc.city.getCities(conf, id, _this);
            });

            /*省市弹出框关闭*/
            $(document).on('click', '.J_divCode .close', function(event) {
                $(this).parent('.J_divCode').fadeOut();
            });

            /*选择市*/
            $(document).on('click', '.J_cityCodeName', function(event) {
                var _this = $(this);
                var id = _this.data('id');
                var name = _this.text();

                $.cpc.city.getId(id, name, conf);
            });

            /*删除已经选择市*/
            $(document).on('click', '.J_del', function(event) {
                var _this = $(this);
                var id = _this.parent('li').data('id');

                _this.parent('li').remove();
                $.cpc.city.DelCheckBoxId(id, conf);
            });
        },

        initEvent: function(conf) {
            $.ajax({
                url: conf.getProvincesUrl,
                type: 'post',
                dataType: 'json',
                data: '',
                success: function(data) {
                    if (data.ret.success) {
                        var data = data.ret.result;

                        $.cpc.city.setCityHtml(data, conf);
                    } else {
                        alert('接口请求错误');
                    }
                },
                error: function(data) {
                    alert('服务器出错了');
                }
            });
        },
        setCityHtml: function(data, conf) {
            var html = '';

            html += '<ul class="clearfix">';
            $.each(data, function(index, el) {
                html += '<li>';
                html += '<label><input type="checkbox" name="city" value="' + el.provinceCode + '">  ' + el.province + '</label>';
                html += '<a href="javascript:;" class="J_cityCode" data-id="' + el.provinceCode + '">查看市区</a>';
                html += '</li>';
            });
            html += '</ul>';
            html += '<div class="J_ids clearfix"><ul></ul></div>';

            if (conf.targetNode.html()) {
                conf.targetNode.fadeIn();
            } else {
                conf.targetNode.fadeIn().append(html);
            }
        },
        setCodeHtml: function(data, conf, obj) {
            var html = '',
                top = obj.offset().top,
                left = obj.offset().left;

            if (data.length != 1) {
                conf.targetCode.css({
                    top: top,
                    left: left,
                    display: 'block'
                }).find('.clearfix').html('');
                html += '<ul class="clearfix">';
                $.each(data, function(index, el) {
                    html += '<li><a href="javascript:;" class="J_cityCodeName" data-id="' + el.cityCode + '">' + el.city + '</a></li>';
                });
                html += '</ul>';
                conf.targetCode.find('.clearfix').append(html);
            } else {
                obj.fadeOut();
            }
        },
        getCities: function(conf, id, obj) {
            $.ajax({
                url: conf.getCityUrl,
                type: 'post',
                dataType: 'json',
                data: {
                    provinceCode: id
                },
                success: function(data) {
                    if (data.ret.success) {
                        var data = data.ret.result;

                        $.cpc.city.setCodeHtml(data, conf, obj);
                    } else {
                        alert('接口请求错误');
                    }
                },
                error: function(data) {
                    alert('服务器出错了');
                }
            });
        },
        getId: function(id, name, conf) {
            var li = conf.ids.find('li');
            var sum = $('.J_ids li');
            var flag = true;
            var html = '';

            html += '<li data-id=' + id + ' data-name=' + name + '>' + name + '<a href="javascript:;" class="J_del">X</a></li>';

            if (sum.length === 0) {
                $('.J_ids ul').append(html);
            } else {
                $.each(sum, function(index, el) {
                    var ids = $(this).data('id');

                    if (ids == id) {
                        flag = false;
                        alert('请勿重复添加,' + name);
                    }
                });

                if (flag) {
                    $('.J_ids ul').append(html);
                }
            };
        },
        delIds: function(id, conf) {
            var sum = $('.J_ids li');

            $.each(sum, function(index, el) {
                var ids = $(this).data('id');

                if (ids == id) {
                    $(this).remove();
                }
            });
        },
        DelCheckBoxId: function(id, conf) {
            var sum = conf.targetNode.find('input[name="city"]');

            $.each(sum, function(index, el) {
                var self = $(this);
                var ids = self.val();

                if (ids == id) {
                    self.attr('checked', false);
                    return false;
                }
            });
        }
    };
})(jQuery);
