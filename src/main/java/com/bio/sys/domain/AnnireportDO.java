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
 * 年报
 * </pre>
 * <small> 2019-12-31 10:19:10 | chenx</small>
 */
 @TableName("annireport")
 @Data
public class AnnireportDO implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /** ID */
    @TableId(value = "id",type = IdType.UUID)
    private String id;
    /** 作者ID */
    private String authorId;
    /** 作者姓名 */
    private String authorName;
    /** 上级部门 ID */
    private String parentDeptId;
    /** 部门ID */
    private String deptId;
    /** 部门名称 */
    private String deptName;
    /** 起始时间 */
    private Date rFromDate;
    /** 终止时间 */
    private Date rToDate;
    /** 年报题目 */
    private String title;
    /** 年报内容 */
    private String content;
    /** 状态0：系统生成，1：人工修改 */
    private Integer status;
    /** 创建时间 */
    private Date rCreateDate;
    /** 修改时间 */
    private Date rChgDate;
}
