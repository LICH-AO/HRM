package com.lich.service.impl;

import java.util.List;

import com.lich.bean.Document;
import com.lich.bean.PageBean;
import com.lich.bean.User;
import com.lich.dao.DocumentDao;
import com.lich.dao.impl.DocumentDaoImpl;
import com.lich.service.DocumentService;

public class DocumentServiceImpl implements DocumentService {
	DocumentDao dd = new DocumentDaoImpl();
	@Override
	public List<Document> findAll(int pageNow) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public Document findById(int id) {
		// TODO Auto-generated method stub
		return dd.findById(id);
	}

	@Override
	public PageBean<Document> findPage(int pageNow, Document entity) {
		// TODO Auto-generated method stub
		return dd.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Document entity) {
		// TODO Auto-generated method stub
		return dd.save(entity);
	}

	@Override
	public boolean update(Document entity) {
		// TODO Auto-generated method stub
		return dd.update(entity);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return dd.delete(id);
	}

}
