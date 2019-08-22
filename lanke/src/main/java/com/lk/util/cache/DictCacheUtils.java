package com.lk.util.cache;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lk.dao.dict.DictDao;
import com.lk.entity.system.DictEntry;
import com.lk.util.system.SpringContextHolder;

/**
 * Datetime   ： 2016年1月6日 上午10:16:03<br>
 * Title      :  DictUtils.java<br>
 * Description:  字典工具类<br>
 * Company    :  hiwan<br>
 *
 * @author cbj
 */
public class DictCacheUtils {

    private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

    public static final String DICT_CACHE = "dictCache";

    /**
     * 获取字典编码
     *
     * @param value 字典值
     * @param type  字典类型
     * @return
     */
    public static String getDictCode(String value, String type) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)) {
            for (DictEntry dict : getDictList(type)) {

                if (type.equals(dict.getDictType()) && value.equals(dict.getDictValue())) {
                    return dict.getDictCode();
                }
            }
        }

        return "";
    }


    /**
     * 获取指定编码串的值
     *
     * @param codes
     * @param type
     * @return
     */
    public static String getDictValues(String codes, String type) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(codes)) {
            List<String> valueList = Lists.newArrayList();
            for (String code : StringUtils.split(codes, ",")) {
                valueList.add(getDictCode(code, type));
            }
            return StringUtils.join(valueList, ",");
        }

        return "";
    }


    /**
     * 获取字典值
     *
     * @param code
     * @param type
     * @return
     * @see DictUtils.getDictValue(info.getStatus(),"D00026");
     */
    public static String getDictValue(String code, String type) {
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(code)) {
            for (DictEntry dict : getDictList(type)) {

                if (type.equals(dict.getDictType()) && code.equals(dict.getDictCode())) {
                    return dict.getDictValue();
                }
            }
        }

        return "";
    }

    /**
     * 获取字典列表
     *
     * @param type
     * @return
     */
    public static List<DictEntry> getDictList(String type) {
        @SuppressWarnings("unchecked")
        Map<String, List<DictEntry>> dictMap = (Map<String, List<DictEntry>>) CacheUtils.get(DICT_CACHE);

        if (dictMap == null) {
            dictMap = Maps.newHashMap();

        }
        if (!dictMap.containsKey(type)) {
            for (DictEntry dict : dictDao.selectDictEntry(type, "")) {
                List<DictEntry> dictList = dictMap.get(dict.getDictType());
                if (dictList != null) {
                    dictList.add(dict);
                } else {
                    dictMap.put(dict.getDictType(), Lists.newArrayList(dict));
                }
            }
            CacheUtils.put(DICT_CACHE, dictMap);
        }
        List<DictEntry> dictList = dictMap.get(type);
        if (dictList == null) {
            dictList = Lists.newArrayList();
        }
        return dictList;
    }

    /**
     * 合并字典
     * <p>
     * 当指定字典已经缓存的时候（调用equals方法），则更新，反之添加
     * <p>
     * 更新字典项
     *
     * @param dictEntry
     */
    public static void mergeDictEntry(DictEntry dictEntry) {
        if (StringUtils.isBlank(dictEntry.getDictType()) || StringUtils.isBlank(dictEntry.getDictCode()))
            return;
        List<DictEntry> list = getDictList(dictEntry.getDictType());
        if (list.contains(dictEntry)) {
            list.remove(dictEntry);
        }
        list.add(dictEntry);

        @SuppressWarnings("unchecked")
        Map<String, List<DictEntry>> dictMap = (Map<String, List<DictEntry>>) CacheUtils.get(DICT_CACHE);
        dictMap.put(dictEntry.getDictType(), list);
    }

    /**
     * 移除字典项
     *
     * @return
     */
    public static void removeDictEntry(DictEntry dictEntry) {
        if (StringUtils.isBlank(dictEntry.getDictType()) || StringUtils.isBlank(dictEntry.getDictCode()))
            return;
        List<DictEntry> list = getDictList(dictEntry.getDictType());
        if (list.contains(dictEntry)) {
            list.remove(dictEntry);
        }
        @SuppressWarnings("unchecked")
        Map<String, List<DictEntry>> dictMap = (Map<String, List<DictEntry>>) CacheUtils.get(DICT_CACHE);
        dictMap.put(dictEntry.getDictType(), list);
    }


}
