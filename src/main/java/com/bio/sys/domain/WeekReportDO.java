package com.bio.sys.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <pre>
 * 月报
 * </pre>
 * <small> 2019-12-15 10:18:15 | chenx</small>
 */
 @TableName("week_report")
 @Data
public class WeekReportDO implements Serializable {
	 
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
    /** 月报题目 */
    private String title;
    /** 月报内容 */
    private String content;
    /** 下月计划 */
    private String nextContent;

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
