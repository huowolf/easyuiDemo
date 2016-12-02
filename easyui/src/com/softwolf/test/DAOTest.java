package com.softwolf.test;

import java.util.List;

import org.junit.Test;

import com.softwolf.dao.UserDao;
import com.softwolf.dao.impl.UserDaoImpl;
import com.softwolf.pojo.User;

public class DAOTest {
	
	private UserDao userDao = new UserDaoImpl();

	@Test
	public void testSave() throws Exception {
		User u = new User();
		u.setAge(21);
		u.setName("张三");
		u.setPwd("zhangsan");
		u.setDescription("我爱编程");
		
		userDao.save(u);
	}


	@Test
	public void testUpdate() throws Exception {
		User u = new User();
		u.setId(20);
		u.setName("dddddd");
		u.setPwd("dddddddd");
		u.setAge(38);
		u.setDescription("我是一名码农");
		userDao.update(u);
	}

	@Test
	public void testDelete() throws Exception {
		userDao.delete(18);
	}

	@Test
	public void testFindAll() throws Exception {
		List<User> userList = userDao.findAll();
		for (User user : userList) {
			System.out.println(user);
		}
	}

	@Test
	public void testFindById() throws Exception {
		User u = userDao.findById(1);
		System.out.println(u);
	}
	
	@Test
	public void testFindByPagination() throws Exception {
		
		List<User> users = userDao.findByPagination(1, 5);
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	@Test
	public void testGetTotal() throws Exception{
		long total = userDao.getTotal();
		System.out.println("total:"+ total);
	}

}
