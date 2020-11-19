package com.gec.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gec.bean.Dept;
import com.gec.bean.Job;
import com.gec.bean.Type;
import com.gec.bean.User;
import com.gec.service.DeptService;
import com.gec.service.EmployeeService;
import com.gec.service.JobService;
import com.gec.service.TypeService;
import com.gec.service.impl.DeptServiceImpl;
import com.gec.service.impl.EmployeeServiceIpl;
import com.gec.service.impl.JobServiceImpl;
import com.gec.service.impl.TypeServiceImpl;


@WebServlet(urlPatterns={"/main.action","/left.action","/top.action","/right.action","/useradd.action"
		,"/employeeadd.action","/deptaddd.action","/jobaddd.action","/noticeaddd.action","/documentaddd.action"})
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DeptService ds = new DeptServiceImpl();
		TypeService ts = new TypeServiceImpl();
		JobService js  = new JobServiceImpl();
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user_session");
		System.out.println(user.getLoginname());
		int pageNow = 1;
		String page = request.getParameter("pageNow");
		if(page!=null&&!page.equals("")) {
			pageNow = Integer.parseInt(page);
		}
		String uri = request.getRequestURI();
		System.out.println(uri);
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("main.action".equals(uri)){
			request.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(request, response);
		}
		if("left.action".equals(uri)){
			request.getRequestDispatcher("/WEB-INF/jsp/left.jsp").forward(request, response);
		}
		if("top.action".equals(uri)){
			request.getRequestDispatcher("/WEB-INF/jsp/top.jsp").forward(request, response);
		}
		if("right.action".equals(uri)){
			request.getRequestDispatcher("/WEB-INF/jsp/right.jsp").forward(request, response);
		}
		if("useradd.action".equals(uri)) {
			
			if(!user.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
				request.setAttribute("txt", "添加用户");
				request.setAttribute("sub", "添加");
				request.getRequestDispatcher("/WEB-INF/jsp/user/useradd.jsp").forward(request, response);
			}
		}
		if("employeeadd.action".equals(uri)) {
			if(!user.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			//获得职位信息
			List<Job> jb = new ArrayList<>();
			jb = js.findAll(pageNow);
			request.setAttribute("jobList", jb);
			//获得部门信息
			List<Dept> dt = new ArrayList<>();
			dt = ds.findAll(0);
			request.setAttribute("deptList",dt);
			request.setAttribute("txt", "添加员工");
			request.setAttribute("sub", "添加");
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeadd.jsp").forward(request, response);
			}
		}
		if("deptaddd.action".equals(uri)) {
			if(!user.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			request.setAttribute("txt", "添加部门");
			request.setAttribute("sub", "添加");
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptadd.jsp").forward(request, response);
			}
		}
		if("jobaddd.action".equals(uri)) {
			if(!user.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			request.setAttribute("txt", "添加职位");
			request.setAttribute("sub", "添加");
			request.getRequestDispatcher("/WEB-INF/jsp/job/jobadd.jsp").forward(request, response);
			}
		}
		if("noticeaddd.action".equals(uri)) {
			if(!user.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			//获得type类型
			List<Type> type = new ArrayList<>();
			type=ts.findAll(pageNow);
			request.setAttribute("types", type);
			request.setAttribute("txt", "添加公告类型");
			request.setAttribute("sub", "添加");
			request.getRequestDispatcher("/WEB-INF/jsp/notice/noticeadd.jsp").forward(request, response);
			}
		}
		if("documentaddd.action".equals(uri)) {
			if(!user.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			request.setAttribute("txt", "上传文件");
			request.setAttribute("sub", "上传");
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
			}
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
