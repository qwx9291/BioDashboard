package com.bio.sys.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bio.common.annotation.Log;
import com.bio.common.base.BaseController;
import com.bio.common.domain.Tree;
import com.bio.common.utils.Result;
import com.bio.sys.domain.MenuDO;
import com.bio.sys.service.MenuService;

/**
 * <pre>
 * </pre>
 * 
 * <small> 2018年3月23日 | Aron</small>
 */
@RequestMapping("/sys/menu")
@Controller
public class MenuController extends BaseController {
	
    String prefix = "sys/menu";
    
    @Autowired
    MenuService menuService;

    @RequiresPermissions("sys:menu:menu")
    @GetMapping()
    String menu(Model model) {
        return prefix + "/menu";
    }

    @RequiresPermissions("sys:menu:menu")
    @RequestMapping("/list")
    @ResponseBody
    List<MenuDO> list() {
        List<MenuDO> menus = menuService.selectList(null);
        return menus;
    }

    @Log("添加菜单")
    @RequiresPermissions("sys:menu:add")
    @GetMapping("/add/{pId}")
    String add(Model model, @PathVariable("pId") String pId) {
        model.addAttribute("pId", pId);
        if ("0".equals(pId)) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.selectById(pId).getName());
        }
        return prefix + "/add";
    }

    @Log("编辑菜单")
    @RequiresPermissions("sys:menu:edit")
    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable("id") String id) {
        MenuDO mdo = menuService.selectById(id);
        String pId = mdo.getParentId();
        model.addAttribute("pId", pId);
        if ("0".equals(pId)) {
            model.addAttribute("pName", "根目录");
        } else {
            model.addAttribute("pName", menuService.selectById(pId).getName());
        }
        model.addAttribute("menu", mdo);
        return prefix + "/edit";
    }

    @Log("保存菜单")
    @RequiresPermissions("sys:menu:add")
    @PostMapping("/save")
    @ResponseBody
    Result<String> save(MenuDO menu) {
        menuService.insert(menu);
        return Result.ok();
    }

    @Log("更新菜单")
    @RequiresPermissions("sys:menu:edit")
    @PostMapping("/update")
    @ResponseBody
    Result<String> update(MenuDO menu) {
        menuService.updateById(menu);
        return Result.ok();
    }

    @Log("删除菜单")
    @RequiresPermissions("sys:menu:remove")
    @PostMapping("/remove")
    @ResponseBody
    Result<String> remove(String id) {
        menuService.deleteById(id);
        return Result.ok();
    }

    @GetMapping("/tree")
    @ResponseBody
    Tree<MenuDO> tree() {
        Tree<MenuDO> tree = new Tree<MenuDO>();
        tree = menuService.getTree();
        return tree;
    }

    @GetMapping("/tree/{roleId}")
    @ResponseBody
    Tree<MenuDO> tree(@PathVariable("roleId") String roleId) {
        Tree<MenuDO> tree = new Tree<MenuDO>();
        tree = menuService.getTree(roleId);
        return tree;
    }
}
