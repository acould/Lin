/**
 * 自定义select x时x分x。x秒
 * author:xiaohai
 */

$(function() {
	$.fn.customSelect = function(cfg) {
		var config = {
			selecttextbox: '.base-select-text-box',
			selecttext: '.base-select-text',
			selectdown: '.base-select-ul',
			callback: function() {}
		};
		config = $.extend(config, cfg);
		var self = $(this);
		var selectDown = self.find(config.selectdown);
		var currentSelect = self.find(config.selecttext);
		$(document).live('click', function() {
			selectDown.hide();
		});
		self.find(config.selecttextbox).live('click', function(event) {
			event.stopPropagation();
			$(config.selectdown).hide();
			if (selectDown.css('display') == 'none') {
				selectDown.show();
			} else {
				selectDown.hide();
			}
		});
		selectDown.find('a').live('click', function(event) {
			event.stopPropagation();
			var me = $(this);
			currentSelect.text(me.text());
			currentSelect.attr('data-val', me.attr('data-val'));
			currentSelect.attr('data-index', selectDown.find('a').index(me));
			selectDown.hide();
			config.callback();
		});
	}

});