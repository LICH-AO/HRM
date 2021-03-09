package com.lich.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lich.bean.Dept;
import com.lich.bean.Employee;
import com.lich.bean.Job;
import com.lich.bean.PageBean;
import com.lich.bean.User;
import com.lich.service.DeptService;
import com.lich.service.EmployeeService;
import com.lich.service.JobService;
import com.lich.service.impl.DeptServiceImpl;
import com.lich.service.impl.EmployeeServiceIpl;
import com.lich.service.impl.JobServiceImpl;

/**
 * Servlet implementation class EmolpyeeList
 */
@WebServlet(urlPatterns={"/employeelist.action","/employeeadd.atcion","/employeedel.action","/employeeedit.action"})
public class EmolpyeeList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DeptService ds = new DeptServiceImpl();
		JobService js  = new JobServiceImpl();
		EmployeeService es = new EmployeeServiceIpl();
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
		
		//获得职位信息
		List<Job> jb = new ArrayList<>();
		jb = js.findAll(pageNow);
		request.setAttribute("jobList", jb);
		//获得部门信息
		List<Dept> dt = new ArrayList<>();
		dt = ds.findAll(0);
		request.setAttribute("deptList",dt);
		
		if("employeelist.action".equals(uri)) {
			PageBean<Employee> pb = new PageBean<>();
			//获得员工信息
			Employee emp = new Employee();
			String job_id1 = request.getParameter("job_id");
			if(job_id1!=null&&!job_id1.equals("")) {
				emp.setJob(new Job(Integer.parseInt(job_id1)));
			}
			emp.setName(request.getParameter("name"));
			emp.setCardId(request.getParameter("cardId"));
			String sex1 = request.getParameter("sex");
			if(sex1!=null&&!sex1.equals("")) {
				emp.setSex(Integer.parseInt(sex1));
			}
			emp.setPhone(request.getParameter("phone"));
			String dept_id1 = request.getParameter("dept_id");
			if(dept_id1!=null&&!dept_id1.equals("")) {
				emp.setDept(new Dept(Integer.parseInt(dept_id1)));
			}
			pb = es.findPage(pageNow, emp);
			pb.setPageNow(pageNow);
			request.setAttribute("pb", pb);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeelist.jsp").forward(request, response);
		}
		if("employeeadd.atcion".equals(uri)) {
			System.out.println("进入添加员工界面");
			Employee ey = new Employee();
			ey.setName(request.getParameter("name"));
			ey.setCardId(request.getParameter("cardId"));
			String sex = request.getParameter("sex");
			if(!"".equals(sex)&&sex!=null) {
				ey.setSex(Integer.parseInt(sex));
			}
			String jobId = request.getParameter("job_id");
			ey.setJob(new Job(Integer.parseInt(jobId)));
			ey.setEducation(request.getParameter("education"));
			ey.setEmail(request.getParameter("email"));
			ey.setPhone(request.getParameter("phone"));
			ey.setTel(request.getParameter("tel"));
			ey.setParty(request.getParameter("party"));
			ey.setPostCode(request.getParameter("postCode"));
			ey.setQqNum(request.getParameter("qqNum"));
			ey.setAddress(request.getParameter("address"));
			String birthday = request.getParameter("birthday");
			try {
				ey.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
			} catch (Exception e) {
				e.printStackTrace();
			}
			ey.setRace(request.getParameter("race"));
			ey.setSpeciality(request.getParameter("speciality"));
			ey.setHobby(request.getParameter("hobby"));
			ey.setRemark(request.getParameter("remark"));
			String deptId = request.getParameter("dept_id");
			ey.setDept(new Dept(Integer.parseInt(deptId)));
			String[] values = request.getParameterValues("submit");
			for (String string : values) {
				if(string.equals("添加")) {
					es.save(ey);
				}else {
					String id = request.getParameter("id");
					ey.setId(Integer.parseInt(id));
					es.update(ey);
				}
			}
			request.getRequestDispatcher("employeelist.action").forward(request, response);
		}
		if("employeedel.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String[] delId = request.getParameterValues("employeeIds");
			for (String id : delId) {
				if(id!=null&&!id.equals("")) {
					es.delete(Integer.parseInt(id));
				}
			}
			request.getRequestDispatcher("employeelist.action").forward(request, response);
			}
		}
		if("employeeedit.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String id = request.getParameter("id");
			Employee employee = es.findById(Integer.parseInt(id));
			employee.setId(Integer.parseInt(id));
			
			System.out.println("employee.getBirthday()"+employee.getBirthday());
			List<Job> jobList = js.findAll(pageNow);
			List<Dept> deptList = ds.findAll(pageNow);
			request.setAttribute("jobList", jobList);
			request.setAttribute("deptList", deptList);
			request.setAttribute("txt", "修改员工");
			request.setAttribute("sub", "提交");
			request.setAttribute("employee", employee);
			request.getRequestDispatcher("/WEB-INF/jsp/employee/employeeadd.jsp").forward(request, response);
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
