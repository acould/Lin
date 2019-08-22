package com.lk.dao.dict;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lk.dao.GenericDao;
import com.lk.dao.MyBatisDao;
import com.lk.entity.system.DictEntry;
import com.lk.entity.system.DictType;

@MyBatisDao
public interface DictDao extends GenericDao<DictEntry, String>{


	public List<DictType> selectDictType(DictType dictType);
	
	public List<DictType> selectDictTypePage(DictType dictType);
	
	// 获取小类字典表列表
	public List<DictEntry> selectDictEntry(@Param("dictType") String dictType,@Param("dictCode") String dictCode);
	
	// 查询是否有存在的字典码
	public List<DictEntry> selectDictEntryList(DictEntry dictEntry);

	public List<DictEntry> selectDistinctDictEntry(String dictType);

    //新增字典表类型
	public int insertDictType(DictType dictType);
	
	/**
	 * 新增字典表
	 * @param dictEntry
	 * @return
	 */
	public int insertDictEntry(DictEntry dictEntry);

	/**
	 * 更新字典表
	 * @param sysDictEntry
	 * @return
	 */
	public int updateDictEntry(DictEntry dictEntry);

	/**
	 * 更新字典信息表的order
	 * @param sysDictEntry
	 */
	public int updateDictEntryOrder(DictEntry dictEntry);

	/**
	 * 删除字典表选项
	 * @param dictType 字典表类型
	 * @param dictCode 字典值
	 * @param orgId 机构号
	 * @param langCode 语言标识
	 * @return
	 */
	public int removeDictEntry(DictEntry dictEntry);
	
	public int updateDictType(DictType dictType);
	
	public int removeDictType(String dictType);
}


