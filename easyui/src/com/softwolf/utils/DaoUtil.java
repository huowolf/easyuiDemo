package com.softwolf.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

public class DaoUtil {

	/**
	 * 获取某个表的所有字段名
	 * @param tablename
	 * @return
	 * @throws SQLException 
	 */
	public static List<String> getColumns(String tablename) throws SQLException{
		
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();
		
		String sql = "SELECT COLUMN_NAME FROM information_schema.columns WHERE table_name=? AND table_schema ='easyui'";
		
		List<String> columns = qr.query(conn, sql, new ColumnListHandler<String>(1), tablename);
		
		return columns;
	}
}
