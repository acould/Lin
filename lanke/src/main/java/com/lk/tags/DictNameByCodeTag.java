package com.lk.tags;
import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import com.lk.util.cache.DictCacheUtils;

/**
 * 
 * Datetime   ： 2013-1-29 下午3:27:58<br>
 * Title      :  DictNameByCodeTag.java<br>
 * Description:  根据字典表的code获取name <br>
 * Copyright  :  Copyright (c) 2013<br>
 * Company    :  HIWAN<br>
 * @author zdm
 *
 */
public class DictNameByCodeTag extends TagSupport {
	private static final long serialVersionUID = 4364554937821296946L;
	private String dictType;// 字典类型
	private String dictCode;// 字典code
	private JspWriter out;

	@Override
	public int doStartTag() {
		String dictName = DictCacheUtils.getDictValue(dictCode, dictType);
		out = this.pageContext.getOut();
		try {
			out.print(dictName);
		} catch (IOException e) {
		}
		return Tag.EVAL_PAGE;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public JspWriter getOut() {
		return out;
	}

	public void setOut(JspWriter out) {
		this.out = out;
	}

}
