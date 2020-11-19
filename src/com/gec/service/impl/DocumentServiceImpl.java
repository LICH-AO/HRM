package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Document;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.DocumentDao;
import com.gec.dao.impl.DocumentDaoImpl;
import com.gec.service.DocumentService;

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
