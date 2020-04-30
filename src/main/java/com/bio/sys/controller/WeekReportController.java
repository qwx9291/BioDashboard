package com.bio.sys.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.bio.common.service.ContextService;
import com.bio.common.utils.DateUtils;
import com.bio.common.utils.FileUtil;
import com.bio.common.utils.Result;
import com.bio.common.utils.StringUtil;
import com.bio.oss.domain.FileDO;
import com.bio.oss.service.FileService;
import com.bio.sys.domain.DeptDO;
import com.bio.sys.domain.WeekReportDO;
import com.bio.sys.domain.RoleDO;
import com.bio.sys.domain.UserDO;
import com.bio.sys.service.DeptService;
import com.bio.sys.service.WeekReportService;
import com.bio.sys.service.RoleService;
import com.bio.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

/**
 * <pre>
 * 日报
 * </pre>
 * <small> 2019-12-14 21:13:15 | chenx</small>
 */
@Controller
@RequestMapping("/bio/weekReport")
public class WeekReportController {
    Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
    @Autowired
    private WeekReportService WeekReportService;

    @Autowired
    private ContextService contextService;


    @Autowired
    private FileService fileService;

    @Autowired
    public RoleService roleService;
    @Autowired
    public UserService userService;

    @Autowired
    private DeptService deptService;

    @GetMapping()
    @RequiresPermissions("bio:weekReport:weekReport")
    String WeekReport(Model model) {
        UserDO userDO = contextService.getCurrentLoginUser(SecurityUtils.getSubject());
        model.addAttribute("currentUserId", userDO.getId());
        RoleDO roleDO = roleService.selectById(userDO.getRoleId());
        //如果是管理员角色
        if ("admin".equals(roleDO.getRoleCode())) {
            //保存当前用户ID至前台页面做权限控制（暂时）
            model.addAttribute("currentUserId", roleDO.getRoleCode());
        }
        return "bio/weekReport/weekReport";
    }

//    @ResponseBody
//    @GetMapping("/latest/{id}")
////	@RequiresPermissions("bio:WeekReport:latest")
//    public Result<WeekReportDO> getLatestReport(@PathVariable("id") Integer id) {
//
//        UserDO userDO = contextService.getCurrentLoginUser(SecurityUtils.getSubject());
//
//        WeekReportDO report = WeekReportService.selectById(id);
//        WeekReportDO WeekReportDO = null;
//        if (null != report) {
//            WeekReportDO = WeekReportService.getLatestReport(userDO.getId(), report.getRFromDate());
//        }
//        if (null != WeekReportDO) {
//            return Result.ok(WeekReportDO);
//        }
//        return Result.fail();
//    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("bio:weekReport:weekReport")
    public Result<Page<WeekReportDO>> list(Integer pageNumber, Integer pageSize, HttpServletRequest request) throws Exception {

        UserDO userDO = contextService.getCurrentLoginUser(SecurityUtils.getSubject());
        //保存当前用户ID至前台页面做权限控制（暂时）
//        model.addAttribute("currentUserId",userDO.getId());

        String authorName = request.getParameter("authorName");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // 查询列表数据
        Page<WeekReportDO> page = new Page<>(pageNumber, pageSize);
        Wrapper<WeekReportDO> wrapper = new EntityWrapper<WeekReportDO>();

        RoleDO roleDO = roleService.selectById(userDO.getRoleId());
        wrapper.like("author_name", authorName);

        if (StringUtil.isNotNull(startDate) && StringUtil.isNotNull(endDate)) {
            Date start = DateUtils.formatStringToDate(startDate);
            Date end = DateUtils.formatStringToDate(endDate);
            wrapper.between("r_create_date", start, end);
        }
        //如果是管理员角色
        if ("admin".equals(roleDO.getRoleCode())) {
        } else
            //如果是经理级别以上
        if ("bmjl".equals(roleDO.getRoleCode()) || "bmzj".equals(roleDO.getRoleCode())) {
            wrapper.in("author_id", getIDsByChirldDept(userDO));
        } else
            //如果是普通员工
            if ("ptyg".equals(roleDO.getRoleCode())) {
                wrapper.eq("author_id", userDO.getId());
            }
        wrapper.orderBy("r_create_date", false);
        page = WeekReportService.selectPage(page, wrapper);
        int total = WeekReportService.selectCount(wrapper);
        page.setTotal(total);
        return Result.ok(page);
    }

    /**
     * 获取当前人员所在部门下所有子部门的人员ID(待优化)
     *
     * @return
     */
    private List<String> getIDsByChirldDept(UserDO userDO) {
        String deptId = userDO.getDeptId();
        DeptDO dept = deptService.selectById(deptId);
        List<DeptDO> depts = deptService.selectDeptByDeptCode(dept.getDeptCode());
        List<String> deptIds = new ArrayList<>();
        for (DeptDO deptDO : depts) {
            deptIds.add(deptDO.getId());
        }
        List<String> userIds = userService.selectListByDeptIds(deptIds);
        return userIds;
    }

