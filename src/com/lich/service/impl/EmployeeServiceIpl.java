package com.lich.service.impl;

import java.util.List;

import com.lich.bean.Employee;
import com.lich.bean.PageBean;
import com.lich.dao.EmployeeDao;
import com.lich.dao.impl.EmployeeDaoImpl;
import com.lich.service.EmployeeService;

public class EmployeeServiceIpl implements EmployeeService {
	EmployeeDao ed = new EmployeeDaoImpl();
	@Override
	public List<Employee> findAll(int pageNow) {
		// TODO Auto-generated method stub
		return ed.findAll(pageNow);
	}

	@Override
	public Employee findById(int id) {
		// TODO Auto-generated method stub
		return ed.findById(id);
	}

	@Override
	public PageBean<Employee> findPage(int pageNow, Employee entity) {
		// TODO Auto-generated method stub
		return ed.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Employee entity) {
		// TODO Auto-generated method stub
		return ed.save(entity);
	}

	@Override
	public boolean update(Employee entity) {
		// TODO Auto-generated method stub
		return ed.update(entity);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return ed.delete(id);
	}

}
