package com.lich.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lich.bean.Job;
import com.lich.bean.Notice;
import com.lich.bean.PageBean;
import com.lich.bean.Type;
import com.lich.bean.User;
import com.lich.dao.NoticeDao;
import com.lich.util.DBUtil;

public class NoticeDaoImpl extends DBUtil<Notice> implements NoticeDao {

	@Override
	public List<Notice> findAll(int pageNow) {
		String sql = "select * from notice_inf";
		return query(sql);
	}

	@Override
	public Notice findById(int id) {
		String sql = "select * from notice_inf where id=?";
		return query(sql, id).get(0);
	}

	@Override
	public PageBean<Notice> findPage(int pageNow, Notice not) {
		PageBean<Notice> pb = new PageBean<>();
		List<Object> obj = new ArrayList<Object>();
		String sqlCount = "select count(*) from notice_inf where 1=1";
		String sqlInfo = "select * from notice_inf where 1=1";
		if(not.getName()!=null&&!not.getName().equals("")) {
			sqlCount+=" and name like ?";
			sqlInfo+=" and name like ?";
			obj.add("%"+not.getName()+"%");
		}
		pb.setRowCount(getFunction(sqlCount, obj.toArray()));
		sqlInfo += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sqlInfo, obj.toArray())); 
		return pb;
	}

	@Override
	public boolean save(Notice entity) {
		String sql;
		List<Object> obj = new ArrayList<Object>();
		obj.add(entity.getName());
		obj.add(entity.getType().getId());
		obj.add(entity.getContent());
		obj.add(entity.getUser().getId());
		sql="insert into notice_inf(name,type_id,content,user_id) values(?,?,?,?)";
		return update(sql, obj.toArray());
	}

	@Override
	public boolean update(Notice entity) {
		String sql;
		List<Object> obj = new ArrayList<Object>();
		obj.add(entity.getName());
		obj.add(entity.getType().getId());
		obj.add(entity.getContent());
		obj.add(entity.getModifyDate());
		obj.add(entity.getId());
		sql="update notice_inf set name=?,type_id=?,content=?,modify_date=? where id=?";
		return update(sql, obj.toArray());
	}

	@Override
	public boolean delete(int id) {
		return update("delete from notice_inf where id=?", id);
	}

	@Override
	public Notice getEntity(ResultSet rs) throws Exception {
		Notice not = new Notice();
		not.setId(rs.getInt("id"));
		not.setName(rs.getString("name"));
		not.setCreateDate(rs.getDate("create_date"));
		not.setType(new Type(rs.getInt("type_id")));
		not.setUser(new User(rs.getInt("user_id")));
		not.setModifyDate(rs.getDate("modify_date"));
		not.setContent(rs.getString("content"));
		return not;
	}

}
