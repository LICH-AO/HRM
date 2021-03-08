package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.PageBean;
import com.gec.bean.Type;
import com.gec.bean.User;
import com.gec.dao.TypeDao;
import com.gec.util.DBUtil;

public class TypeDaoImap  extends DBUtil<Type> implements TypeDao{

	@Override
	public List<Type> findAll(int pageNow) {
		return query("select * from type_inf");
	}

	@Override
	public Type findById(int id) {
		String sql = "select * from type_inf where id=?";
		return query(sql, id).get(0);
	}

	@Override
	public PageBean<Type> findPage(int pageNow, Type entity) {
		//创建PageLimit对象存储数据
		PageBean<Type> pb = new PageBean<Type>();
		List<Object> obj = new ArrayList<>();
		pb.setPageNow(pageNow);
		String sql = "select count(id) from type_inf where 1=1";
		String querySql	= "select * from type_inf where 1=1";
		if(null!=entity.getName()) {
			sql += " and name like ?";
			querySql += " and name like ?";
			obj.add("%"+entity.getName()+"%");
		}
		pb.setRowCount(getFunction(sql,obj.toArray()));
		querySql += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		List<Type> list = query(querySql ,obj.toArray());
		pb.setList(list);
		return pb;
	}

	@Override
	public boolean save(Type entity) {
		List<Object> obj = new ArrayList<Object>();
		obj.add(entity.getName());
		String sql;
			sql="insert into type_inf(name,create_date,user_id) values(?,?,?)";
			obj.add(entity.getCreateDate());
			obj.add(entity.getUser().getId());
		return update(sql, obj.toArray());
	}

	@Override
	public boolean update(Type entity) {
		List<Object> obj = new ArrayList<Object>();
		obj.add(entity.getName());
		String sql;
		//修改
		sql="update type_inf set name=?,modify_date=? where id=?";
		obj.add(entity.getModifyDate());
		obj.add(entity.getId());
		return update(sql, obj.toArray());
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from type_inf where id=?", id);
	}

	@Override
	public Type getEntity(ResultSet rs) throws Exception {
		Type tp = new Type();
		tp.setId(rs.getInt("id"));
		tp.setName(rs.getString("name"));
		tp.setCreateDate(rs.getDate("create_date"));
		tp.setUser(new User(rs.getInt("user_id")));
		tp.setModifyDate(rs.getDate("modify_date"));
		return tp;
	}

}
