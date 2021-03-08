package com.lich.service.impl;

import java.util.List;

import com.lich.bean.Job;
import com.lich.bean.PageBean;
import com.lich.dao.JobDao;
import com.lich.dao.impl.JobDaoImpl;
import com.lich.service.JobService;

public class JobServiceImpl implements JobService {
JobDao jd = new JobDaoImpl();
	@Override
	public List<Job> findAll(int pageNow) {
		// TODO Auto-generated method stub
		return jd.findAll(pageNow);
	}

	@Override
	public Job findById(int id) {
		// TODO Auto-generated method stub
		return jd.findById(id);
	}

	@Override
	public PageBean<Job> findPage(int pageNow, Job entity) {
		// TODO Auto-generated method stub
		return jd.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Job entity) {
		// TODO Auto-generated method stub
		return jd.save(entity);
	}

	@Override
	public boolean update(Job entity) {
		// TODO Auto-generated method stub
		return jd.update(entity);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return jd.delete(id);
	}

}
