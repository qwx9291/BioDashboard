package com.bio.sys.dao;

import com.bio.common.base.BaseDao;
import com.bio.sys.domain.RoleDO;

import java.util.List;

/**
 * <pre>
 * 角色
 * </pre>
 * <small> 2018年3月23日 | Aron</small>
 */
public interface RoleDao extends BaseDao<RoleDO> {
    List<UserRoleDao> findByRoleId(String roleId);
}
