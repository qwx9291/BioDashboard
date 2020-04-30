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
 * 日报
 * </pre>
 * <small> 2019-12-15 10:18:15 | chenx</small>
 */
 @TableName("report")
 @Data
public class ReportDO implements Serializable {
	 
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** ID */
    @TableId(type = IdType.INPUT)
    private String id;
    /** 作者ID */
    private String authorId;
    /** 作者姓名 */
    private String authorName;
    
    /** 所属部门ID */
    private String deptId;
    /** 所属部门名称 */
    private String deptName;
    
    /** 部门所属上级部门ID */
    private String parentDeptId;
    
    /** 起始时间 */
    private Date rFromDate;
    /** 终止时间 */
    private Date rToDate;
    /** 日报题目 */
    private String title;
    /** 日报内容 */
    private String content;
    /** 评论内容 */
    private String comment;
    /** 状态0: 系统生成, 1:人工修改 */
    private Integer status;
    /** 创建时间 */
    private Date rCreateDate;
    /** 修改时间 */
    private Date rChgDate;
    /**评论时间*/
    private Date commentDate;
    /**评论者*/
    private String commentatorId;
}
