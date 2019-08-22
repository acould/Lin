package com.lk.entity.system;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * 
 * Datetime   ： 2016年1月12日 下午5:50:30<br>
 * Title      :  DictType.java<br>
 * Description:  字典类型<br>
 * Company    :  hiwan<br>
 * @author cbj
 *
 */
public class DictType  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String dictType;
	private String dictDesc;
	
	private Set<DictEntry> Entries = new HashSet<DictEntry>(0);
	
	
	public String getDictType() {
		return dictType;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public String getDictDesc() {
		return dictDesc;
	}
	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}
	public Set<DictEntry> getEntries() {
		return Entries;
	}
	public void setEntries(Set<DictEntry> entries) {
		Entries = entries;
	}
}
