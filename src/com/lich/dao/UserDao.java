package com.lich.dao;

import java.util.List;

import com.lich.bean.PageBean;
import com.lich.bean.User;

public interface UserDao extends BaseDao<User> {

	User login(String loginname,String password);
	
	Integer findIdByName(String name);
	
}
