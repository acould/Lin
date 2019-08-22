package com.lk.entity.system;



/**
 * 
 * Datetime   ： 2016年1月12日 下午6:05:41<br>
 * Title      :  DictEntry.java<br>
 * Description:  字典项<br>
 * Company    :  hiwan<br>
 * @author cbj
 *
 */
public class DictEntry {
	
	private static final long serialVersionUID = 1L;
	
	private String dictCode;
	private String dictValue;
	private String dictOrder;
	private String dictType;
	
	
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public String getDictOrder() {
		return dictOrder;
	}
	public void setDictOrder(String dictOrder) {
		this.dictOrder = dictOrder;
	}
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	
	
	@Override
	public int hashCode() {
		int result = 17;
        result = 37 * result + dictType.hashCode();
        result = 37 * result + dictCode.hashCode();
        return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof DictEntry))
			return false;
		DictEntry dictEntry = (DictEntry)obj;
		return dictEntry.dictType.equals(dictType) && dictEntry.dictCode.equals(dictCode);
	}
	@Override
	public String toString() {
		return "DictEntry [dictCode=" + dictCode + ", dictValue=" + dictValue + ", dictOrder=" + dictOrder
				+ ", dictType=" + dictType + "]";
	}
	
}
