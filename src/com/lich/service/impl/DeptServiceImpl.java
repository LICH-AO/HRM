package com.lich.service.impl;

import java.sql.ResultSet;
import java.util.List;

import com.lich.bean.Dept;
import com.lich.bean.PageBean;
import com.lich.dao.DeptDao;
import com.lich.dao.impl.DeptDaoImpl;
import com.lich.service.DeptService;
import com.lich.util.DBUtil;

public class DeptServiceImpl implements DeptService{
	DeptDao dd = new DeptDaoImpl();
	@Override
	public List<Dept> findAll(int pageNow) {
		// TODO Auto-generated method stub
		return dd.findAll(pageNow);
	}

	@Override
	public Dept findById(int id) {
		// TODO Auto-generated method stub
		return dd.findById(id);
	}

	@Override
	public PageBean<Dept> findPage(int pageNow, Dept entity) {
		// TODO Auto-generated method stub
		return dd.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Dept entity) {
		// TODO Auto-generated method stub
		return dd.save(entity);
	}

	@Override
	public boolean update(Dept entity) {
		// TODO Auto-generated method stub
		return dd.update(entity);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return dd.delete(id);
	}


}
