package com.mine.base.data.web.service;

import java.sql.SQLException;
import java.util.Map;

public interface UserService {

	Map<String, Object> userListQuery(Map<String, Object> paramMap) throws SQLException;

	Map<String, Object> saveUserInfo(Map<String, Object> paramMap) throws SQLException;;
	String demo(Map<String, Object> paramMap) throws SQLException;;
}
