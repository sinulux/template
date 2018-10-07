package com.mine.base.data.web.service.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mine.base.data.common.mybatis.ParentService;
import com.mine.base.data.common.util.Constants;
import com.mine.base.data.common.util.PageMap;
import com.mine.base.data.web.service.UserService;

@Service("userService")
public class UserServiceImpl extends ParentService implements UserService{

	@Override
	public Map<String, Object> userListQuery(Map<String, Object> param)
			throws SQLException {
		PageMap pageMap = new PageMap();
		pageMap.setPageSize(Integer.valueOf(param.get("pageSize").toString()));
		pageMap.setStartNum(Integer.valueOf(param.get("startNum").toString()));
		param.put("pageMap", pageMap);
		List<Map<String, Object>> list = this.selectList("login.userListQuery", param);
		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("totalRecord", pageMap.getTotalRecord());
		retMap.put("resultList", list);
		return retMap;
	}

	@Override
	public Map<String, Object> saveUserInfo(Map<String, Object> paramMap)
			throws SQLException {
		Object userId = paramMap.get("userId");
		if(userId == null || "".equals(userId.toString())){
			this.insert("login.saveUserInfo", paramMap);
		}else{
			this.insert("login.updateUserInfo", paramMap);
		}
		Map<String,Object> rstMap = new HashMap<String,Object>();
		rstMap.put("code", Constants.SUCCESS_CODE);
		rstMap.put("desc", "用户信息保存成功！");
		return rstMap;
	}

	@Override
	public String demo(Map<String, Object> paramMap) throws SQLException {
		paramMap = new HashMap<String,Object>();
		return this.selectOne("service.demo1", paramMap);
	}

}
