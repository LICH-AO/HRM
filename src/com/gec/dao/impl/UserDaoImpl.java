package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Employee;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.UserDao;
import com.gec.util.DBUtil;


public class UserDaoImpl extends DBUtil<User> implements UserDao {

	@Override
	public List<User> findAll(int pageNow) {
		
		return null;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		List<User> user = new ArrayList<>();
		user = query("select * from user_inf where id=?", id);
		User use = new User();
		for (User user2 : user) {
			if(user2.getUsername()!=null&&!user2.getUsername().equals("")) {
				use = user2;
			}
		}
		return use;
	}

	
	
	@Override
	public PageBean<User> findPage(int pageNow, User user) {
		PageBean<User> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		String sqlCount = "select count(*) from user_inf where 1=1";
		String sqlInfo = "select * from user_inf where 1=1";
		if(user.getLoginname()!=null) {
			sqlCount+=" and loginname like ?";
			sqlInfo+=" and loginname like ?";
			obj.add("%"+user.getLoginname()+"%");
		}
		if(user.getUsername()!=null) {
			sqlCount+=" and username like ?";
			sqlInfo+=" and username like ?";
			obj.add("%"+user.getUsername()+"%");
		}
		if(user.getStatus()!=null) {
			sqlCount+=" and status like ?";
			sqlInfo+=" and status like ?";
			obj.add("%"+user.getStatus()+"%");
		}
		
		pb.setRowCount(getFunction(sqlCount, obj.toArray()));
		sqlInfo += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sqlInfo, obj.toArray())); 
		return pb;
	}

	@Override
	public boolean save(User user) {
		List<Object> obj = new ArrayList<>();
		obj.add(user.getLoginname());
		obj.add(user.getPassword());
		obj.add(user.getStatus());
		obj.add(user.getUsername());
		return update("insert into user_inf values(null,?,?,?,null,?)",obj.toArray());
	}

	@Override
	public boolean update(User user) {
		List<Object> obj = new ArrayList<>();
		obj.add(user.getLoginname());
		obj.add(user.getPassword());
		obj.add(user.getStatus());
		obj.add(user.getUsername());
		obj.add(user.getId());
		for (Object object : obj) {
			System.out.println("======");
			System.out.println(object);
		}
		return update("update user_inf set loginname=?,password=?,status=?,username=? where id=?", obj.toArray());
	}

	@Override
	public boolean delete(int id) {
		return update("delete from user_inf where id=?", id);
	}
	
	@Override
	public User login(String loginname, String password) {
		List<User> list = query("select * from user_inf where loginname=? and password=?", loginname,password);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public User getEntity(ResultSet rs) throws Exception {
		User user = new User();
		user.setId(rs.getInt(1));
		user.setLoginname(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setStatus(rs.getInt(4));
		user.setCreateDate(rs.getDate(5));
		user.setUsername(rs.getString(6));
		return user;
	}

	@Override
	public Integer findIdByName(String name) {
		// TODO Auto-generated method stub
		List<User> query = query("select * from user_inf where username = ?", name);
		Integer id = null;
		for (User user : query) {
			id=user.getId();
			System.out.println("id=================="+id);
		}
		return id;
	}



}
