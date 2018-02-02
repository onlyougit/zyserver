package com.zyserver.util.db.jpa;

import java.util.List;

/**
 * 
 * JPA工具类
 * 
 * @author 靳欣龙
 *
 */
public class JpaUtils {
	
	/**
	 * 
	 * UseFor @EntityGraph
	 * 删除主类中重复项
	 * 
	 * @param list
	 * @see EntityGraph
	 */
	public static <T> void shrinkList(List<T> list){
		
		for(int i = list.size(); i > 1; i--){
			if(list.get(i - 1) == list.get(i - 2)){
				list.remove(i - 1);
			}
		}
		
	}
	
}
