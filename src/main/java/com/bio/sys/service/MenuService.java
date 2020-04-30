package com.bio.sys.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.IService;
import com.bio.common.domain.Tree;
import com.bio.sys.domain.MenuDO;

/**
 * 
 * 
 * @author chenx
 *
 */
@Service
public interface MenuService extends IService<MenuDO> {
	
    Tree<MenuDO> getSysMenuTree(String id);

    List<Tree<MenuDO>> listMenuTree(String id);

    Tree<MenuDO> getTree();

    Tree<MenuDO> getTree(String id);

    Set<String> listPerms(String userId);

    void initMenu();

}
