package com.lich.dao;

import java.util.List;

import com.lich.bean.PageBean;

public interface BaseDao<T> {

	List<T> findAll(int pageNow);    
	T findById(int id);
	
	PageBean<T> findPage(int pageNow,T entity);
	
	boolean save(T entity);
	
	boolean update(T entity);
	
	boolean delete(int id);
}
