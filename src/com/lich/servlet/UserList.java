package com.lich.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lich.bean.PageBean;
import com.lich.bean.User;
import com.lich.service.UserService;
import com.lich.service.impl.UserServiceImpl;
/**
 * Servlet implementation class User
 */
@WebServlet(urlPatterns={"/userlist.action","/useraddsave.action","/userdel.action","/useredit.action"})
public class UserList extends HttpServlet {
	private static final long serialVersionUID = 1L;
   UserService us = new UserServiceImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNow = 1;
		//如果是第二次进入,则获取到pageNow
		String page = request.getParameter("pageNow");
		if(page!=null) {
			pageNow = Integer.parseInt(page);
		}
		System.out.println("1111pageNow>>>>"+pageNow);
		//获得管理员登录名
		HttpSession session = request.getSession();
		User usLogName = (User)session.getAttribute("user_session");
		System.out.println(usLogName.getLoginname());
		//获取跳转链接
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("userlist.action".equals(uri)){
			PageBean<User> pb = new PageBean<>();
			User user = new User();
			user.setLoginname(request.getParameter("loginname"));
			user.setUsername(request.getParameter("username"));
			String statusStr = (request.getParameter("status"));
			//进行判断防止Integer报NumberFormatException
			Integer status = null;
			if(statusStr!=null&&!"".equals(statusStr)) {
				user.setStatus(Integer.parseInt(statusStr));
			}
			pb=us.findPage(pageNow, user);
			pb.setPageNow(pageNow);
			request.setAttribute("pb", pb);
			request.getRequestDispatcher("/WEB-INF/jsp/user/userlist.jsp").forward(request, response);

		}
		if("useraddsave.action".equals(uri)) {
			System.out.println("进入addServlet");
			User user = new User();
			String username = request.getParameter("username");
			String statusStr = request.getParameter("status");
			Integer status = null;
			if(null!=statusStr&&!("").equals(statusStr)) {
				status = Integer.parseInt(statusStr);
			}
			String loginname = request.getParameter("loginname");
			String password = request.getParameter("password");
			user.setUsername(username);
			user.setStatus(status);
			user.setLoginname(loginname);
			user.setPassword(password);
			
			String[] values = request.getParameterValues("submit");
			for (String string : values) {
				if(string.equals("添加")) {
					us.save(user);
				}else {
					String id = request.getParameter("id");
					user.setId(Integer.parseInt(id));
					us.update(user);
				}
			}
			
			request.setAttribute("txt", "添加用户");
			request.setAttribute("sub", "添加");
			request.getRequestDispatcher("userlist.action").forward(request, response);
		}
		if("userdel.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String[] delId = request.getParameterValues("userIds");
			for (String id : delId) {
				if(id!=null&&!id.equals("")) {
					us.delete(Integer.parseInt(id));
				}
			}
			request.getRequestDispatcher("userlist.action").forward(request, response);
			}
		}
		if("useredit.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String id = request.getParameter("id");
			Integer idd = Integer.parseInt(id);
			request.setAttribute("txt", "修改用户");
			request.setAttribute("sub", "提交");
			User user = us.findById(idd);
			user.setId(idd);
			request.setAttribute("user", user);
			request.getRequestDispatcher("/WEB-INF/jsp/user/useradd.jsp").forward(request, response);
			}
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
