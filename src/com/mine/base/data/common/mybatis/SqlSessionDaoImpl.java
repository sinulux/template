package com.mine.base.data.common.mybatis;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * @ClassName SqlSessionDaoImpl.java
 * @description 获取mybatis的SqlSession支持
 * @author wangshibao
 * @date 2015-09-29 15:33:56
 *
 */
public class SqlSessionDaoImpl extends SqlSessionDaoSupport{
	
	//在此添加sqlSession控制属性
	
	public SqlSessionDaoImpl(){
		this.logger.info("初始化sqlSession...");
	}
}