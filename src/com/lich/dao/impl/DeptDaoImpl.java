package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.PageBean;
import com.gec.dao.DeptDao;
import com.gec.util.DBUtil;

public class DeptDaoImpl extends DBUtil<Dept> implements DeptDao {

	@Override
	public List<Dept> findAll(int pageNow) {
		return query("select * from dept_inf");
	}

	@Override
	public Dept findById(int id) {
		List<Dept> dept = new ArrayList<>();
		dept = query("select * from dept_inf where id=?", id);
		return dept.get(0);
	}

	@Override
	public PageBean<Dept> findPage(int pageNow, Dept dept) {
		PageBean<Dept> pb = new PageBean<>();
		List<Object> obj = new ArrayList<Object>();
		String sqlCount = "select count(*) from dept_inf where 1=1";
		String sqlInfo = "select * from dept_inf where 1=1";
		if(dept.getName()!=null&&!dept.getName().equals("")) {
			sqlCount+=" and name like ?";
			sqlInfo+=" and name like ?";
			obj.add("%"+dept.getName()+"%");
		}
		pb.setRowCount(getFunction(sqlCount, obj.toArray()));
		sqlInfo += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sqlInfo, obj.toArray())); 
		return pb;
	}

	@Override
	public boolean save(Dept entity) {
		List<Object> obj = new ArrayList<Object>();
		obj.add(entity.getName());
		obj.add(entity.getRemark());
		return update("insert into dept_inf values(null,?,?,null)",obj.toArray());
	}

	@Override
	public boolean update(Dept entity) {
		List<Object> obj = new ArrayList<Object>();
		obj.add(entity.getName());
		obj.add(entity.getRemark());
		obj.add(entity.getId());
		return update("update dept_inf set name=?,remark=? where id=?",obj.toArray());
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from dept_inf where id=?", id);
	}

	@Override
	public Dept getEntity(ResultSet rs) throws Exception {
		Dept dt = new Dept();
		dt.setId(rs.getInt("id"));
		dt.setName(rs.getString("name"));
		dt.setRemark(rs.getString("remark"));
		return dt;
	}

}
