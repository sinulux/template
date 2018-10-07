package com.mine.base.data.web.action;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mine.base.data.common.util.Constants;
import com.mine.base.data.web.channel.BaseAction;
import com.mine.base.data.web.model.User;
import com.mine.base.data.web.service.ResAttrService;

@Service("resAttr")
public class ResAttrAction extends BaseAction{
	
	@Resource
	private ResAttrService resAttrService;
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 查询资源属性规格
	 * @param request
	 * @param response
	 * @param rtnMap
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked"})
	public void queryResAttr(HttpServletRequest request, HttpServletResponse response, Map<String, Object> rtnMap)throws IOException{
		try {
			logger.info("ResAttrAction.queryResAttr: 查询资源属性规格!");
			Map<String, Object> param = (Map<String, Object>)this.dataToMap(request);
			long SYSTEM_USER_ID =((User)request.getSession().getAttribute("user")).getUserId();
			param.put("userId", SYSTEM_USER_ID);
			Map<String, Object> resMap = resAttrService.queryResAttr(param);
			rtnMap.put("data", resMap);
		} catch (Exception e) {
			logger.error("ResAttrAction.queryResAttr: 查询失败!"+e.getMessage());
		}
	}
	
	/**
	 * 添加资源属性规格
	 * @param request
	 * @param response
	 * @param rtnMap
	 */
	public void addResAttr(HttpServletRequest request, HttpServletResponse response, Map<String, Object> rtnMap) {
		try {
			logger.info("ResAttrAction.addResAttr: 添加资源属性规格及属性值列表!");
			Map map=(Map) this.dataToMap(request);
			resAttrService.addResAttr(map);
			rtnMap.put("msg", "添加成功!");
		} catch (Exception e) {
			logger.error("ResAttrAction.addResAttr: 添加失败!"+e.getMessage());
			rtnMap.put("error", "添加失败!");
		}
	}
	
	/**
	 * 加载资源属性及属性值列表
	 * @param request
	 * @param response
	 * @param rtnMap
	 */
	public void loadResAttrList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> rtnMap){
		try {
			logger.info("ResAttrAction.loadResAttrList: 加载资源属性规格及属性值列表!");
			Map map=(Map) this.dataToMap(request);
			Map<String,Object> data = resAttrService.loadResAttrList(map);
			rtnMap.put("data", data);
		} catch (Exception e) {
			logger.error("ResAttrAction.loadResAttrList: 加载数据失败!"+e.getMessage());
			rtnMap.put("error", "加载数据失败!");
		}
	}
	
	/**
	 * 检查属性规格是否使用中
	 * @param request
	 * @param response
	 * @param rtnMap
	 */
	public void checResAttr(HttpServletRequest request, HttpServletResponse response, Map<String, Object> rtnMap){
		try {
			logger.info("ResAttrAction.checResAttr: 检查属性规格是否使用中!");
			Map map=(Map) this.dataToMap(request);
			Map<String,Object> msg= resAttrService.checResAttr(map);
			if(msg!=null && msg.size()!=0){
				rtnMap.put("error", msg.get("msg"));
			}
		} catch (Exception e) {
			logger.error("ResAttrAction.checResAttr: 检查失败!"+e.getMessage());
			rtnMap.put("error", "检查失败!");
		}
	}

	/**
	 * 修改资源属性规格
	 * @param request
	 * @param response
	 * @param rtnMap
	 */
	public void updateResAttr(HttpServletRequest request, HttpServletResponse response, Map<String, Object> rtnMap) {
		try {
			logger.info("ResAttrAction.updateResAttr: 更新资源属性规格!");
			Map map=(Map) this.dataToMap(request);
			Map<String,Object> msg = resAttrService.updateResAttr(map);
			rtnMap.put("msg", msg.get("msg"));
		} catch (Exception e) {
			logger.error("ResAttrAction.updateResAttr: 更新资源属性规格失败!"+e.getMessage());
			rtnMap.put("error","更新资源属性规格失败!");
		}
	}

	/**
	 * 删除资源属性规格
	 * @param request
	 * @param response
	 * @param rtnMap
	 */
	public void delResAttr(HttpServletRequest request, HttpServletResponse response, Map<String, Object> rtnMap) {
		try {
			logger.info("ResAttrAction.delResAttr: 删除资源属性规格!");
			Map map=(Map) this.dataToMap(request);
			String [] ids =(map.get("ids")+"").split(",");
			List list = Arrays.asList(ids);
			Map<String,Object> msg = resAttrService.delResAttr(list);
			String resCode = (String) msg.get("resCode");
			rtnMap.put("code", Constants.SUCCESS_CODE);
			if(resCode.equals(Constants.FAIL_CODE)){
				rtnMap.put("code", Constants.FAIL_CODE);
				rtnMap.put("info", msg.get("msg"));
				return;
			}
			rtnMap.put("info", "删除成功！");
		} catch (Exception e) {
			logger.error("ResAttrAction.delResAttr: 删除资源属性规格失败!"+e.getMessage());
			rtnMap.put("code", Constants.FAIL_CODE);
			rtnMap.put("info","删除失败!");
		}
	}

}
