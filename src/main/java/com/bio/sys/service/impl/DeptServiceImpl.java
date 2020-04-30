package com.bio.sys.service.impl;

import com.bio.common.base.CoreServiceImpl;
import com.bio.common.domain.Tree;
import com.bio.common.utils.BuildTree;
import com.bio.common.utils.StringUtil;
import com.bio.sys.dao.DeptDao;
import com.bio.sys.domain.DeptDO;
import com.bio.sys.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 部门管理 Service
 * 
 * @author chenx
 *
 */
@Service
public class DeptServiceImpl extends CoreServiceImpl<DeptDao, DeptDO> implements DeptService {

	@Override
	public Tree<DeptDO> getTree(String deptId) {

		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> sysDepts = new ArrayList<DeptDO>();
		if ("0".equals(deptId)) { // 根 dept
			sysDepts = baseMapper.selectList(null);
		} else {
			sysDepts.add(baseMapper.selectById(deptId));
			sysDepts.addAll(getSubDepts(deptId));
		}
		if (!sysDepts.isEmpty()) {
			for (DeptDO sysDept : sysDepts) {
				Tree<DeptDO> tree = new Tree<DeptDO>();
				tree.setId(sysDept.getId().toString());
				tree.setParentId(sysDept.getParentId().toString());
				tree.setText(sysDept.getName());
				Map<String, Object> state = new HashMap<>(16);
				state.put("opened", true);
				tree.setState(state);
				trees.add(tree);
			}
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public boolean checkDeptHasUser(String deptId) {
		int result = baseMapper.getDeptUserNumber(deptId);
		return result == 0;
	}

	@Override
	public DeptDO getParentDept(String deptId) {
		DeptDO deptDO = baseMapper.getParentDept(deptId);
		return deptDO;
	}

	@Override
	public List<DeptDO> getSubDepts(String deptId) {
		return baseMapper.getSubDepts(deptId);
	}

	@Override
	public void updateDept(Map<String, Object> param) {
		DeptDO dept = (DeptDO) param.get("deptDO");
		if(dept==null){
			return;
		}
		insertLongCode(dept);
		this.updateById(dept);
	}

	@Override
	public boolean saveDept(Map<String, Object> param) {
		DeptDO dept = (DeptDO) param.get("deptDO");
		if(dept==null){
			return false;
		}
		insertLongCode(dept);
		return this.insert(dept);
	}

	@Override
	public List<DeptDO> selectDeptByDeptCode(String deptCode) {
		if(StringUtil.isNotNull(deptCode)){
			deptCode ="%"+deptCode+"%";
		}else {
			deptCode = null;
		}
		return baseMapper.selectDeptByCode(deptCode);
	}

	/**
	 *  插入部门长编码
	 * @param dept
	 */
	private void insertLongCode(DeptDO dept){
		String parentId = dept.getParentId();
		if(StringUtil.isNotNull(parentId)&&!("0".equals(parentId))){
			DeptDO parentDept = selectById(parentId);
			dept.setLongCode(parentDept.getLongCode()+"_"+dept.getDeptCode());
		}else {
			dept.setLongCode(dept.getDeptCode());
		}
	}
}
