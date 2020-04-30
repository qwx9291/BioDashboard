package com.bio.common.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;


/**
 * 
 * 
 * @author Aron
 * @email izenglong@163.com
 * @date 2018-04-06 01:05:22
 */
 @TableName("sys_config")
public class ConfigDO implements Serializable {
	 
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    
    /**  */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**  */
    private String k;
    /**  */
    private String v;
    /**  */
    private String remark;
    /**  */
    private Date createTime;

    /**
     * 设置：
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 获取：
     */
    public String getId() {
        return id;
    }
    /**
     * 设置：
     */
    public void setK(String k) {
        this.k = k;
    }
    /**
     * 获取：
     */
    public String getK() {
        return k;
    }
    /**
     * 设置：
     */
    public void setV(String v) {
        this.v = v;
    }
    /**
     * 获取：
     */
    public String getV() {
        return v;
    }
    /**
     * 设置：
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * 获取：
     */
    public String getRemark() {
        return remark;
    }
    /**
     * 设置：
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * 获取：
     */
    public Date getCreateTime() {
        return createTime;
    }
}
