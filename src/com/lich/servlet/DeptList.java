package com.gec.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.DeptService;
import com.gec.service.impl.DeptServiceImpl;

/**
 * Servlet implementation class DeptList
 */
@WebServlet(urlPatterns={"/deptlist.action","/deptadd.action","/deptdel.action","/deptedit.action"})
public class DeptList extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DeptService ds = new DeptServiceImpl();
		int pageNow = 1;
		//如果是第二次进入,则获取到pageNow
		String page = request.getParameter("pageNow");
		if(page!=null&&!page.equals("")) {
			pageNow = Integer.parseInt(page);
		}
		//获得管理员登录名
		HttpSession session = request.getSession();
		User usLogName = (User)session.getAttribute("user_session");
		System.out.println(usLogName.getLoginname());
		
		//获取跳转链接
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		
		if("deptlist.action".equals(uri)) {
			Dept dept = new Dept();
			PageBean<Dept> pb = new PageBean<Dept>();
			dept.setName(request.getParameter("name"));
			pb=ds.findPage(pageNow, dept);
			pb.setPageNow(pageNow);
			request.setAttribute("pb", pb);
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptlist.jsp").forward(request, response);
		}
		if("deptadd.action".equals(uri)) {
			Dept dept = new Dept();
			dept.setName(request.getParameter("name"));
			dept.setRemark(request.getParameter("remark"));
			String[] values = request.getParameterValues("submit");
			for (String string : values) {
				if(string.equals("添加")) {
					ds.save(dept);
				}else {
					String id = request.getParameter("id");
					dept.setId(Integer.parseInt(id));
					ds.update(dept);
				}
			}
			request.getRequestDispatcher("deptlist.action").forward(request, response);
		}
		if("deptdel.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String[] delId = request.getParameterValues("deptIds");
			for (String id : delId) {
				if(id!=null&&!id.equals("")) {
					ds.delete(Integer.parseInt(id));
				}
			}
			request.getRequestDispatcher("deptlist.action").forward(request, response);
			}
		}
		if("deptedit.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String id = request.getParameter("id");
			request.setAttribute("txt", "修改部门");
			request.setAttribute("sub", "提交");
			Dept dept = ds.findById(Integer.parseInt(id));
			request.setAttribute("dept", dept);
			request.getRequestDispatcher("/WEB-INF/jsp/dept/deptadd.jsp").forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
