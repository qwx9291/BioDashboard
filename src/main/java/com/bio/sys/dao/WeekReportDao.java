package com.bio.sys.dao;

import com.bio.common.base.BaseDao;
import com.bio.sys.domain.WeekReportDO;
import com.bio.sys.domain.ReportCountDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * <pre>
 * 月报
 * </pre>
 * <small> 2019-12-14 21:13:15 | chenx</small>
 */
public interface WeekReportDao extends BaseDao<WeekReportDO> {

	public  WeekReportDO getLatestReport(@Param("authorid") String authorid, @Param("rfromdate") String rToDate);

	public Integer getReportCountByTitle(String title);

	public List<WeekReportDO>getReports(@Param("fromdate") String fromDate, @Param("todate") String toDate, @Param("status") Integer status);

	 List<ReportCountDO> getReportsCount(@Param("fromdate") String fromDate, @Param("todate") String toDate, @Param("status") Integer status);
}
