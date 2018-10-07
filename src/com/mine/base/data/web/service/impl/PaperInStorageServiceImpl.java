package com.mine.base.data.web.service.impl;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mine.base.data.common.mybatis.ParentService;
import com.mine.base.data.common.util.PageMap;
import com.mine.base.data.web.service.PaperInStorageService;

@Service("paperInStorageService")
public class PaperInStorageServiceImpl  extends ParentService implements PaperInStorageService {

	/**
	 * 票据入库
	 */
	@Override
	public String  savePaperInStorage(Map<String, Object> param) {
		try {
			String orgId= param.get("deptName").toString();
			Map rstMap = this.selectOne("paperIns.QRY_COMMON_REGION_ID", orgId);

			long latnId= getLatnId(param);
			param.put("latnId", latnId);
			param.put("commonRegionId", rstMap.get("COMMON_REGION_ID").toString());
			
			
			int startCodeOBJ = Integer.valueOf(param.get("startCode").toString()).intValue();
			String endCode = param.get("endCode").toString();
			int paperSize = Integer.valueOf(param.get("paperSize").toString()).intValue();
			
			String paperCode = param.get("paperCode").toString();

			String format2="";
			for (int i = 0; i < endCode.length(); i++) {
				format2+="0";
			}
	        DecimalFormat format = new DecimalFormat(format2);
			for (int i = 0; i < paperSize; i++) {
				 param.put("invoiceNo", format.format(startCodeOBJ));
				 param.put("invoiceNbr", paperCode+format.format(startCodeOBJ));
				 startCodeOBJ=startCodeOBJ+1;
				 Long mktInvoiceId = this.selectOne( "paperIns.QRY_MKT_INVOICE_ID",param);
				 param.put("mktInvoiceId",mktInvoiceId);
				 param.put("operTypeId", 100);
				 this.insert("paperIns.savePaperInStorageDetail",param);
				 this.insert("paperIns.savePaperInStorage",param);
		    }
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}
	/**
	 * 查询当前编码是否存在如果存在 提示不保存
	 * @throws SQLException 
	 */
	@Override
	public 	List<String>  findPaperInStorage(Map<String, Object> param) throws SQLException {
		long latnId= getLatnId(param);
		String paperCode= param.get("paperCode").toString();
		int startCodeOBJ = Integer.valueOf(param.get("startCode").toString()).intValue();
		String endCode = param.get("endCode").toString();
		String typeName = param.get("typeName").toString();

		int paperSize = Integer.valueOf(param.get("paperSize").toString()).intValue();
		List<String> maps = new ArrayList<String>();
		
		String format2="";
		for (int i = 0; i < endCode.length(); i++) {
			format2+="0";
		}
        DecimalFormat format = new DecimalFormat(format2);
		for (int i = 0; i < paperSize; i++) {
			Map map = new HashMap();
			map.put("INVOICE_NO", format.format(startCodeOBJ));
			startCodeOBJ=startCodeOBJ+1;
			map.put("INVOICE_CODE", paperCode);
			map.put("latnId", latnId);
			map.put("typeName", typeName);
			int count = this.selectOne("paperIns.findPaperInStorage", map);
			if(count>0){
				maps.add(map.get("INVOICE_NO").toString());
			}
		}
		return maps; 
	}
	/**
	 * 查询本地网
	 * @param param
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public long getLatnId(Map<String, Object> param) throws SQLException{
		String orgId= param.get("deptName").toString();
		Map rstMap = this.selectOne("paperIns.QRY_COMMON_REGION_ID", orgId);
		String regionCode=null;
		if(rstMap != null){
			String regionLevel=rstMap.get("REGION_LEVEL_ID").toString();
			if("1".equals(regionLevel)){
				regionCode=rstMap.get("REGION_CODE").toString();
			}else{
				String temp=rstMap.get("REGION_CODE").toString();
				regionCode=temp.substring(0, 9);
			}
		}
		long  latnID = this.selectOne("disResResource.QRY_LATN_ID", regionCode);
		return latnID;
	}
	
	
	/**
	 * 查询票据
	 * @throws SQLException 
	 */
	@Override
	public Map<String, Object> queryPaperInStorage(Map<String,Object> param) throws SQLException {
		PageMap pageMap = new PageMap();
		pageMap.setPageSize(Integer.valueOf(param.get("pageSize").toString()));
		pageMap.setStartNum(Integer.valueOf(param.get("startNum").toString()));
		param.put("pageMap", pageMap);
//		先查询本地网
		long latnId= getLatnId(param);
		param.put("latnId", latnId);
		if(!"".equals(param.get("endCode").toString())){
			int endCode = Integer.valueOf(param.get("endCode").toString()).intValue();
			param.put("endCode", endCode);
		}
		if(!"".equals(param.get("startCode").toString())){
			int startCode = Integer.valueOf(param.get("startCode").toString()).intValue();
			param.put("startCode", startCode);
		}
		List<Map<String, Object>> list = this.selectList( "paperIns.queryPaperInStorage", param);
		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("totalRecord", pageMap.getTotalRecord());
		retMap.put("resultList", list);
		return retMap;
	}
	
	/**
	 * 修改票据
	 * @throws SQLException 
	 */
	@Override
	public void updatePaper(Map<String,Object> param) throws SQLException {
		long latnId= getLatnId(param);
		param.put("latnId", latnId);
		if(!"".equals(param.get("endCode").toString())){
			int endCode = Integer.valueOf(param.get("endCode").toString()).intValue();
			param.put("endCode", endCode);
		}
		if(!"".equals(param.get("startCode").toString())){
			int startCode = Integer.valueOf(param.get("startCode").toString()).intValue();
			param.put("startCode", startCode);
		}
		this.update("paperIns.updatePaper", param);
		param.put("operTypeId", 108);
		List<Map<String, Object>> list = this.selectList( "paperIns.queryPaperInStorage", param);
		for (int i = 0; i < list.size(); i++) {
			Map map=list.get(i);
			String MKT_INVOICE_ID=map.get("MKT_INVOICE_ID").toString();
			param.put("mktInvoiceId",Long.valueOf(MKT_INVOICE_ID));
			this.insert("paperIns.savePaperInStorageDetail",param);
		}
	}
	
	/**
	 * 票据分发
	 * @throws SQLException 
	 */
	@Override
	public void paperDistribute(Map<String, Object> param) throws SQLException {
		
		String orgId= param.get("deptName").toString();
		Map rstMap = this.selectOne("paperIns.QRY_COMMON_REGION_ID", orgId);

		long latnId= getLatnId(param);
		param.put("latnId", latnId);
		param.put("commonRegionId", rstMap.get("COMMON_REGION_ID").toString());
		if(!"".equals(param.get("endCode").toString())){
			int endCode = Integer.valueOf(param.get("endCode").toString()).intValue();
			param.put("endCode", endCode);
		}
		if(!"".equals(param.get("startCode").toString())){
			int startCode = Integer.valueOf(param.get("startCode").toString()).intValue();
			param.put("startCode", startCode);
		}
		this.update("paperIns.paperDistribute", param);
		
		List<Map<String, Object>> list = this.selectList( "paperIns.queryPaperInStorage", param);
		for (int i = 0; i < list.size(); i++) {
			Map map=list.get(i);
			String MKT_INVOICE_ID=map.get("MKT_INVOICE_ID").toString();
			param.put("mktInvoiceId",Long.valueOf(MKT_INVOICE_ID));
			param.put("operTypeId",101);
			this.insert("paperIns.savePaperInStorageDetail",param);
		}
	}
	/**
	 * 票据退库
	 * @throws SQLException 
	 */
	@Override
	public void paperGuild(Map<String, Object> param) throws SQLException {
		
		String orgId= param.get("deptName").toString();
		Map rstMap = this.selectOne("paperIns.QRY_COMMON_REGION_ID", orgId);

		long latnId= getLatnId(param);
		param.put("latnId", latnId);
		param.put("commonRegionId", rstMap.get("COMMON_REGION_ID").toString());
		if(!"".equals(param.get("endCode").toString())){
			int endCode = Integer.valueOf(param.get("endCode").toString()).intValue();
			param.put("endCode", endCode);
		}
		if(!"".equals(param.get("startCode").toString())){
			int startCode = Integer.valueOf(param.get("startCode").toString()).intValue();
			param.put("startCode", startCode);
		}
		this.update("paperIns.paperGuild", param);
		
		List<Map<String, Object>> list = this.selectList( "paperIns.queryPaperInStorage", param);
		for (int i = 0; i < list.size(); i++) {
			Map map=list.get(i);
			String MKT_INVOICE_ID=map.get("MKT_INVOICE_ID").toString();
			param.put("mktInvoiceId",Long.valueOf(MKT_INVOICE_ID));
			param.put("operTypeId",102);
			this.insert("paperIns.savePaperInStorageDetail",param);
		}
	}
	/**
	 * 查询员工
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<Map<String, Object>> queryStaff(Map<String, Object> paramMap) {
		try {
			List<Map<String,Object>> list = this.selectList( "paperIns.queryStaff", paramMap);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 票据领取
	 * @throws SQLException 
	 */
	@Override
	public void paperGets(Map<String, Object> param) throws SQLException {
		
		String orgId= param.get("deptName").toString();
		Map rstMap = this.selectOne("paperIns.QRY_COMMON_REGION_ID", orgId);

		long latnId= getLatnId(param);
		param.put("latnId", latnId);
		param.put("commonRegionId", rstMap.get("COMMON_REGION_ID").toString());
		if(!"".equals(param.get("endCode").toString())){
			int endCode = Integer.valueOf(param.get("endCode").toString()).intValue();
			param.put("endCode", endCode);
		}
		if(!"".equals(param.get("startCode").toString())){
			int startCode = Integer.valueOf(param.get("startCode").toString()).intValue();
			param.put("startCode", startCode);
		}
		this.update("paperIns.paperGets", param);
		
		List<Map<String, Object>> list = this.selectList( "paperIns.queryPaperInStorage", param);
		for (int i = 0; i < list.size(); i++) {
			Map map=list.get(i);
			String MKT_INVOICE_ID=map.get("MKT_INVOICE_ID").toString();
			param.put("mktInvoiceId",Long.valueOf(MKT_INVOICE_ID));
			param.put("operTypeId",103);
			this.insert("paperIns.savePaperInStorageDetail",param);
		}
	}
	/**
	 * 票据退回
	 * @throws SQLException 
	 */
	@Override
	public void paperReback(Map<String, Object> param) throws SQLException {
		
		String orgId= param.get("deptName").toString();
		Map rstMap = this.selectOne("paperIns.QRY_COMMON_REGION_ID", orgId);

		long latnId= getLatnId(param);
		param.put("latnId", latnId);
		param.put("commonRegionId", rstMap.get("COMMON_REGION_ID").toString());
		if(!"".equals(param.get("endCode").toString())){
			int endCode = Integer.valueOf(param.get("endCode").toString()).intValue();
			param.put("endCode", endCode);
		}
		if(!"".equals(param.get("startCode").toString())){
			int startCode = Integer.valueOf(param.get("startCode").toString()).intValue();
			param.put("startCode", startCode);
		}
		this.update("paperIns.paperReback", param);
		
		List<Map<String, Object>> list = this.selectList( "paperIns.queryPaperInStorage", param);
		for (int i = 0; i < list.size(); i++) {
			Map map=list.get(i);
			String MKT_INVOICE_ID=map.get("MKT_INVOICE_ID").toString();
			param.put("mktInvoiceId",Long.valueOf(MKT_INVOICE_ID));
			param.put("operTypeId",104);
			this.insert("paperIns.savePaperInStorageDetail",param);
		}
	}
	/**
	 * 查询当前编码是否存在如果存在 提示不保存
	 * @throws SQLException 
	 */
	@Override
	public 	List<String>  findPaperInStorageGets(Map<String, Object> param) throws SQLException {
		long latnId= getLatnId(param);
		String paperCode= param.get("paperCode").toString();
		int startCodeOBJ = Integer.valueOf(param.get("startCode").toString()).intValue();
		String endCode = param.get("endCode").toString();
		String typeName = param.get("typeName").toString();
		String paperStaff = param.get("paperStaff").toString();

		int paperSize = Integer.valueOf(param.get("paperSize").toString()).intValue();
		List<String> strings = new ArrayList<String>();
		
		String format2="";
		for (int i = 0; i < endCode.length(); i++) {
			format2+="0";
		}
        DecimalFormat format = new DecimalFormat(format2);
		for (int i = 0; i < paperSize; i++) {
			Map map = new HashMap();
			map.put("INVOICE_NO", format.format(startCodeOBJ));
			startCodeOBJ=startCodeOBJ+1;
			map.put("INVOICE_CODE", paperCode);
			map.put("latnId", latnId);
			map.put("typeName", typeName);
			map.put("paperStaff", paperStaff);
			int count = this.selectOne("paperIns.findPaperInStorageGets", map);
			if(count>0){//查询出来的大于0证明在库里面存在当前营业厅下的票据也就是可以领取
				strings.add(map.get("INVOICE_NO").toString());
			}
		}
		return strings; 
	}
	/**
	 * 查询组织机构ID
	 * @param param
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public 	Map findOrgId(String userId) throws SQLException{
		Map rstMap = this.selectOne("paperIns.findOrgId", userId);
		return rstMap;
		
	}
}
