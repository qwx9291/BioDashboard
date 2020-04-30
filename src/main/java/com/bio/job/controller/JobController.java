package com.bio.job.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bio.common.base.BaseController;
import com.bio.common.utils.Result;
import com.bio.job.domain.TaskDO;
import com.bio.job.service.JobService;

/**
 * <pre>
 * 定时任务
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@Controller
@RequestMapping("/common/job")
public class JobController extends BaseController {
	
	@Autowired
	private JobService taskScheduleJobService;

	@GetMapping()
	String taskScheduleJob() {
		return "common/job/job";
	}

	@ResponseBody
	@GetMapping("/list")
	public Result<Page<TaskDO>> list(Integer pageNumber, Integer pageSize, TaskDO taskDTO) {
		// 查询列表数据
		Page<TaskDO> page = new Page<>(pageNumber, pageSize);

		Wrapper<TaskDO> wrapper = new EntityWrapper<TaskDO>(taskDTO);
		page = taskScheduleJobService.selectPage(page, wrapper);
		int total = taskScheduleJobService.selectCount(wrapper);
		page.setTotal(total);
		return Result.ok(page);
	}

	@GetMapping("/add")
	String add() {
		return "common/job/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id, Model model) {
		TaskDO job = taskScheduleJobService.selectById(id);
		model.addAttribute("job", job);
		return "common/job/edit";
	}

	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	public Result<TaskDO> info(@PathVariable("id") String id) {
		TaskDO taskScheduleJob = taskScheduleJobService.selectById(id);
		return Result.ok(taskScheduleJob);
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	public Result<String> save(TaskDO taskScheduleJob) {
		taskScheduleJobService.insert(taskScheduleJob);
		return Result.ok();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@PostMapping("/update")
	public Result<String> update(TaskDO taskScheduleJob) {
		taskScheduleJobService.updateById(taskScheduleJob);
		return Result.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/run")
	@ResponseBody
	public Result<String> run(String id) {
		try {
			taskScheduleJobService.runAtOnce(id);
			return Result.ok("任务执行成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.ok("任务执行失败");
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	public Result<String> remove(String id) {
		taskScheduleJobService.deleteById(id);
		return Result.ok();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	public Result<String> remove(@RequestParam("ids[]") Long[] ids) {
		taskScheduleJobService.deleteBatchIds(Arrays.asList(ids));

		return Result.ok();
	}

	@PostMapping(value = "/changeJobStatus")
	@ResponseBody
	public Result<String> changeJobStatus(String id, String cmd) {
		String label = "停止";
		if ("start".equals(cmd)) {
			label = "启动";
		} else {
			label = "停止";
		}
		try {
			taskScheduleJobService.changeStatus(id, cmd);
			return Result.ok("任务" + label + "成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Result.ok("任务" + label + "失败");
	}

}
