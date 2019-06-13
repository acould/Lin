/**
 * aiyoumi 选择学校
 * author:xiaocpc
 */

(function($) {
    $.cpc.school = {
        conf: {
            $schoolInput: $('#J_ChooseSchoolCity'),
            getProvinceUrl: '/common/provinces',
            getCityUrl: '/common/citys',
            getSchoolUrl: '/common/schools',
            showMask: true
        },
        init: function(opt) {
            var self = $.cpc.school,
                conf = self.conf;

            if (opt) {
                $.extend(self.conf, opt);
            }

            conf.$schoolInput.on('click', function() {
                var html = '';

                html += '<div class="clearfix">';
                html += '<div class="fl base-select-outer" id="J_ChooseProvince">';
                html += '<div class="clearfix base-select-text-box">';
                html += '<span class="fl base-select-text fs16">请选择所在的省份</span>';
                html += '<span class="common-icon select-down-arrow fr"></span>';
                html += '</div>';
                html += '<ul class="base-select-ul">';
                html += '</ul>';
                html += '</div>';
                html += '<div class="fl base-select-outer ml10" id="J_ChooseCity">';
                html += '<div class="clearfix base-select-text-box">';
                html += '<span class="fl base-select-text fs16">请选择学校所在市</span>';
                html += '<span class="common-icon select-down-arrow fr"></span>';
                html += '</div>';
                html += '<ul class="base-select-ul">';
                html += '</ul>';
                html += '</div>';
                html += '<div class="fl ml10 clarfix school-search-box disabled">';
                html += '<input type="text" placeholder="搜索学校" class="base-input fl" disabled="disabled"/>';
                html += '<a class="fl common-icon search-icon" id="J_SearchSchoolIcon"></a>';
                html += '</div>';
                html += '</div>';
                html += '<ul id="J_SchoolBox" class="clearfix mt10"></ul>';
                html += '<p class="table" style="text-align:right"><a href="javascript:;" class="btn default mr20 mt10 confirm-btn" style="display:none">确定</a></p>';

                if ($('#J_ChooseSchoolDialog').html()) {
                    $('#J_ChooseSchoolDialog').show();
                } else {
                    $('#J_ChooseSchoolDialog').html(html).show();
                    self.initEvent();
                    self.initProvincedata($('#J_ChooseProvince'));

                    $('#J_ChooseCity').on('click', 'ul li a', function(event) {
                        var cityId = $(this).attr('data-val');

                        if (cityId) {
                            self.initSchooldata($('#J_SchoolBox'), cityId);
                        }
                    });

                    $('#J_ChooseProvince').customSelect();
                    $('#J_ChooseCity').customSelect();
                }
            });
        },

        initEvent: function() {
            var me = this;

            $('#J_ChooseSchoolDialog').on('click', '#J_SchoolBox li', function() {
                var self = $(this);
                var schoolName = self.attr('data-schoolName');
                var schoolId = self.attr('data-schoolId');
                var schoolLi = $('#confirmSchool').find('li');

                if (schoolLi.length > 0) {
                    for (var i = 0; i < schoolLi.length; i++) {
                        var id = schoolLi.eq(i).data('schoolId');

                        if (id == schoolId) {
                            alert('该学校已经存在了哦！');
                            return;
                        }
                    }
                }

                var li = '<li data-school-id="' + schoolId + '" data-school-name="' + schoolName + '">' + schoolName + '<a href="javascript:;" class="del-btn">X</a></li>';

                $('#confirmSchool').append(li);
            });

            $('#J_ChooseSchoolDialog').on('keypress', '.school-search-box input', function(event) {
                var self = $(this);

                if (event.keyCode == 13) {
                    var o = $('#J_SchoolBox');
                    var v = $.trim(self.val());
                    var cityId = $('#J_ChooseCity').find('.base-select-text').attr('data-val');

                    $.post(me.conf.getSchoolUrl, {
                        cityId: cityId,
                        schoolName: v
                    }, function(data) {
                        if (data) {
                            if (data.ret.success && data.ret.result) {
                                var html = '',
                                    schoollist = data.ret.result;

                                o.html('');

                                $.each(schoollist, function(i, item) {
                                    var iname = item.schoolName;

                                    if (iname.indexOf(v) >= 0) {
                                        html += '<li data-provinceId="' + item.provinceId + '" data-cityId="' + item.cityId + '" data-provinceId="' + item.provinceId + '" data-schoolId="' + item.schoolId + '" data-schoolName="' + item.schoolName + '">' + item.schoolName + '</li>';
                                    }
                                });
                                o.html(html);
                            }
                        }
                    });
                }
            });

            $('#J_ChooseSchoolDialog').on('click', '#J_SearchSchoolIcon', function() {
                var self = $(this);

                if (self.parent().hasClass('disabled')) {
                    return;
                }

                var o = $('#J_SchoolBox');
                var v = $.trim($('#J_ChooseSchoolDialog .school-search-box input').val());
                var cityId = $('#J_ChooseCity').find('.base-select-text').attr('data-val');

                $.post(me.conf.getSchoolUrl, {
                    cityId: cityId,
                    schoolName: v
                }, function(data) {
                    if (data) {
                        if (data.ret.success && data.ret.result) {
                            var html = '',
                                schoollist = data.ret.result;

                            o.html('');
                            $.each(schoollist, function(i, item) {
                                var iname = item.schoolName;

                                if (iname.indexOf(v) >= 0) {
                                    html += '<li data-provinceId="' + item.provinceId + '" data-cityId="' + item.cityId + '" data-provinceName="' + item.provinceId + '" data-schoolId="' + item.schoolId + '" data-schoolName="' + item.schoolName + '">' + item.schoolName + '</li>';
                                }
                            });
                            o.html(html);
                        }
                    }
                });
            });

            $('#confirmSchool').on('click', '.del-btn', function() {
                var self = $(this);

                self.parent().remove();
            });

            $('#J_ChooseSchoolDialog').on('click', '.confirm-btn', function() {
                $('#J_ChooseSchoolDialog').hide();
            });
        },
        getProvincedata: function(o, callback) {
            var self = $.cpc.school;
            var me = this;
            var conf = self.conf;

            $.post(conf.getProvinceUrl, function(data) {
                if (data.ret.success && data.ret.result) {
                    if (typeof(callback) === 'function') {
                        callback(data);
                    }
                }
            });
        },
        getCitydata: function(o, provinceId, callback) {
            var self = $.cpc.school;
            var me = this;
            var conf = self.conf;

            if (provinceId) {
                $.post(conf.getCityUrl, {
                    provinceCode: provinceId
                }, function(data) {
                    if (data.ret.success && data.ret.result) {
                        if (typeof(callback) === 'function') {
                            callback(data);
                        }
                    }
                });
            }
        },
		getAreadata: function(o, cityId, callback) {
            var self = $.cpc.school;
            var me = this;
            var conf = self.conf;

            if (cityId) {
                $.post(conf.getCityUrl, {
                    cityId: cityId
                }, function(data) {
                    if (data.ret.success && data.ret.result) {
                        if (typeof(callback) === 'function') {
                            callback(data);
                        }
                    }
                });
            }
        },
        getSchooldata: function(o, cityId, callback) {
            var self = $.cpc.school;
            var me = this;
            var conf = self.conf;

            if (cityId) {
                $.post(conf.getSchoolUrl, {
                    cityId: cityId
                }, function(data) {
                    if (data.ret.success && data.ret.result) {
                        if (data.ret.success && data.ret.result) {
                            if (typeof(callback) === 'function') {
                                callback(data);
                            }
                        }
                    }
                });
            }
        },
        initProvincedata: function(o) {
            var self = $.cpc.school;

            self.getProvincedata(o, function(data) {
                var html = '';
                var provincelist = data.ret.result;

                $.each(provincelist, function(i, item) {
                    html += '<li><a href="javascript:;" data-val="' + item.provinceCode + '">' + item.province + '</a></li>';
                });

                o.find('ul').html(html);
                o.on('click', 'ul li a', function(event) {
                    self.initCitydata($('#J_ChooseCity'), $(this).data('val'));
                    $('#J_ChooseCity').find('.base-select-text').text('请选择学校所在市').attr('data-val', '');
                });
            });
        },

        initCitydata: function(o, provinceId) {
            var self = $.cpc.school;
            var conf = self.conf;

            self.getCitydata(o, provinceId, function(data) {
                var html = '',
                    citylist = data.ret.result;

                $.each(citylist, function(i, item) {
                    html += '<li><a href="javascript:;" data-val="' + item.cityCode + '">' + item.city + '</a></li>';
                });
                o.find('ul').html(html);
            });
        },

        initSchooldata: function(o, cityId) {
            var self = $.cpc.school,
                conf = self.conf;

            self.getSchooldata(o, cityId, function(data) {
                var html = '',
                    schoollist = data.ret.result;

                $.each(schoollist, function(i, item) {
                    html += '<li data-provinceId="' + item.provinceCode + '" data-provinceName="' + item.province + '" data-cityId="' + item.cityCode + '" data-cityname="' + item.city + '"  data-schoolId="' + item.schoolId + '" data-schoolName="' + item.schoolName + '">' + item.schoolName + '</li>';
                });
                o.html(html);
                $('#J_ChooseSchoolDialog').find('.confirm-btn').show();

                if (html !== '') {
                    $('#J_ChooseSchoolDialog .school-search-box').removeClass('disabled');
                    $('#J_ChooseSchoolDialog .school-search-box input').removeAttr('disabled');
                } else {
                    $('#J_ChooseSchoolDialog .school-search-box').addClass('disabled');
                    $('#J_ChooseSchoolDialog .school-search-box input').attr('disabled', 'disabled');
                }
            });
        }
    };
})(jQuery);
