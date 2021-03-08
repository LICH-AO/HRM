package com.lich.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lich.bean.Dept;
import com.lich.bean.Job;
import com.lich.bean.PageBean;
import com.lich.bean.User;
import com.lich.service.JobService;
import com.lich.service.impl.JobServiceImpl;

/**
 * Servlet implementation class joblist
 */
@WebServlet(urlPatterns={"/joblist.action","/jobadd.action","/jobdel.action","/jobedit.action"})
public class joblist extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JobService js = new JobServiceImpl();
		int pageNow = 1;
		//如果是第二次进入,则获取到pageNow
		String page = request.getParameter("pageNow");
		if(page!=null&&!page.equals("")) {
			pageNow = Integer.parseInt(page);
		}
		System.out.println("pageNow"+pageNow);
		//获得管理员登录名
		HttpSession session = request.getSession();
		User usLogName = (User)session.getAttribute("user_session");
		System.out.println(usLogName.getLoginname());
		//获取跳转链接
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		if("joblist.action".equals(uri)) {
			Job job = new Job();
			PageBean<Job> pb = new PageBean<>();
			job.setName(request.getParameter("name"));
			pb=js.findPage(pageNow, job);
			pb.setPageNow(pageNow);
			request.setAttribute("pb", pb);
			request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
		}
		if("jobadd.action".equals(uri)) {
			Job job = new Job();
			job.setName(request.getParameter("name"));
			job.setRemark(request.getParameter("remark"));
			String[] values = request.getParameterValues("submit");
			for (String string : values) {
				if(string.equals("添加")) {
					js.save(job);
				}else {
					String id = request.getParameter("id");
					job.setId(Integer.parseInt(id));
					js.update(job);
				}
			}
			
			request.getRequestDispatcher("joblist.action").forward(request, response);
		}
		if("jobdel.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String[] delId = request.getParameterValues("jobIds");
			for (String id : delId) {
				if(id!=null&&!id.equals("")) {
					js.delete(Integer.parseInt(id));
				}
			}
			request.getRequestDispatcher("/WEB-INF/jsp/job/joblist.jsp").forward(request, response);
			}
		}
		if("jobedit.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String id = request.getParameter("id");
			Job job = js.findById(Integer.parseInt(id));
			job.setId(Integer.parseInt(id));
			request.setAttribute("txt", "修改职位");
			request.setAttribute("sub", "修改");
			request.setAttribute("job", job);
			request.getRequestDispatcher("/WEB-INF/jsp/job/jobadd.jsp").forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
