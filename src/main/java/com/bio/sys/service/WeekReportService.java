package com.bio.sys.service;

import com.bio.common.base.CoreService;
import com.bio.sys.domain.WeekReportDO;
import com.bio.sys.domain.ReportCountDO;
import com.bio.sys.domain.WeekReportDO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * 日报
 * </pre>
 * <small> 2019-12-14 21:13:15 | chenx</small>
 */
public interface WeekReportService extends CoreService<WeekReportDO> {

	public  WeekReportDO getLatestReport(String id, Date date);

	public  Integer getReportCountByTitle(String title);

	public List<WeekReportDO> getReports(Date fromDate, Date toDate, Integer status);

	public List<ReportCountDO> getReportsCount(Date fromDate, Date toDate, Integer status);

	/**
	 *  保存日报表单和附件
	 * @param param
	 */
	public boolean saveReportandFile(Map<String, Object> param);

	public boolean removeById(String id);

	public void removeAll(List<String> ids);

	public boolean updateReportandFile(Map<String, Object> param);
}
