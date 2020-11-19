package com.gec.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import javax.websocket.Session;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gec.bean.Dept;
import com.gec.bean.Document;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.service.DocumentService;
import com.gec.service.UserService;
import com.gec.service.impl.DocumentServiceImpl;
import com.gec.service.impl.UserServiceImpl;
import com.gec.util.DBUtil;

/**
 * Servlet implementation class documentlist
 */
@WebServlet(urlPatterns = {"/documentlist.action","/documentadd.action","/documentedit.action"
		,"/documentdel.action","/documentdownload.action"})
public class Documentlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserService us = new UserServiceImpl();
		DocumentService ds = new DocumentServiceImpl();
		int pageNow = 1;
		String page = request.getParameter("pageNow");
		if(page!=null&&!page.equals("")) {
			pageNow = Integer.parseInt(page);
		}
		//获得管理员登录名
		HttpSession session = request.getSession();
		User usLogName = (User)session.getAttribute("user_session");
		System.out.println(usLogName.getLoginname());
		String uri = request.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/")+1);
		
		if("documentlist.action".equals(uri)) {
			Document doc = new Document();
			PageBean<Document> pb = new PageBean<>();
			doc.setTitle(request.getParameter("title"));
			pb = ds.findPage(pageNow, doc);
			pb.setPageNow(pageNow);
			for(int i = 0;i<pb.getList().size();i++) {
				User findById = us.findById(pb.getList().get(i).getUser().getId());
				pb.getList().get(i).setUser(findById);
			}
			request.setAttribute("pb", pb);
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentlist.jsp").forward(request, response);
		}
		
		if("documentadd.action".equals(uri)) {
			Document doc = new Document();
			boolean flag = false;  //用于判断文件是否上传成功
			String submit = "";//判断submit是添加还是修改
			/*
			 * 是通过upload上传组件进行获取内容
			 */
			//1.判断是否为二进制流提交内容
			if(ServletFileUpload.isMultipartContent(request)){
				//2.创建一个存储工厂来存储数据内容
				//DiskFileItemFactory 是一个内存数据保存工厂对象
				FileItemFactory factory = new DiskFileItemFactory();
				//3.获取到组件中servletFileUpload,将所解析的内容放入工厂,通过工厂转换为每一项文件
				ServletFileUpload upload = new ServletFileUpload(factory);
				//4.通过servletFileUpload类中的parseRequest将request中的数据转换为FileItem
			try {
				@SuppressWarnings("unchecked")
				List<FileItem> list = upload.parseRequest(request);
				if(list!=null){
					//5.循环所有项,
					for (FileItem item : list) {
						//判断file是否为普通表单文件,isFormField判断 是普通表单数据则返回true 否则返回false
						if(item.isFormField()){
							if("title".equals(item.getFieldName())){
								//如果找到对应属性名,直接获取对应的值
								doc.setTitle(item.getString("utf-8"));
							}
							if("remark".equals(item.getFieldName())){
								doc.setRemark(item.getString("utf-8"));
							}
							if("userName".equals(item.getFieldName())){
								//item.getString("utf-8")
								//,us.findIdByName(item.getString("utf-8"))
								doc.setUser(new User(us.findIdByName(item.getString("utf-8")),item.getString("utf-8")));
								System.out.println("doc.getUser().getUsername()+doc.getUser().getId()"+doc.getUser().getUsername()+doc.getUser().getId());
							}
							if("docSubmit".equals(item.getFieldName())){
								submit = item.getString("utf-8");
								System.out.println(submit+"submitsubmitsubmit");
							}
						}else{
							//6.获取到要存储的文件夹
							String path = request.getServletContext().getRealPath("/upload");
							File file = new File(path);
							//判断该路径是否存在,如果不存在就新建
							if(!file.exists()){
								file.mkdirs();
							}
							//获取文件名
							String fileName = item.getName();
							String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
							fileName = fileName.substring(0, fileName.lastIndexOf("."))+System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
							File newFile = new File(file, fileName);
							//将item写出到指定文件中newFile
							item.write(newFile);
							flag = true;
							//将文件名保存在对象中
							//将文件写入byte 	
							int length = (int) newFile.length();
							 byte[] data = new byte[length];
							 FileInputStream in = new FileInputStream(newFile);
							 in.read(data);
							in.close();
							doc.setFileName(fileName);
							doc.setFiletype(fileType);
							doc.setFileBytes(data);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//普通提交方式获取
			doc.setUser(new User((request.getParameter("userName"))));
			flag = true;
		}
			
		if(flag){
				if(submit.equals("上传")) {
					ds.save(doc);
				}else {
					String id = request.getParameter("id");
					if(id!=null&&!"".equals(id)) {
						doc.setId(Integer.parseInt(id));
					}else {
						System.out.println(id+"idididid");
					}
					
					ds.update(doc);
				}
			
			request.setAttribute("doc", doc);
			request.getRequestDispatcher("/documentlist.action").forward(request, response);
		}else{
			response.sendRedirect("/WEB-INF/jsp/document/documentadd.jsp");
		}
		}
		if("documentedit.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String id = request.getParameter("id");
			request.setAttribute("txt", "修改文件");
			request.setAttribute("sub", "提交");
			Document doc = ds.findById(Integer.parseInt(id));
			request.setAttribute("document", doc);
			ds.update(doc);
			request.getRequestDispatcher("/WEB-INF/jsp/document/documentadd.jsp").forward(request, response);
			}
		}
		if("documentdel.action".equals(uri)) {
			if(!usLogName.getLoginname().equals("admin")) {
				request.getRequestDispatcher("test.jsp").forward(request, response);
			}else {
			String delId = request.getParameter("ids");
			String[] splitAddress=delId.split(","); 
			for (String string : splitAddress) {
				if(string!=null&&!string.equals("")) {
					ds.delete(Integer.parseInt(string));
				}
			}
				
			request.getRequestDispatcher("documentlist.action").forward(request, response);
			}
		}
		if("documentdownload.action".equals(uri)) {
			//得到文件夹真实路径
			String path = request.getServletContext().getRealPath("/upload");
			//得到要下载的文件名
			String id = request.getParameter("id");
			Document doc = ds.findById(Integer.parseInt(id));
			String fileName = doc.getFileName();
			System.out.println("path>>>"+path+">>>>>"+fileName);
			//File.separator>>File提供的自适应斜杠，是静态常量
			InputStream in = new FileInputStream(path+File.separator+fileName);
			//设置相应头内容，设置响应类型，并设置下载文件的名字
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			//创建文件输出流，输出到客户端
			ServletOutputStream out = response.getOutputStream();
			int len = 0;
			byte[] b = new byte[1024];
			while((len=in.read(b))>0) {
				out.write(b,0,len);
			}
			//关闭流
			out.flush();
			out.close();
//			request.getRequestDispatcher("documentlist.action").forward(request, response);
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
