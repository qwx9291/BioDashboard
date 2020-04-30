package com.bio.sys.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@TableName("sys_menu")
@Data
public class MenuDO implements Serializable {
	
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.UUID)
    private String id;
    // 父菜单ID，一级菜单为0
    private String parentId;
    // 菜单名称
    private String name;
    // 菜单URL
    private String url;
    // 授权(多个用逗号分隔，如：user:list,user:create)
    private String perms;
    // 类型 0：目录 1：菜单 2：按钮
    private Integer type;
    // 菜单图标
    private String icon;
    // 排序
    private Integer orderNum;
    // 创建时间
    private Date gmtCreate;
    // 修改时间
    private Date gmtModified;


    @Override
    public String toString() {
        return "MenuDO [id=" + id + ", parentId=" + parentId + ", name=" + name + ", url=" + url + ", perms=" + perms
                + ", type=" + type + ", icon=" + icon + ", orderNum=" + orderNum + ", gmtCreate=" + gmtCreate
                + ", gmtModified=" + gmtModified + "]";
    }

}
