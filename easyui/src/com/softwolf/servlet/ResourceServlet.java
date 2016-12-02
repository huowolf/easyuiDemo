package com.softwolf.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softwolf.dao.ResourceDao;
import com.softwolf.dao.impl.ResourceDaoImpl;
import com.softwolf.dto.TreeDTO;

import net.sf.json.JSONArray;

@WebServlet("/ResourceServlet")
public class ResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ResourceDao rdao = new ResourceDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			String method = request.getParameter("method");
			if("loadTree".equals(method)){
				loadTree(request,response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	private void loadTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String id = request.getParameter("id");
		List<TreeDTO> treelist = rdao.getChildByParentId(id);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(JSONArray.fromObject(treelist).toString());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
