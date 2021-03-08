package com.lich.service.impl;

import java.util.List;

import com.lich.bean.PageBean;
import com.lich.bean.Type;
import com.lich.dao.TypeDao;
import com.lich.dao.impl.TypeDaoImap;
import com.lich.service.TypeService;

public class TypeServiceImpl implements TypeService {
	TypeDao td = new TypeDaoImap();
	@Override
	public List<Type> findAll(int pageNow) {
		// TODO Auto-generated method stub
		return td.findAll(pageNow);
	}

	@Override
	public Type findById(int id) {
		// TODO Auto-generated method stub
		return td.findById(id);
	}

	@Override
	public PageBean<Type> findPage(int pageNow, Type entity) {
		// TODO Auto-generated method stub
		return td.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Type entity) {
		// TODO Auto-generated method stub
		return td.save(entity);
	}

	@Override
	public boolean update(Type entity) {
		// TODO Auto-generated method stub
		return td.update(entity);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return td.delete(id);
	}

}
