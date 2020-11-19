package com.gec.dao;

import java.util.List;

import com.gec.bean.PageBean;
import com.gec.bean.User;

public interface UserDao extends BaseDao<User> {

	User login(String loginname,String password);
	
	Integer findIdByName(String name);
	
}
