package com.lich.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lich.bean.Dept;
import com.lich.bean.Job;
import com.lich.bean.PageBean;
import com.lich.dao.JobDao;
import com.lich.util.DBUtil;

public class JobDaoImpl extends DBUtil<Job> implements JobDao {

	@Override
	public List<Job> findAll(int pageNow) {
		return query("select * from job_inf");
	}

	@Override
	public Job findById(int id) {
		List<Job> job = new ArrayList<>();
		job = query("select * from job_inf where id=?", id);
		return job.get(0);
	}

	@Override
	public PageBean<Job> findPage(int pageNow, Job job) {
		PageBean<Job> pb = new PageBean<>();
		List<Object> obj = new ArrayList<Object>();
		String sqlCount = "select count(*) from job_inf where 1=1";
		String sqlInfo = "select * from job_inf where 1=1";
		if(job.getName()!=null&&!job.getName().equals("")) {
			sqlCount+=" and name like ?";
			sqlInfo+=" and name like ?";
			obj.add("%"+job.getName()+"%");
		}
		pb.setRowCount(getFunction(sqlCount, obj.toArray()));
		sqlInfo += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sqlInfo, obj.toArray())); 
		return pb;
	}

	@Override
	public boolean save(Job entity) {
		List<Object> obj = new ArrayList<Object>();
		obj.add(entity.getName());
		obj.add(entity.getRemark());
		return update("insert into job_inf values(null,?,?,null)",obj.toArray());
	}

	@Override
	public boolean update(Job entity) {
		List<Object> obj = new ArrayList<Object>();
		obj.add(entity.getName());
		obj.add(entity.getRemark());
		obj.add(entity.getId());
		return update("update job_inf set name=?,remark=? where id=?",obj.toArray());
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from job_inf where id=?", id);
	}

	@Override
	public Job getEntity(ResultSet rs) throws Exception {
		Job jb = new Job();
		jb.setId(rs.getInt(1));
		jb.setName(rs.getString(2));
		jb.setRemark(rs.getString(3));
		return jb;
	}

}
