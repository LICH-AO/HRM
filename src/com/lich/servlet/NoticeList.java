package com.gec.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.management.AttributeList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;

import com.gec.bean.Dept;
import com.gec.bean.Job;
import com.gec.bean.Notice;
import com.gec.bean.PageBean;
import com.gec.bean.Type;
import com.gec.bean.User;
import com.gec.service.NoticeService;
import com.gec.service.TypeService;
import com.gec.service.UserService;
import com.gec.service.impl.NoticeServiceImpl;
import com.gec.service.impl.TypeServiceImpl;
import com.gec.service.impl.UserServiceImpl;

/**
 * Servlet implementation class NoticeList
 */
@WebServlet(urlPatterns = { "/noticelist.action","/noticeadd.action","/noticeedit.action","/noticedel.action"})
public class NoticeList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		NoticeService ns = new NoticeServiceImpl();
		TypeService ts = new TypeServiceImpl();
		UserService us = new UserServiceImpl();
		int pageNow = 1;
		//如果是第二次进入,则获取到pageNow
		String page = request.getParameter("pageNow");
		if(page!=null) {
			pageNow = Integer.parseInt(page);
		}
		String uri = request.getRequestURI();
		uri=uri.substring(uri.lastIndexOf("/")+1);
		//获得管理员登录名
		HttpSession session = request.getSession();
		User usLogName = (User)session.getAttribute("user_session");
		System.out.println(usLogName.getLoginname());
		//获得type类型
		List<Type> type = new ArrayList<>();
		type=ts.findAll(pageNow);
		request.setAttribute("types", type);
		
		if("noticelist.action".equals(uri)) {
			PageBean<Notice> pb = new PageBean<Notice>();
			Notice not = new Notice();
			String name = request.getParameter("name");
			not.setName(name);
			pb = ns.findPage(pageNow, not);
			pb.setPageNow(pageNow);
			request.setAttribute("pb",pb);
			request.getRequestDispatcher("/WEB-INF/jsp/notice/noticelist.jsp").forward(request, response);
		}
		if("noticeadd.action".equals(uri)) {
			Notice not = new Notice();
			not.setName(request.getParameter("name"));
			not.setType(new Type(Integer.parseInt(request.getParameter("type_id"))));
			String userName = request.getParameter("userName");
			not.setUser(new User(us.findIdByName(userName)));
			
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String format = dateFormat.format(date);
			try {
				not.setModifyDate(dateFormat.parse(format));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String[] values = request.getParameterValues("submit");
			for (String string : values) {
				if(string.equals("添加")) {
					ns.save(not);
				}else {
					String id = request.getParameter("id");
					not.setId(Integer.parseInt(id));
					String text = request.getParameter("text");
					text = text.substring(text.indexOf(">")+1,text.lastIndexOf("<"));
					not.setContent(text);
					ns.update(not);
				}
			}
			request.getRequestDispatcher("/noticelist.action").forward(request, response);
		}
		if("noticeedit.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String id = request.getParameter("id");
			Notice not = ns.findById(Integer.parseInt(id));
			System.out.println("not.getContent()"+not.getContent());
			List<Type> types = ts.findAll(pageNow);
			request.setAttribute("notice", not);
			request.setAttribute("types", types);
			request.setAttribute("txt", "修改公告类型");
			request.setAttribute("sub", "确定");
			request.getRequestDispatcher("/WEB-INF/jsp/notice/noticeadd.jsp").forward(request, response);
			}
		}
		if("noticedel.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String[] ids = request.getParameterValues("noticeIds");
			for (String id : ids) {
				System.out.println("id="+id);
				boolean delete = ns.delete(Integer.parseInt(id));
				if(delete) {
					System.out.println("删除成功");
				}else {
					System.out.println("删除失败");
				}
				
			}
			request.getRequestDispatcher("/noticelist.action").forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
