package com.lk.util.data;


/**
 * @author myq
 * @description
 * @create 2019-04-24 18:14
 */
public enum Operation {

    opera_apply_member("操作提醒", "您已经是会员了，无需重新申请"),
    opera_invalid_board("操作提醒", "当前上机是无效状态，请返回后刷新页面")
    ;

    private String title;
    private String content;

    Operation(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
