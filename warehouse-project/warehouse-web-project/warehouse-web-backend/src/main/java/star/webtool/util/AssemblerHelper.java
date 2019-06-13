package star.webtool.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import star.vo.search.TypeCountVo;

public class AssemblerHelper {
	/**
	 * 按类型行数转换为map
	 * @param typeCounts
	 * @return
	 */
	public static Map<String,Integer> toMap4Count(List<TypeCountVo> typeCounts){
		Map<String,Integer> typeCountMap = new HashMap<String,Integer>();
		
		for(TypeCountVo typeCount : typeCounts){
			if(typeCount != null){
				typeCountMap.put(typeCount.getType(), typeCount.getCount());
			}
		}
		
		return typeCountMap;
	}
}
