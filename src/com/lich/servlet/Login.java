package com.lich.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lich.bean.User;
import com.lich.service.UserService;
import com.lich.service.impl.UserServiceImpl;

@WebServlet(urlPatterns={"/loginForm.action","/login.action","/logout.action","/indexMain.action"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService us = new UserServiceImpl();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//区分请求执行不同的代码
		//1.获取请求路径
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		//2.判断请求路径,执行不同代码
		if("login.action".equals(uri)){
			String loginname = request.getParameter("loginname");
			String password = request.getParameter("password");
			User user = us.login(loginname, password);
			if(user!=null){
//				//加入到session
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(60*600);
				session.setAttribute("user_session", user);
				//跳转到主页面
				request.getRequestDispatcher("/main.action").forward(request, response);
			}else{
				request.setAttribute("message", "用户名或密码错误");
				request.getRequestDispatcher("/loginForm.action").forward(request, response);
			}
		}
		if("loginForm.action".equals(uri)) {
			//进入登录界面
			request.getRequestDispatcher("/WEB-INF/jsp/loginForm.jsp").forward(request, response);
		}
		if("logout.action".equals(uri)) {
			System.out.println("进入登录界面");
			//进入登录界面
			request.getRequestDispatcher("/loginForm.action").forward(request, response);
		}
		if("indexMain.action".equals(uri)) {
			System.out.println("进入主界面");
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
