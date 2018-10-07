package com.mine.base.data.common.base;

import org.apache.log4j.Logger;

import com.mine.base.data.common.mybatis.ParentService;

/**
 * @ClassName BaseService.java
 * @description 业务层直接继承调用服务入口
 * @author wangshibao
 * @date 2016-03-22 10:33:37
 */
public class BaseService  extends ParentService{
	//可以在此添加业务层公共属性和方法
	
	public Logger logger = Logger.getLogger(getClass());
}
