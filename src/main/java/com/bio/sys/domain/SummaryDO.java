package com.bio.sys.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;


/**
 * 
 * <pre>
 * 日报汇总
 * </pre>
 * <small> 2019-12-18 15:03:07 | chenx</small>
 */
 @TableName("report_summary")
 @Data
public class SummaryDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** ID */
    @TableId(value = "id",type = IdType.UUID)
    private String id;
    /** 部门ID */
    private String deptId;
    /** 部门名称 */
    private String deptName;
    /** 起始时间 */
    private Date rFromDate;
    /** 终止时间 */
    private Date rToDate;
    /** 日报题目 */
    private String title;
    /** 浏览次数 */
    private Long count;
    /** 创建时间 */
    private Date rCreateDate;

}
