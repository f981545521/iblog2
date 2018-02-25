package cn.acyou.iblog.service;

import cn.acyou.iblog.model.Sort;

import java.util.List;
import java.util.Map;

/**文章分类*/
public interface SortService {
	
	/**
	 * 按照uid查找属于自己分类
	 * @param uid
	 * @return
	 */
	List<Sort> listSorts(Integer uid);
	/** 
	 * 添加分类
	 * @param sortName
	 * @param uid
	 * @param description
	 * @return
	 */
	int addSort(String sortName, Integer uid, String description);
	/**
	 * 删除分类
	 * @param id 要删除的ID
	 * @return
	 */
	int delSort(Integer id);
	
	/**
	 * 修改分类
	 * @param id	要修改的分类ID
	 * @param sortName	分类名称
	 * @param description	描述
	 * @return	影响的行数
	 */
	Sort updateSort(Integer id, String sortName, String description);

	Sort updateSort(Integer id);
	/**
	 * 添加文章的时候使用：获取用户所有分类名与描述
	 * @param uid
	 * @return
	 */
	List<Sort> listSortNames(Integer uid);
	
}
