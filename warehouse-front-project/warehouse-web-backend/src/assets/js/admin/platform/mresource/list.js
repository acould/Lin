/*
 * 资源管理列表
 */

$(function() {
    var $textarea = $('textarea[name=value]');

    if ($textarea.val() !== '') {
        try {
            var result = jsonlint.parse($textarea.val());
            if (result) {
                $textarea.val(JSON.stringify(result, null, '  '));
            }
        } catch (e) {}
    }

    function jsonLint() {
        try {
            var result = jsonlint.parse($textarea.val());
            if (result) {
                $('#result').addClass('pass').html('JSON是有效的!');
                $textarea.val(JSON.stringify(result, null, '  '));
                return true;
            }
        } catch (e) {
            $('#result').addClass('fail').text(e);
            return false;
        }
    }

    // 新增/更新
    $('.J_ResourceAdd, .J_ResourceUpdate').click(function(event) {
        var url = $(this).hasClass('J_ResourceAdd') ? '/platform/mresource/add' : '/platform/mresource/update',
            data = {
                type: $.trim($('input[name=type]').val()),
                key: $.trim($('input[name=key]').val()),
                value: $textarea.length ? $.trim($textarea.val()) : $.trim($('input[name=value]').val()),
                customField1: $.trim($('input[name=customField1]').val()),
                customField2: $.trim($('input[name=customField2]').val()),
                customField3: $.trim($('input[name=customField3]').val()),
                customField4: $.trim($('input[name=customField4]').val()),
                ordering: $.trim($('input[name=ordering]').val()),
                status: $('input[name=status]:checked').val()
            };

        if ($('input[name=jsonlint]').is(':checked') == false) {
            $.cpc.ajax.init(data, url);

        } else if (jsonLint()) {
            setTimeout(function() {
                $.cpc.ajax.init(data, url);
            }, 1000);
        }
    });

    // 删除
    $('.J_ResourceDel').click(function(event) {
        var data = {
            key: $(this).data('key'),
            type: $(this).data('type')
        };

        if (confirm('是否删除KEY：' + data.key + '资源')) {
            $.cpc.ajax.init(data, '/platform/mresource/delete');
        }
    });
});
