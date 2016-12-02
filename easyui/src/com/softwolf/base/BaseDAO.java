package com.softwolf.base;

import java.util.List;
import java.util.Map;

public interface BaseDAO<Entity> {
	
	void save(Entity obj) throws Exception;
	
	void update(Entity entity) throws Exception;
	
	void delete(int id) throws Exception;
	
	List<Entity> findAll() throws Exception;
	
	Entity findById(int id) throws Exception;
	
	/**
	 * 分页查询
	 * @param currentPage 	当前页码
	 * @param pageSize		每页显示数据条数
	 * @return
	 */
	List<Entity> findByPagination(int currentPage,int pageSize) throws Exception;
	
	/**
	 * 带有过滤条件的分页查询
	 * @param currentPage 	当前页码
	 * @param pageSize  	每页显示数据条数
	 * @param conditionMap	过滤条件
	 * @return
	 * @throws Exception
	 */
	List<Entity> findByPagination(int currentPage,int pageSize,Map<String, Object> conditionMap) throws Exception;
	
	long getTotal() throws Exception;
}
