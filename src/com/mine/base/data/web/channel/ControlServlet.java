package com.mine.base.data.web.channel;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSONArray;

/**
 * 利用servlet3.0特性构造控制器总控
 * @ClassName ControlServlet.java
 * @author wangshibao
 * @date 2015年7月9日 下午12:39:38
 * @Description: 所有Servlet类的入口
 * @version V1.0
 * @test-date 2015-07-12 11:35:33
 * @test-status success
 * 
 * @description 此控制器渠道需要在tomcat7，weblogic9.0以上运行，其他服务器未验证
 */
@WebServlet("/controlServlet.shtml")
@MultipartConfig
public class ControlServlet extends HttpServlet {
	private static final long serialVersionUID = -4714985293186408724L;
	Logger logger = Logger.getLogger(getClass());

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if (logger.isDebugEnabled()) {
			logger.debug("enter method:ControlServlet.doPost");
		}
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "no-cache");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		String serviceName = request.getParameter("serviceName");
		if (StringUtils.isEmpty(serviceName)) {
			logger.error("Parameter[serviceName] is empty, please check it!");
			return;
		}
		String methodName = request.getParameter("methodName");
		if (StringUtils.isEmpty(methodName)) {
			logger.error("Parameter[methodName] is empty, please check it!");
			return;
		}
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		BaseAction serviceInstance = null;
		try {
			serviceInstance = (BaseAction) context.getBean(serviceName);
		} catch (Exception e) {
			logger.error("bean " + serviceName + " not exists!!!", e);
			return;
		}
		Class<? extends BaseAction> clazz = serviceInstance.getClass();
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class, Map.class);
		} catch (NoSuchMethodException e) {
			logger.error("bean=" + serviceName + " not exists method " + methodName, e);
			return;
		} catch (SecurityException e) {
			logger.error("bean=" + serviceName + " not exists public method " + methodName, e);
			return;
		}
		try {
			method.invoke(serviceInstance, request, response, rtnMap);
		} catch (IllegalAccessException e) {
			logger.error("get error in [bean=" + serviceName + ",method=" + methodName + "]", e);
		} catch (IllegalArgumentException e) {
			logger.error("get error in [bean=" + serviceName + ",method=" + methodName + "]", e);
		} catch (InvocationTargetException e) {
			logger.error("get error in [bean=" + serviceName + ",method=" + methodName + "]", e);
		}
//		}catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
//			logger.error("get error in [bean=" + serviceName + ",method=" + methodName + "]", e);
//		}
		Object o = rtnMap.get("showCode");
		boolean isShowCode = o==null?false:true;
		if(!isShowCode){ //验证码请求不需要返回json
			PrintWriter out = response.getWriter();
			out.println(JSONArray.toJSONString(rtnMap));
			out.flush();
			out.close();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("leave method:ControlServlet.doPost");
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doPost(request,response);
	}
}