package com.mine.base.data.common.util;

import java.io.Serializable;

/**
 * 
* @author wangshibao   
* @date 20150627
* @Description:获取分页对象
* @version V1.0
 */
public abstract class PageRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PageMap pageMap;

	/**
	 * @return the pageMap
	 */
	public PageMap getPageMap() {
		return pageMap;
	}

	/**
	 * @param pageMap the pageMap to set
	 */
	public void setPageMap(PageMap pageMap) {
		this.pageMap = pageMap;
	}
}