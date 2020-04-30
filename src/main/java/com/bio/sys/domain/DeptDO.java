package com.bio.sys.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <pre>
 * 部门管理
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@TableName("sys_dept")
@Data
public class DeptDO implements Serializable {
	
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
    private String id;
    // 上级部门ID，一级部门为0
    private String parentId;
    // 部门名称
    private String name;
    // 排序
    private Integer orderNum;
    // 是否删除 -1：已删除 0：正常
    private Integer delFlag;
    //部门编码
    private String deptCode;
    //长编码
    private String longCode;
    @Override
    public String toString() {
        return "DeptDO [id=" + id + ", parentId=" + parentId + ", name=" + name + ", orderNum=" + orderNum
                + ", delFlag=" + delFlag + "]";
    }

}
