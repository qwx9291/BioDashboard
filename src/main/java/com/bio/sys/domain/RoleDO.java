package com.bio.sys.domain;

import java.sql.Timestamp;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

/**
 * <pre>
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
@TableName("sys_role")
@Data
public class RoleDO {
    @TableId(type = IdType.UUID)
    private String id;
    private String roleName;
    private String roleSign;
    private String roleCode;
    private String remark;
    private String userIdCreate;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    @TableField(exist = false)
    private List<String> menuIds;


    @Override
    public String toString() {
        return "RoleDO [id=" + id + ", roleName=" + roleName + ", roleSign=" + roleSign + ", roleCode = "+roleCode+"remark=" + remark
                + ", userIdCreate=" + userIdCreate + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified
                + ", menuIds=" + menuIds + "]";
    }

}
