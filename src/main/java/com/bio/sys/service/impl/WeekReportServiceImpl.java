package com.bio.sys.service.impl;

import com.bio.common.base.CoreServiceImpl;
import com.bio.common.service.ContextService;
import com.bio.common.utils.GenerateID;
import com.bio.common.utils.StringUtil;
import com.bio.oss.service.FileService;
import com.bio.sys.dao.WeekReportDao;
import com.bio.sys.domain.ReportCountDO;
import com.bio.sys.domain.UserDO;
import com.bio.sys.domain.WeekReportDO;
import com.bio.sys.service.DeptService;
import com.bio.sys.service.WeekReportService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * 日报
 * </pre>
 * <small> 2019-12-14 21:13:15 | chenx</small>
 */
@Service
public class WeekReportServiceImpl extends CoreServiceImpl<WeekReportDao, WeekReportDO> implements WeekReportService {

	@Autowired
	private DeptService deptService;

	@Autowired
	private FileService fileService;

	@Autowired
	private ContextService contextService;
	@Override
	public WeekReportDO getLatestReport(String authorid, Date rToDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return baseMapper.getLatestReport(authorid, sdf.format(rToDate));
	}

	@Override
	public Integer getReportCountByTitle(String title) {
		return baseMapper.getReportCountByTitle(title);
	}

	@Override
	public List<WeekReportDO> getReports(Date fromDate, Date toDate, Integer status) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return baseMapper.getReports(sdf.format(fromDate),  sdf.format(toDate),  status);
	}

	@Override
	public List<ReportCountDO> getReportsCount(Date fromDate, Date toDate, Integer status) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		List<ReportCountDO> reportCountDOs =  baseMapper.getReportsCount(sdf.format(fromDate),  sdf.format(toDate),  status);
//
//		if(null != reportCountDOs && !reportCountDOs.isEmpty()) {
//
//			for (ReportCountDO reportCountDO : reportCountDOs) {
//				reportCountDO.setDeptName(deptService.selectById( reportCountDO.getDeptId()).getName());
//			}
//		}
//		return reportCountDOs;
		return null;
	}

	@Override
	@Transactional
	public boolean saveReportandFile(Map<String, Object> param) {
		String id = GenerateID.getId();
		boolean flag1 = false;
		String fileUrl = param.get("fileUrl")+"";
		WeekReportDO WeekReportDO = (WeekReportDO) param.get("weekReport");
		//保存附件信息
		if(StringUtil.isNotNull(fileUrl)){
			Map<String,Object> urlMap = new HashMap<>();
			urlMap.put("url",fileUrl);
			urlMap.put("fId",id);
			fileService.saveAllFiles(urlMap);
		}
		//保存日报信息
		if(WeekReportDO!=null){
			UserDO userDO =  contextService.getCurrentLoginUser(SecurityUtils.getSubject());
			WeekReportDO.setId(id);
			WeekReportDO.setDeptId(userDO.getDeptId());
			flag1 = insert(WeekReportDO);
		}
		return flag1;
	}

	@Override
	@Transactional
	public boolean removeById(String id) {
		fileService.removeByFId(id);
		return deleteById(id);
	}

	@Override
	@Transactional
	public void removeAll(List<String> ids) {
		fileService.removeByFIds(ids);
		deleteBatchIds(ids);
	}

	@Override
	public boolean updateReportandFile(Map<String, Object> param) {
		boolean flag1 = false;
		String fileUrl = param.get("fileUrl")+"";
		WeekReportDO WeekReportDO = (WeekReportDO) param.get("report");
		//保存附件信息
		if(StringUtil.isNotNull(fileUrl)){
			Map<String,Object> urlMap = new HashMap<>();
			urlMap.put("url",fileUrl);
			urlMap.put("fId",WeekReportDO.getId());
			fileService.saveAllFiles(urlMap);
		}
		//保存日报信息
		if(WeekReportDO!=null){
			UserDO userDO =  contextService.getCurrentLoginUser(SecurityUtils.getSubject());
			WeekReportDO.setDeptId(userDO.getDeptId());
			flag1 = insert(WeekReportDO);
		}
		return flag1;
	}
}
