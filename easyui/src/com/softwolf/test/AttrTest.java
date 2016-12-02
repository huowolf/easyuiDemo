package com.softwolf.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.softwolf.utils.DaoUtil;

public class AttrTest {

	@Test
	public void testGetColumns() throws SQLException{
		List<String> columns = DaoUtil.getColumns("user");
		for (String col : columns) {
			System.out.println(col);
		}
	}
	
}
