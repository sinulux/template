package com.mine.base.data.web.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrgService {
	public List<Map<String,Object>> queryOrgList(Map<String,Object> params) throws SQLException;

	public Map<String, Object> queryOrgListInfo(Map<String, Object> params) throws SQLException;
}
