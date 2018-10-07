package com.mine.base.data.web.service;

import java.sql.SQLException;
import java.util.Map;

import com.mine.base.data.web.model.User;

public interface LoginService {
	
	public User queryLoginUser(Map<String,Object> params) throws SQLException;

	public void updateLoginNum(Map<String,Object> params) throws SQLException;

	public void lockUser(Map<String,Object> params) throws SQLException;

	public void updateFailNum(Map<String, Object> params) throws SQLException;
}
