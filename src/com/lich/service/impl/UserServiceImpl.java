package com.lich.service.impl;

import java.util.List;

import com.lich.bean.PageBean;
import com.lich.bean.User;
import com.lich.dao.UserDao;
import com.lich.dao.impl.UserDaoImpl;
import com.lich.service.UserService;

public class UserServiceImpl implements UserService {

	UserDao ud = new UserDaoImpl();
	@Override
	public List<User> findAll(int pageNow) {
		// TODO Auto-generated method stub
		return ud.findAll(pageNow);
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return ud.findById(id);
	}
	

	@Override
	public PageBean<User> findPage(int pageNow, User entity) {
		// TODO Auto-generated method stub
		return ud.findPage(pageNow, entity);
	}

	@Override
	public boolean save(User entity) {
		System.out.println("进入save方法");
		return ud.save(entity);
	}

	@Override
	public boolean update(User entity) {
		// TODO Auto-generated method stub
		return ud.update(entity);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return ud.delete(id);
	}

	@Override
	public User login(String loginname, String password) {
		// TODO Auto-generated method stub
		return ud.login(loginname, password);
	}

	@Override
	public Integer findIdByName(String name) {
		return ud.findIdByName(name);
	}



}
