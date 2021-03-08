package com.lich.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.lich.bean.Dept;
import com.lich.bean.Document;
import com.lich.bean.Job;
import com.lich.bean.PageBean;
import com.lich.bean.User;
import com.lich.dao.DocumentDao;
import com.lich.util.DBUtil;

public class DocumentDaoImpl extends DBUtil<Document> implements DocumentDao {

	@Override
	public List<Document> findAll(int pageNow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document findById(int id) {
		List<Document> dept = new ArrayList<>();
		dept = query("select * from document_inf where id=?", id);
		return dept.get(0);
	}

	@Override
	public PageBean<Document> findPage(int pageNow, Document dm) {
		PageBean<Document> pb = new PageBean<>();
		List<Object> obj = new ArrayList<Object>();
		String sqlCount = "select count(*) from document_inf where 1=1";
		String sqlInfo = "select * from document_inf where 1=1";
		if(dm.getTitle()!=null&&!"".equals(dm.getTitle())) {
			sqlCount+=" and title like ?";
			sqlInfo+=" and title like ?";
			obj.add("%"+dm.getTitle()+"%");
		}
		pb.setRowCount(getFunction(sqlCount, obj.toArray()));
		sqlInfo += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sqlInfo, obj.toArray())); 
		return pb;
	}

	@Override
	public boolean save(Document doc) {
		return update("insert into document_inf VALUES(null,?,?,?,?,?,null,?)"
				,doc.getTitle(),doc.getFileName(),doc.getFiletype(),doc.getFileBytes(),doc.getRemark(),doc.getUser().getId());
	}

	@Override
	public boolean update(Document entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from document_inf where id=?", id);
	}

	@Override
	public Document getEntity(ResultSet rs) throws Exception {
		Document dm = new Document();
		dm.setId(rs.getInt("id"));
		dm.setTitle(rs.getString("title"));
		dm.setFileName(rs.getString("filename"));
		dm.setFiletype(rs.getString("filetype"));
		dm.setFileBytes(rs.getBytes("filebytes"));
		dm.setRemark(rs.getString("remark"));
		dm.setCreateDate(rs.getDate("create_date"));
		dm.setUser(new User(rs.getInt("user_id")));
		return dm;
	}

}
