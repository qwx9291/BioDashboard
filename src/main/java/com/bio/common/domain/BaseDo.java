package com.bio.common.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.bio.common.dao.IBaseDao;

import java.util.UUID;

public abstract class BaseDo implements IBaseDao {
    /** ID */
    @TableId(value = "id",type = IdType.INPUT)
    protected String id;

    public String getId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
