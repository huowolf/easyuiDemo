package com.softwolf.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softwolf.dao.UserDao;
import com.softwolf.dao.impl.UserDaoImpl;
import com.softwolf.pojo.User;

import net.sf.json.JSONArray;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao userDao = new UserDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String method = request.getParameter("method");
		if("userList".equals(method)){
			userList(request,response);
		}
		if("userAdd".equals(method)){
			userAdd(request,response);
		}
		if("userUpdate".equals(method)){
			userUpdate(request,response);
		}
		if("userDelete".equals(method)){
			userDelete(request,response);
		}
		
		
		
	}

	private void userDelete(HttpServletRequest request, HttpServletResponse response) {
		
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			userDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void userUpdate(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");		
			
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			int age = Integer.parseInt(request.getParameter("age"));
			String pwd = request.getParameter("pwd");
			String description = request.getParameter("description");
			System.out.println(description);
			User u = new User();
			u.setId(id);
			u.setName(name);
			u.setAge(age);
			u.setPwd(pwd);
			u.setDescription(description);
		
			userDao.update(u);
			
			String str = "{\"status\":\"ok\", \"message\":\"操作成功\"}";
			response.getWriter().write(str);
		} catch (Exception e) {
			
			String str = "{\"status\":\"fail\", \"message\":\"操作失败\"}";
			try {
				response.getWriter().write(str);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}

	private void userAdd(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String pwd = request.getParameter("pwd");
		String description = request.getParameter("description");
		
		User u = new User();
		u.setName(name);
		u.setAge(age);
		u.setPwd(pwd);
		u.setDescription(description);
		
		try {
			userDao.save(u);
			
			//{"status":"ok", "message":"操作成功"}
			String str = "{\"status\":\"ok\", \"message\":\"操作成功\"}";
			response.getWriter().write(str);
		} catch (Exception e) {
			
			//{"status":"fail", "message":"操作失败"}
			String str = "{\"status\":\"fail\", \"message\":\"操作失败\"}";
			try {
				response.getWriter().write(str);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	private void userList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			
			int currentPage = 1;
			int pageSize  = 5;
			
			if(page!=null && !"".equals(page)){
				currentPage = Integer.parseInt(page);
			}
			
			if(rows!=null && !"".equals(rows)){
				pageSize = Integer.parseInt(rows);
			}
			
			
			String name = request.getParameter("name");
			String age = request.getParameter("age");
			
			Map<String, Object> conditionMap = new HashMap<>();
			if(name!=null && !"".equals(name)){
				conditionMap.put("name", name);
			}
			if(age!=null && !"".equals(age)){
				conditionMap.put("age", age);
			}
			
			List<User> users = null;
			if(conditionMap.size()==0){
				users= userDao.findByPagination(currentPage, pageSize);
			}else{
				users=userDao.findByPagination(currentPage, pageSize, conditionMap);
			}


			long total = userDao.getTotal();
			
			//{"total":10,"rows":[{},{}]}
			String json = "{\"total\":"+ total + ",\"rows\":"+ JSONArray.fromObject(users)+"}";
			
			response.getWriter().write(json);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
