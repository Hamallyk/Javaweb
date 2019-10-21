package com.atguigu.crowd.funding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.entity.Role;
import com.atguigu.crowd.funding.entity.RoleExample;
import com.atguigu.crowd.funding.mapper.RoleMapper;
import com.atguigu.crowd.funding.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class RoleServiceImpl  implements  RoleService{
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public PageInfo<Role> queryForKeywordWithPage(Integer pageNum,
			Integer pageSize, 
			String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		  List<Role> list = roleMapper.selectForKeywordSearch(keyword);
		  
		return new PageInfo<>(list);
	}

	@Override
	public List<Role> getRoleListByIdList(List<Integer> roleIdList) {
	
		RoleExample roleExample = new RoleExample();
		
		roleExample.createCriteria().andIdIn(roleIdList);

		return roleMapper.selectByExample(roleExample);
	}

	@Override
	public void batchRemove(List<Integer> roleIdList) {
		RoleExample roleExample = new RoleExample();
		
		roleExample.createCriteria().andIdIn(roleIdList);
		
		roleMapper.deleteByExample(roleExample);
		
	}

	@Override
	public void saveRole(String roleName) {
		roleMapper.insert(new Role(null, roleName));
		
	}

	@Override
	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
		
	}
	
	

	
}
