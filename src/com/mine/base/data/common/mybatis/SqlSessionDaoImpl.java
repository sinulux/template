package com.mine.base.data.common.mybatis;

import org.mybatis.spring.support.SqlSessionDaoSupport;

/**
 * @ClassName SqlSessionDaoImpl.java
 * @description ��ȡmybatis��SqlSession֧��
 * @author wangshibao
 * @date 2015-09-29 15:33:56
 *
 */
public class SqlSessionDaoImpl extends SqlSessionDaoSupport{
	
	//�ڴ����sqlSession��������
	
	public SqlSessionDaoImpl(){
		this.logger.info("��ʼ��sqlSession...");
	}
}