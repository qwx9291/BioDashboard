package com.bio.common.base;

import com.bio.common.utils.JSONUtils;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

/**
 * <pre>
 * 通用业务层实现
 * </pre>
 * 
 * <small> 2018年1月9日 | Aron</small>
 * 
 * @param <M> mapper
 * @param <T> entity
 */
public abstract class CoreServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements CoreService<T> {
    public Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean insert(T entity) {
        return super.insert(entity);
    }
}
