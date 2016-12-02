package com.softwolf.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.softwolf.dao.ResourceDao;
import com.softwolf.dto.TreeDTO;
import com.softwolf.pojo.Res;
import com.softwolf.utils.JDBCUtil;

public class ResourceDaoImpl implements ResourceDao {

	@Override
	public List<TreeDTO> getChildByParentId(String id) {
		
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();
		
		String sql = "";
		if(id == null || "".equals(id)){
			sql = "SELECT * FROM resource WHERE parent_id IS NULL";
		}else{
			sql = "SELECT * FROM resource WHERE parent_id=" + id;
		}
		
		List<TreeDTO> treeList = new ArrayList<>();
		try {
			//获取到数据库中对应的Res对象
			List<Res> resList = qr.query(conn, sql, new BeanListHandler<Res>(Res.class));
			
			//转换成treeDTO对象
			for (Res res : resList) {
				TreeDTO treeDTO = new TreeDTO();
				
				treeDTO.setId(res.getId());
				treeDTO.setText(res.getName());
				treeDTO.setChecked(res.getChecked());
				if(hasChild(res.getId())){	//当前是非叶子节点，则默认不用自动展开
					treeDTO.setState("closed");
				}else{
					treeDTO.setState("open");
				}
				treeDTO.getAttributes().put("url", res.getUrl());
				
				treeList.add(treeDTO);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return treeList;
	}
	
	/*
	 * 判断当前节点是否有子节点
	 */
	private boolean hasChild(int pid){
		
		QueryRunner qr = new QueryRunner();
		Connection conn = JDBCUtil.getConnection();
		
		String sql = "select count(*) from resource where parent_id=?";
		long num = 0;
		try {
			num = qr.query(conn,sql, new ScalarHandler<Long>(), pid);
			if(num>0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
