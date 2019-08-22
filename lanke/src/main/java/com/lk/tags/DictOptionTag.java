package com.lk.tags;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.lk.entity.system.DictEntry;
import com.lk.util.cache.DictCacheUtils;

public class DictOptionTag extends TagSupport {
	
	
	private static final long serialVersionUID = 4364554937821296946L;
	private String dictType;// 字典类型
	private String value;
	private String defaultValue;
	private String noKey;// option选项中不需要展示的项
	private String withKey;// option中需要展示的项
	private String nullLable;// 是否需要显示空标签，默认为是(yes|no)
	private JspWriter out;
	private String currentValue;// 当前值(多个值用|隔开)
	
	
	public int doStartTag() {
		
		List<DictEntry> dictList = DictCacheUtils.getDictList(dictType);
		StringBuffer options = new StringBuffer();
		
		if(StringUtils.isNotBlank(defaultValue)){
			nullLable="no";
		}
		
		if (!StringUtils.equals(nullLable, "no")) {
			options.append("<option value=''>==请选择==</option>");
		}
		
		for (DictEntry dictEntry : dictList) {
			if (StringUtils.isNotEmpty(withKey)) {
				if (Arrays.binarySearch(withKey.split("\\|"), dictEntry.getDictCode()) <= -1) {
					continue;
				}
			} else if (StringUtils.isNotEmpty(noKey)) {
				if (Arrays.binarySearch(noKey.split("\\|"), dictEntry.getDictCode()) >=0) {
					continue;
				}
			}
			
			//当value为非空，defaultValue非空时，选中value的值
			if (StringUtils.isNotBlank(value)&&StringUtils.isNotBlank(defaultValue) && dictEntry.getDictCode().equals(value)) {
				options.append("<option value=\"" + dictEntry.getDictCode() + "\" selected>" + dictEntry.getDictValue() + "</option>");
				continue;
			}
			
			//当value为空，defaultValue非空时，
			if (StringUtils.isBlank(value)&&StringUtils.isNotBlank(defaultValue) && dictEntry.getDictCode().equals(defaultValue)) {
				options.append("<option value=\"" + dictEntry.getDictCode() + "\" selected>" + dictEntry.getDictValue() + "</option>");
				continue;
			}
			//当value为非空，defaultValue空时，
			if (StringUtils.isNotBlank(value)&&StringUtils.isBlank(defaultValue) && dictEntry.getDictCode().equals(value)) {
				options.append("<option value=\"" + dictEntry.getDictCode() + "\" selected>" + dictEntry.getDictValue() + "</option>");
			}else if(currentValue!=null&&(Arrays.binarySearch(currentValue.split("\\|"), dictEntry.getDictCode()) >=0)){
				options.append("<option value=\"" + dictEntry.getDictCode() + "\" selected>" + dictEntry.getDictValue() + "</option>");
			}else {
				options.append("<option value=\"" + dictEntry.getDictCode() + "\">" + dictEntry.getDictValue() + "</option>");
			}
		}
		
		out = pageContext.getOut();
		try {
			out.print(options.toString());
		} catch (IOException e) {
		}
		return EVAL_PAGE;
	}


	public String getDictType() {
		return dictType;
	}


	public void setDictType(String dictType) {
		this.dictType = dictType;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}


	public String getNoKey() {
		return noKey;
	}


	public void setNoKey(String noKey) {
		this.noKey = noKey;
	}


	public String getWithKey() {
		return withKey;
	}


	public void setWithKey(String withKey) {
		this.withKey = withKey;
	}


	public String getNullLable() {
		return nullLable;
	}


	public void setNullLable(String nullLable) {
		this.nullLable = nullLable;
	}


	public JspWriter getOut() {
		return out;
	}


	public void setOut(JspWriter out) {
		this.out = out;
	}


	public String getCurrentValue() {
		return currentValue;
	}


	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	
	
}
