package com.bio.job.jobs;

import com.bio.common.domain.MailBean;
import com.bio.common.utils.DateUtils;
import com.bio.common.utils.ThreadPool;
import com.bio.sys.domain.ReportDO;
import com.bio.sys.domain.SummaryDO;
import com.bio.sys.domain.UserDO;
import com.bio.sys.service.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 每周一早七点执行
 * 
 * @author chenx
 *
 */
@Component
public class ThisWeekReportTemplateGenerateJob implements Job {

    private Logger logger = LoggerFactory.getLogger(MailService.class);

    @Value("${bio.projectRootURL}")
    private String url;
    
	@Autowired
	private UserService userService;

	@Autowired
	private DeptService deptService;

	@Autowired
	private RoleService userRoleService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private SummaryService summaryService;
	
	@Autowired
	private MailService mailService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("status", "1"); // 正常用户

		List<UserDO> users = userService.selectByMap(columnMap);

		List<ReportDO> reportDOs = new ArrayList<ReportDO>();
		List<SummaryDO> summaryDOs = new ArrayList<SummaryDO>();

		// Step 0，准备数据
		for (UserDO userDO : users) {
			String roleDOs = userRoleService.findRoleIdByUserId(userDO.getId());

			if (null != roleDOs && roleDOs.equals(3)) { // 学生

				ReportDO reportDO = new ReportDO();

				reportDO.setAuthorId(userDO.getId());
				reportDO.setStatus(0); // 标识 Bot 生成的

				reportDO.setAuthorName(userDO.getName());
				reportDO.setContent("暂无");

				Date mon = DateUtils.getThisWeekMondayStart(new Date());
				Date sun = DateUtils.getThisWeekSundayEnd(new Date());
				String deptName = deptService.selectById(userDO.getDeptId()).getName();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String title = sdf.format(mon) + "-" + sdf.format(sun) + "-" + deptName + "-" + userDO.getName()
						+ " 日报";

				if (reportService.getReportCountByTitle(title) > 0) {
					continue;
				}

				reportDO.setRFromDate(mon);
				reportDO.setRToDate(sun);
				reportDO.setRCreateDate(new Date());
				reportDO.setDeptId(userDO.getDeptId());

				reportDO.setDeptName(deptName);
				String deptId = deptService.getParentDept(userDO.getDeptId()).getId();
				reportDO.setParentDeptId(deptId);

				reportDO.setTitle(title);

				reportDOs.add(reportDO);
			}

			if (null != roleDOs && roleDOs.equals(2)) { // PI
				SummaryDO summaryDO = new SummaryDO();

				Date mon = DateUtils.getThisWeekMondayStart(new Date());
				Date sun = DateUtils.getThisWeekSundayEnd(new Date());
				summaryDO.setRFromDate(mon);
				summaryDO.setRToDate(sun);
				summaryDO.setRCreateDate(new Date());

				summaryDO.setDeptId(userDO.getDeptId());
				String deptName = deptService.selectById(userDO.getDeptId()).getName();
				summaryDO.setDeptName(deptName);
				summaryDO.setCount(0L);

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				String title = sdf.format(mon) + "-" + sdf.format(sun) + "-" + deptName + " 日报";

				if (summaryService.getSummaryCountByTitle(title) > 0) {
					continue;
				}
				summaryDO.setTitle(title);

				summaryDOs.add(summaryDO);
			}
		}
		// Step 1, 批量插入日报
		if (reportDOs.size() > 0) {
			boolean b = reportService.insertBatch(reportDOs);
			if (b) {
				for (ReportDO reportDO : reportDOs) {
					ThreadPool.getThreadPool().addRunnable(new ThreadPool.Runnable() {
						@Override
						public void run() {
							try {
								MailBean mailBean = new MailBean();

								String recipient=  userService.selectById(reportDO.getAuthorId()).getEmail();
								mailBean.setSubject("【BioDashboard】本周的日报草稿已经为您生成！");
								mailBean.setRecipient(recipient);

								Map<String, Object> parameters = new HashMap<>();
								parameters.put("name", reportDO.getAuthorName());
								parameters.put("url", url);

								mailService.sendTemplateMail(mailBean, "newreport.html", parameters);
								
							} catch (Exception e) {
								e.printStackTrace();
					            logger.error("邮件发送失败", e.getMessage());
							}
						}
					});
				}
			}

			// Step 2,批量插入日报Summary
			if (summaryDOs.size() > 0) {
				summaryService.insertBatch(summaryDOs);
			}

			System.out.println("生成日报任务及日报汇总任务执行 | " + DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN_19));
		}
	}
}