    @GetMapping("/add")
    @RequiresPermissions("bio:weekReport:add")
    String add(Model model) {
        UserDO userDO = contextService.getCurrentLoginUser(SecurityUtils.getSubject());
        model.addAttribute("user", userDO);
        return "bio/weekReport/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("bio:weekReport:edit")
    String edit(@PathVariable("id") String id, Model model) {
        WeekReportDO report = WeekReportService.selectById(id);
        model.addAttribute("createDate", DateUtils.format(report.getRCreateDate()));
        model.addAttribute("weekReport", report);
//		model.addAttribute("dynamicPlaceholder", "default");
//		PlaceholderDO placeholderDO = placeholderService.findRandomPlaceholder();
//		if(null != placeholderDO) {
//			model.addAttribute("dynamicPlaceholder", placeholderDO.getContent() +" (来源:" + placeholderDO.getContributor() + "，欢迎在 意见反馈 投稿!)" );
//		}
        return "bio/weekReport/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("bio:weekReport:add")
    @Transactional
    public Result<String> save(HttpServletRequest request, WeekReportDO report) {
        String fileUrl = request.getParameter("fileHiddenUrl");
        Map<String, Object> param = new HashMap<>();
        param.put("fileUrl", fileUrl);
        param.put("weekReport", report);

        if (WeekReportService.saveReportandFile(param)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @ResponseBody
    @PostMapping("/viewFile")
    public List<Map<String, String>> viewFile(HttpServletRequest request, String rId) throws JSONException {
        Map<String, String> reMap = new HashMap<>();
        List<Map<String, String>> reList = new ArrayList<>();
        List<FileDO> files = fileService.getFilesByFId(rId);
        if (files.size() > 0) {
            for (FileDO file : files) {
                reMap.put("fileUrl", file.getUrl());
                reMap.put("fileName", file.getfName());
                reList.add(reMap);
            }
        }
        return reList;
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/upload")
    public Map<String, Object> upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("fileInput") MultipartFile[] files) {
        resultMap.put("status", 400);
        if (files == null || !(files.length > 0)) {
            resultMap.put("status", 500);
            resultMap.put("message", "没有检测到有效文件！");
            return resultMap;
        }
        //组合image名称，“;隔开”
        List<String> fileUrl = new ArrayList<String>();
        PrintWriter out = null;
        try {
            for (int i = 0; i < files.length; i++) {
                if (!files[i].isEmpty()) {
                    //上传文件，随机名称，";"分号隔开
                    fileUrl.add(FileUtil.uploadFile(request, "/upload/" + FileUtil.formateString(new Date()) + "/", files[i], true));
                }
            }
            //上传成功
            if (fileUrl != null && fileUrl.size() > 0) {
                System.out.println("上传成功！");
                resultMap.put("status", 200);
                resultMap.put("message", "上传成功！");
                resultMap.put("url", fileUrl);
            } else {
                resultMap.put("status", 500);
                resultMap.put("message", "上传失败！文件格式错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", 500);
            resultMap.put("message", "上传异常！");
        }
        return resultMap;
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("bio:weekReport:edit")
    public Result<String> update(WeekReportDO report) {

        //判断如果有修改，则修改最后时间和 Status
        report.setRChgDate(new Date());
        report.setStatus(1);

        WeekReportService.updateById(report);
        return Result.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("bio:weekReport:remove")
    public Result<String> remove(String id) {
        if (WeekReportService.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("bio:weekReport:batchRemove")
    public Result<String> remove(@RequestParam("ids[]") String[] ids) {
        WeekReportService.removeAll(Arrays.asList(ids));
        return Result.ok();
    }

    /**
     * 查看
     */
    @GetMapping("/review/{id}")
    public String review(@PathVariable("id") String id, Model model) {
        UserDO userDO = contextService.getCurrentLoginUser(SecurityUtils.getSubject());
        WeekReportDO report = WeekReportService.selectById(id);
        String comId = report.getCommentatorId();
        String comName = userDO.getName();
        if (StringUtil.isNotNull(comId)) {
            comName = userService.selectById(comId).getName();
        } else {
            comId = userDO.getId();
        }
        RoleDO roleDO = roleService.selectById(userDO.getRoleId());
        //如果是经理级别以上
        if ("bmjl".equals(roleDO.getRoleCode()) || "bmzj".equals(roleDO.getRoleCode())||"admin".equals(roleDO.getRoleCode())) {
            model.addAttribute("hidden",false);
        }else{
            model.addAttribute("hidden",true);
        }
        //值存入modelui
        model.addAttribute("weekReport", report);
        model.addAttribute("createDate", DateUtils.format(report.getRCreateDate()));
        model.addAttribute("comDate", DateUtils.format(new Date()));
        model.addAttribute("comName", comName);
        model.addAttribute("comId", comId);
        return "bio/weekReport/review";
    }


}
