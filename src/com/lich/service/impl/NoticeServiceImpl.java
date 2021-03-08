package com.lich.service.impl;

import java.util.List;

import com.lich.bean.Notice;
import com.lich.bean.PageBean;
import com.lich.dao.NoticeDao;
import com.lich.dao.impl.NoticeDaoImpl;
import com.lich.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {
	NoticeDao nd = new NoticeDaoImpl();
	@Override
	public List<Notice> findAll(int pageNow) {
		// TODO Auto-generated method stub
		return nd.findAll(pageNow);
	}

	@Override
	public Notice findById(int id) {
		// TODO Auto-generated method stub
		return nd.findById(id);
	}

	@Override
	public PageBean<Notice> findPage(int pageNow, Notice entity) {
		// TODO Auto-generated method stub
		return nd.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Notice entity) {
		// TODO Auto-generated method stub
		return nd.save(entity);
	}

	@Override
	public boolean update(Notice entity) {
		// TODO Auto-generated method stub
		
		return nd.update(entity);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return nd.delete(id);
	}

}
