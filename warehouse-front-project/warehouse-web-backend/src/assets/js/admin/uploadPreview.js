/**
* 图片预览插件
* by Min 2015/06/19
* usage: $("#uploadFile1").uploadPreview({ ImgPreview: "#ImgPreview", Width: 500, Height: 500 });
*/
+(function($) {
    'use strict';

    var uploadPreview = function (element, options) {
        this.$elem  = $(element);
        this.options   = $.extend({}, uploadPreview.DEFAULTS, options);

        this.init();
    }

    uploadPreview.DEFAULTS = {
        ImgPreview: '#ImgPreview',
        Width: '120px',
        Height: '120px',
        ImgType: ['gif','png','jpg','jpeg','bmp']
    };

    uploadPreview.prototype.init = function(){
        var self = this;
        self.$ImgPreview = $(self.options.ImgPreview);
        //self.$ImgPreview.width(self.options.Width);
        //self.$ImgPreview.height(self.options.Height);

        self.$elem.on('change', function(e) {
            self.doImgPreview(this,e);
        });
    }
    uploadPreview.prototype.doImgPreview = function(input,e){
        var self = this;
        if (!RegExp("\.(" + self.options.ImgType.join("|") + ")$", "i").test(input.value.toLowerCase())) {
            alert('文件格式错误,图片类型必须是' + self.options.ImgType.join(',') + '中的一种');
            input.value = "";
            return false;
        }
        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                var img = new Image();
                img.src = e.target.result;
                img.onload = function() {
                    if(this.width > self.options.Width && this.height > self.options.Height){
                        self.$ImgPreview.attr('src', '').hide();
                        input.value = "";
                        alert('文件大小超过限制，请重新选择文件！');
                        return false;
                    }else{
                        self.$ImgPreview.attr('src', img.src).show();
                    }
                }
                
            }

            reader.readAsDataURL(input.files[0]);
        }
    }
    function Plugin(option) {
        return this.each(function () {
          var $this   = $(this);
          var data    = $this.data('axd.uploadPreview');
          var options = typeof option == 'object' && option;

          if (!data) $this.data('axd.uploadPreview', (data = new uploadPreview(this, options)))
        })
    }
    $.fn.uploadPreview             = Plugin;
    $.fn.uploadPreview.Constructor = uploadPreview;

})(jQuery);