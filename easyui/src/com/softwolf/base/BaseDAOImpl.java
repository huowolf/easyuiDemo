package com.softwolf.base;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.softwolf.utils.CommonUtil;
import com.softwolf.utils.JDBCUtil;

public class BaseDAOImpl<Entity> implements BaseDAO<Entity> {

	private Class<Entity> clazz;

	@SuppressWarnings("unchecked")
	public BaseDAOImpl() {
		// 使用反射技术得到Entity的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<Entity>) pt.getActualTypeArguments()[0];
	}

	@Override
	public void save(Entity obj) throws Exception {

		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();

		String tablename = clazz.getSimpleName();

		/*
		 * 拼接sql
		 */
		// INSERT INTO USER(NAME,age,pwd,description)
		// VALUES('张三',20,'zhangsan','我是张三')
		String sql = "INSERT INTO " + tablename + " (";

		List<String> columns = CommonUtil.getFileds(clazz);

		for (int i = 1; i < columns.size(); i++) {
			if (i < columns.size() - 1) {
				sql += (columns.get(i) + ",");
			} else {
				sql += columns.get(i);
			}

		}
		sql += ") values(";

		List<Object> objs = CommonUtil.getFiledValues(obj);
		for (int i = 1; i < objs.size(); i++) {
			Object object = objs.get(i);
			
			sql += ("\""+ object + "\"" );

			if (i < objs.size() - 1) {
				sql += ",";
			}

		}

		sql += ")";

		System.out.println("sql:" + sql);
		try {
			qr.update(conn, sql);
		} finally {
			DbUtils.close(conn);
		}

	}

	@Override
	public void update(Entity entity) throws Exception {
		//UPDATE USER SET NAME="cccc",age="35",pwd="ccccc",description="cccccccc" WHERE id="20"
		
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();
		
		/**
		 * 拼接sql
		 */
		String sql = "UPDATE " + clazz.getSimpleName() + " SET ";
		
		List<String> columns = CommonUtil.getFileds(clazz);
		List<Object> columnValues = CommonUtil.getFiledValues(entity);
		
		for(int i=1; i<columns.size()-1; i++){
			sql += (columns.get(i)+"=\""+columnValues.get(i) + "\",");
		}
		sql += (columns.get(columns.size()-1)+"=\""+columnValues.get(columns.size()-1)+"\" ");
		
		sql += ("WHERE id=" + columnValues.get(0));
		
		System.out.println("sql:"+sql);
		try {
			qr.update(conn, sql);
		} finally {
			DbUtils.close(conn);
		}
	}

	@Override
	public void delete(int id) throws Exception {
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();
		
		String sql = "DELETE FROM " + clazz.getSimpleName() + " WHERE id=?";
		
		try {
			qr.update(conn, sql, id);
		} finally {
			DbUtils.close(conn);
		}
	}

	@Override
	public List<Entity> findAll() throws Exception {

		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();

		String sql = "select * from " + clazz.getSimpleName();
		List<Entity> entityList = null;

		try {
			entityList = qr.query(conn, sql, new BeanListHandler<Entity>(clazz));
		} finally {
			DbUtils.close(conn);
		}

		return entityList;
	}

	@Override
	public Entity findById(int id) throws Exception {

		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();

		String sql = "select * from " + clazz.getSimpleName() + " where id=?";
		Entity entity = null;

		try {
			entity = qr.query(conn, sql, new BeanHandler<Entity>(clazz), id);
		} finally {
			DbUtils.close(conn);
		}

		return entity;
	}

	@Override
	public List<Entity> findByPagination(int currentPage, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();

		List<Entity> entityList = new ArrayList<>();

		int start = (currentPage - 1) * pageSize;
		String sql = "select * from " + clazz.getSimpleName() + " limit " + start + "," + pageSize;

		try {
			entityList = qr.query(conn, sql, new BeanListHandler<Entity>(clazz));
		} finally {
			DbUtils.close(conn);
		}

		return entityList;
	}
	
	
	@Override
	public List<Entity> findByPagination(int currentPage, int pageSize, Map<String, Object> conditionMap)
			throws Exception {
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();
		
		int start = (currentPage - 1) * pageSize;
		
		//SELECT * FROM USER WHERE NAME LIKE '%小%' AND age LIKE '25';
		String sql = "select * from " + clazz.getSimpleName() +" WHERE 1=1 ";
		
		for (Map.Entry<String, Object> condition : conditionMap.entrySet()) {
			sql += (" AND "+ condition.getKey() +" LIKE '%" + condition.getValue() + "%' ");
		}
		
		sql += " LIMIT " + start + "," + pageSize;
		
		System.out.println("sql:"+sql);
		
		List<Entity> entityList = new ArrayList<>();
		try {
			entityList = qr.query(conn, sql, new BeanListHandler<Entity>(clazz));
		} finally {
			DbUtils.close(conn);
		}
		return entityList;
	}

	@Override
	public long getTotal() throws Exception {
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();
		
		String sql = "SELECT COUNT(*) FROM " + clazz.getSimpleName();
		long total = 0;
		
		try {
			total = qr.query(conn, sql, new ScalarHandler<Long>());
		} finally {
			DbUtils.close(conn);
		}
		return total;
	}

	

}
