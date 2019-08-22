package com.lk.tags;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.lk.util.cache.DictCacheUtils;

public class DictCodeByNameTag extends TagSupport {
	private static final long serialVersionUID = 4364554937821296946L;
	private String dictType;//字典类型
	private String dictName;//字典name
	private JspWriter out;

	@SuppressWarnings("static-access")
	@Override
	public int doStartTag() throws JspException {
		out = this.pageContext.getOut();
		String dictCode = DictCacheUtils.getDictCode(dictName, dictType);
		try {
			if (StringUtils.isEmpty(dictCode)) {
				out.print("");
			} else {
				out.print(dictCode);
			}
		} catch (IOException e) {
		}
		return this.EVAL_PAGE;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictCode(String dictName) {
		this.dictName = dictName;
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
