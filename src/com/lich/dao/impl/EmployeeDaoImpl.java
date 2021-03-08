package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.Document;
import com.gec.bean.Employee;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.EmployeeDao;
import com.gec.util.DBUtil;

public class EmployeeDaoImpl extends DBUtil<Employee> implements EmployeeDao {

	@Override
	public List<Employee> findAll(int pageNow) {
		
		return null;
	}

	@Override
	public Employee findById(int id) {
		List<Employee> dept = new ArrayList<>();
		dept = query(" select * from" + 
				" (" + 
				" select e.*,j.NAME as job_name from" + 
				" (select e.*,d.NAME as dept_name  from employee_inf e left join dept_inf d on e.DEPT_ID=d.ID) e" + 
				" left join job_inf j on  e.JOB_ID=j.ID" + 
				" ) t where id=?", id);
		return dept.get(0);
	}

	@Override
	public PageBean<Employee> findPage(int pageNow, Employee emp) {
		PageBean<Employee> pb = new PageBean<>();
		List<Object> obj = new ArrayList<>();
		String sql = " ( "+ 
				" select e.*,j.NAME as job_name from" + 
				" (select e.*,d.NAME as dept_name  from employee_inf e left join dept_inf d on e.DEPT_ID=d.ID) e" + 
				" left join job_inf j on  e.JOB_ID=j.ID" + 
				" ) t";
		String sqlCount = "select count(*) from "+sql+" where 1=1";
		String sqlInfo = "select * from "+sql+" where 1=1";
		if(emp.getJob()!=null&&emp.getJob().getId()>0) {
			sqlCount+=" and job_id=?";
			sqlInfo+=" and job_id=?";
			obj.add(emp.getJob().getId());
		}
		if(emp.getName()!=null&&!emp.getName().equals("")) {
			sqlCount+=" and name like ?";
			sqlInfo+=" and name like ?";
			obj.add("%"+emp.getName()+"%");
		}
		if(emp.getCardId()!=null&&!emp.getCardId().equals("")) {
			sqlCount+=" and card_id like ?";
			sqlInfo+=" and card_id like ?";
			obj.add("%"+emp.getCardId()+"%");
		}
		if(emp.getSex()!=null) {
			sqlCount+=" and sex = ?";
			sqlInfo+=" and sex = ?";
			obj.add(emp.getSex());
		}
		if(emp.getPhone()!=null&&!emp.getPhone().equals("")) {
			sqlCount+=" and phone like ?";
			sqlInfo+=" and phone like ?";
			obj.add("%"+emp.getPhone()+"%");
		}
		if(emp.getDept()!=null&&emp.getDept().getId()>0) {
			sqlCount+=" and dept_id=?";
			sqlInfo+=" and dept_id=?";
			obj.add(emp.getDept().getId());
		}
		pb.setRowCount(getFunction(sqlCount, obj.toArray()));
		sqlInfo += " limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		pb.setList(query(sqlInfo, obj.toArray())); 
		return pb;
	}

	@Override
	public boolean save(Employee emp) {
		List<Object> obj = new ArrayList<>();
		obj.add(emp.getName());
		obj.add(emp.getCardId());
		obj.add(emp.getAddress());
		obj.add(emp.getPostCode());
		obj.add(emp.getTel());
		obj.add(emp.getPhone());
		obj.add(emp.getQqNum());
		obj.add(emp.getEmail());
		obj.add(emp.getSex());
		obj.add(emp.getParty());
		obj.add(emp.getBirthday());
		obj.add(emp.getRace());
		obj.add(emp.getEducation());
		obj.add(emp.getSpeciality());
		obj.add(emp.getHobby());
		obj.add(emp.getRemark());
		obj.add(emp.getDept().getId());
		obj.add(emp.getJob().getId());
		return update("insert into employee_inf values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,null,null,?,?)"
				,obj.toArray());
//		return false;
	}

	@Override
	public boolean update(Employee ey) {
		String sql="update employee_inf set dept_id=?,JOB_ID=?,NAME=?,CARD_ID=?,"
			    + "ADDRESS=?,POST_CODE=?,TEL=?,PHONE=?,QQ_NUM=?,EMAIL=?,SEX=?,PARTY=?,"
			    + "BIRTHDAY=?,RACE=?,EDUCATION=?,SPECIALITY=?,HOBBY=?,REMARK=? where id=?";
	  return update(sql,ey.getDept().getId(),ey.getJob().getId(),ey.getName(),ey.getCardId(),ey.getAddress(),
			    ey.getPostCode(),ey.getTel(),ey.getPhone(),ey.getQqNum(),ey.getEmail(),
			    ey.getSex(),ey.getParty(),ey.getBirthday(),ey.getRace(),
			    ey.getEducation(),ey.getSpeciality(),ey.getHobby(),ey.getRemark(),ey.getId());
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return update("delete from employee_inf where id=?", id);
	}

	@Override
	public Employee getEntity(ResultSet rs) throws Exception {
		Employee emp = new Employee();
		emp.setId(rs.getInt(1));
		emp.setName(rs.getString(2));
		emp.setCardId(rs.getString(3));
		emp.setAddress(rs.getString(4));
		emp.setPostCode(rs.getString(5));
		emp.setTel(rs.getString(6));
		emp.setPhone(rs.getString(7));
		emp.setQqNum(rs.getString(8));
		emp.setEmail(rs.getString(9));
		emp.setSex(rs.getInt(10));
		emp.setParty(rs.getString(11));
		emp.setBirthday(rs.getDate(12));
		emp.setRace(rs.getString(13));
		emp.setEducation(rs.getString(14));
		emp.setSpeciality(rs.getString(15));
		emp.setHobby(rs.getString(16));
		emp.setRemark(rs.getString(17));
		emp.setCreateDate(rs.getDate(18));
		emp.setDept(new Dept(rs.getInt(20)));
		emp.setJob(new Job(rs.getInt(21)));
		emp.setDept(new Dept(rs.getString(22)));
		emp.setJob(new Job(rs.getString(23)));
		return emp;
	}

}
