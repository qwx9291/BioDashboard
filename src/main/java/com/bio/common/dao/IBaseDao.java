package com.bio.common.dao;

import java.io.Serializable;

public interface IBaseDao extends Serializable {
    /**
     * @return ID
     */
    public abstract String getId();

    /**
     * 设置ID
     *
     * @param id
     */
    public abstract void setId(String id);
}
