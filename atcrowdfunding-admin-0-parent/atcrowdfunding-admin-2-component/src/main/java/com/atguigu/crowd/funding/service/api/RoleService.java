package com.atguigu.crowd.funding.service.api;

import java.util.List;

import com.atguigu.crowd.funding.entity.Role;
import com.github.pagehelper.PageInfo;

public interface RoleService {
	public PageInfo<Role> queryForKeywordWithPage(Integer pageNum, Integer pageSize, String keyword);

	public List<Role> getRoleListByIdList(List<Integer> roleIdList);

	public void batchRemove(List<Integer> roleIdList);

	public void saveRole(String roleName);

	public void updateRole(Role role);
}
