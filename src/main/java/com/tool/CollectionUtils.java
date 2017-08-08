package com.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 集合工具类
 * 
 * @Title: CollectionUtils.java
 * @Package mall.common.utils
 * @Description: 集合工具类
 * @author tianzy
 * @date 2017年7月17日下午2:30:46
 */
public class CollectionUtils {
	/**
	 * 获取一个空的ListMap
	 * 
	 * @Title: getEmptyListMap
	 * @Description: 获取一个空的ListMap
	 * @author tianzy
	 * @date 2017年7月17日下午2:38:01
	 *
	 * @return
	 */
	public static List<Map<String, Object>> getEmptyListMap() {
		return new ArrayList<Map<String, Object>>();
	}

	/**
	 * 获取一个空的Map
	 * 
	 * @Title: getEmptyMap
	 * @Description: 获取一个空的Map
	 * @author tianzy
	 * @date 2017年7月17日下午2:38:49
	 *
	 * @return
	 */
	public static Map<String, Object> getEmptyMap() {
		return new HashMap<String, Object>();
	}

	public static List<Map<String, Object>> minGroupBy(List<Map<String, Object>> sourceListMap, String minKey, String groupByKey) {
		List<Map<String, Object>> distinctListMap = distinct(sourceListMap, groupByKey);
		List<Map<String, Object>> newListMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : distinctListMap) {
			newListMap.add(min(select(sourceListMap, groupByKey, map.get(groupByKey)), minKey));
		}
		return newListMap;
	}

	public static Map<String, Object> minWhr(List<Map<String, Object>> sourceListMap, String minKey, String whrKey, Object whrValue) {
		Map<String, Object> rltMap = null;
		for (Map<String, Object> map : sourceListMap) {
			if (map.get(whrKey).toString().equals(whrValue.toString())) {
				if (null == rltMap)
					rltMap = map;
				else {
					if (Double.parseDouble(rltMap.get(minKey).toString()) > Double.parseDouble(map.get(minKey).toString())) {
						rltMap = map;
					}
				}
			}
		}
		return rltMap;
	}

	public static Map<String, Object> maxWhr(List<Map<String, Object>> sourceListMap, String minKey, String whrKey, Object whrValue) {
		Map<String, Object> rltMap = null;
		for (Map<String, Object> map : sourceListMap) {
			if (map.get(whrKey).toString().equals(whrValue.toString())) {
				if (null == rltMap)
					rltMap = map;
				else {
					if (Double.parseDouble(rltMap.get(minKey).toString()) < Double.parseDouble(map.get(minKey).toString())) {
						rltMap = map;
					}
				}
			}
		}
		return rltMap;
	}

	public static Map<String, Object> min(List<Map<String, Object>> sourceListMap, String key) {
		Map<String, Object> rltMap = null;
		for (Map<String, Object> map : sourceListMap) {
			if (null == rltMap)
				rltMap = map;
			else {
				if (Double.parseDouble(rltMap.get(key).toString()) > Double.parseDouble(map.get(key).toString())) {
					rltMap = map;
				}
			}
		}
		return rltMap;
	}

	public static List<Map<String, Object>> select(List<Map<String, Object>> sourceListMap, String key, Object value) {
		List<Map<String, Object>> newListMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : sourceListMap) {
			if (map.get(key).toString().equals(value.toString()))
				newListMap.add(map);
		}
		return newListMap;
	}

	public static Map<String, Object> selectMap(List<Map<String, Object>> sourceListMap, String key, Object value) {
		Map<String, Object> newMap = CollectionUtils.getEmptyMap();
		for (Map<String, Object> map : sourceListMap) {
			if (map.get(key).toString().equals(value.toString())) {
				newMap = map;
				break;
			}
		}
		return newMap;
	}

	public static List<Map<String, Object>> select(List<Map<String, Object>> sourceListMap, String key, Object value, String removeKeys) {
		List<Map<String, Object>> newListMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapTemp = null;
		for (Map<String, Object> map : sourceListMap) {
			if (map.get(key).toString().equals(value.toString())) {
				mapTemp = copyMap(map);
				for (String keys : removeKeys.split(",")) {
					mapTemp.remove(keys);
				}
				newListMap.add(mapTemp);
			}
		}
		return newListMap;
	}

	public static <K, V> List<Map<K, V>> selectLike(List<Map<K, V>> sourceListMap, K key, V value) {
		List<Map<K, V>> newListMap = new ArrayList<Map<K, V>>();
		for (Map<K, V> map : sourceListMap) {
			if (map.get(key).toString().contains(value.toString()))
				newListMap.add(map);
		}
		return newListMap;
	}

	public static List<Map<String, Object>> update(List<Map<String, Object>> sourceListMap, String updateKey, Object updateValue, String whrKey, Object whrValue) {
		for (Map<String, Object> map : sourceListMap) {
			if (map.get(whrKey).toString().equals(whrValue.toString()))
				map.put(updateKey, updateValue);
		}

		return sourceListMap;
	}

	public static List<Map<String, Object>> distinct(List<Map<String, Object>> sourceListMap, String key) {
		List<Map<String, Object>> newListMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : sourceListMap) {
			if (!exists(newListMap, key, map.get(key)))
				newListMap.add(map);
		}
		return newListMap;
	}

	public static List<Map<String, Object>> distinct(List<Map<String, Object>> sourceListMap, String key, String removeKeys) {
		List<Map<String, Object>> newListMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapTemp = null;
		for (Map<String, Object> map : sourceListMap) {
			if (!exists(newListMap, key, map.get(key))) {
				mapTemp = copyMap(map);
				for (String keys : removeKeys.split(",")) {
					mapTemp.remove(keys);
				}
				newListMap.add(mapTemp);
			}
		}
		return newListMap;
	}

	public static List<String> distinctKey(List<Map<String, Object>> sourceListMap, String key) {
		List<String> newList = new ArrayList<String>();
		for (Map<String, Object> map : sourceListMap)
			newList.add(map.get(key).toString());

		return newList;
	}

	public static Map<String, Object> copyMap(Map<String, Object> inMap) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : inMap.entrySet()) {
			outMap.put(entry.getKey(), entry.getValue());
		}

		return outMap;
	}

	public static Map<String, Object> copyMap(Map<String, Object> leftMap, Map<String, Object> rightMap) {
		Map<String, Object> outMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : leftMap.entrySet()) {
			outMap.put(entry.getKey(), entry.getValue());
		}
		for (Map.Entry<String, Object> entry : rightMap.entrySet()) {
			outMap.put(entry.getKey(), entry.getValue());
		}

		return outMap;
	}

	public static List<Map<String, Object>> copyListMap(List<Map<String, Object>> inListMap) {
		List<Map<String, Object>> outListMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> inMap : inListMap) {
			outListMap.add(copyMap(inMap));
		}
		return outListMap;
	}

	public static boolean exists(List<Map<String, Object>> sourceListMap, String key, Object value) {
		for (Map<String, Object> map : sourceListMap) {
			if (map.get(key).toString().equals(value.toString())) {
				return true;
			}
		}
		return false;
	}

	public static boolean exists(List<Map<String, Object>> sourceListMap, String key, Object value, String key1, Object value1) {
		for (Map<String, Object> map : sourceListMap) {
			if (map.get(key).toString().equals(value.toString()) && map.get(key1).toString().equals(value1.toString())) {
				return true;
			}
		}
		return false;
	}

	public static void clear(List<Map<String, Object>> listMap) {
		for (Map<String, Object> map : listMap) {
			map.clear();
		}
		listMap.clear();
	}

	public static void mapSelectCut(Map<String, Object> map, String keyLst) {
		for (String key : keyLst.split(",")) {
			if (!map.containsKey(key))
				map.remove(key);
		}
	}

	public static void listMapSelectCut(List<Map<String, Object>> listMap, String keyLst) {
		for (Map<String, Object> map : listMap)
			mapSelectCut(map, keyLst);
	}

	public static void mapSelectRemove(Map<String, Object> map, String keyLst) {
		for (String key : StringUtils.split(keyLst, ",")) {
			if (map.containsKey(key)) {
				map.remove(key);
			}
		}
	}

	public static void listMapSelectRemove(List<Map<String, Object>> listMap, String keyLst) {
		for (Map<String, Object> map : listMap)
			mapSelectRemove(map, keyLst);
	}

	public static boolean ifNullKey(Map<String, Object> map, String keyLst) {
		for (String key : keyLst.split(",")) {
			if (null == map.get(key))
				return true;
		}
		return false;
	}

	public static List<Map<String, Object>> selectCopy(List<Map<String, Object>> sourceListMap, String key, Object value) {
		List<Map<String, Object>> newListMap = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : sourceListMap) {
			if (map.get(key).toString().equals(value.toString()))
				newListMap.add(copyMap(map));
		}
		return newListMap;
	}

	public static List<Map<String, Object>> InnerJoin(List<Map<String, Object>> listMapLeft, List<Map<String, Object>> listMapRight, String key) {
		List<Map<String, Object>> listMapOut = getEmptyListMap();
		for (Map<String, Object> map : listMapLeft) {
			for (Map<String, Object> mapTmp : selectCopy(listMapRight, key, map.get(key))) {
				listMapOut.add(copyMap(mapTmp, map));
			}
		}

		return listMapOut;
	}
	/**
	 * 根据key的值经行分组
	 * @Title: groupListByKey
	 * @Description: 根据key的值经行分组
	 * @author tianzy
	 * @date 2017年7月17日下午2:58:27
	 *
	 * @param list 源list
	 * @param key 分组key
	 * @return
	 * @throws Exception
	 */
	public static Map<String, List<Map<String, Object>>> groupListByKey(List<Map<String, Object>> list,String key) throws CustomException{
		Map<String, List<Map<String, Object>>> returnMap=new HashMap<String, List<Map<String,Object>>>();
		for (Map<String, Object> map : list) {
			Object value = map.get(key);
			if(value==null) {
				throw new CustomException(200,"the value of key is null");
			}
			if(returnMap.containsKey(value)) {
				List<Map<String, Object>> list2 = returnMap.get(value);
				list2.add(map);
			}else {
				List<Map<String, Object>> emptyListMap = getEmptyListMap();
				emptyListMap.add(map);
				returnMap.put(value.toString(), emptyListMap);
			}
		}
		return returnMap;
	}
}
