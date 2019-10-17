package com.atguigu.crowd.funding.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.entity.AdminExample;
import com.atguigu.crowd.funding.entity.AdminExample.Criteria;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;
import com.atguigu.crowd.funding.util.CrowdFundingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminMapper adminMapper;

	public List<Admin> getAll() {
		return adminMapper.selectByExample(new AdminExample());
	}
	
	@Override
	public Admin login(String loginAcct, String userPswd) {

		// ����loginAcct��ѯ���ݿ�
		AdminExample adminExample = new AdminExample();
		adminExample.createCriteria().andLoginAccetEqualTo(loginAcct);
	
		
		// ִ�в�ѯ
		List<Admin> list = adminMapper.selectByExample(adminExample);
		
		if(!CrowdFundingUtils.collectionEffective(list)) {
			System.out.println("ddddd");
			// �����ѯ���������Ч����ֱ�ӷ���null;
			return null;
		}
	
		// ��ȡΨһ����Ԫ��
		Admin admin = list.get(0);
		
		// ȷ��admin��Ϊnull
		if(admin == null) {
			return null;
		}

		// �Ƚ�����
		String userPswdDataBase = admin.getUserPswd();
		String userPswdBroswer = CrowdFundingUtils.md5(userPswd);
		if(Objects.equals(userPswdBroswer, userPswdDataBase)) {
			// ����������������ô˵����¼�ܹ��ɹ�������Admin����
			return admin;
		}
		
		return null;
	}
	@Override
	public PageInfo<Admin> queryForKeywordSearch(Integer pageNum,Integer pageSize ,String keyword) {
		PageHelper.startPage(pageNum, pageSize);
		List<Admin> list = adminMapper.selectAdminListByKeyword(keyword);
		
		return new PageInfo<>(list);
	}
	@Override
	public void batchRemove(List<Integer> adminIdList) {

		
			// QBC��Query By Criteria
			
			// ����AdminExample���󣨲�Ҫ��Example������ʲô��˼����û����˼��
			AdminExample adminExample = new AdminExample();
			
			// ����Criteria���󣨲�Ҫ��Criteria������ʲô��˼����û����˼��
			// Criteria������԰������Ƿ�װ��ѯ����
			// ͨ��ʹ��Criteria���󣬿��԰�Java����ת����SQL�����WHERE�Ӿ�����ľ����ѯ����
			Criteria criteria = adminExample.createCriteria();
			
			// ���Ҫ��ѯ���ֶη�װ����Ĳ�ѯ����
			criteria.andIdIn(adminIdList);
			
			// ִ�о������ʱ�ѷ�װ�˲�ѯ������Example������
			adminMapper.deleteByExample(adminExample);

	}
	@Override
	public void saveAdmin(Admin admin) {
		
		// ��������м���
		String userPswd = admin.getUserPswd();
		userPswd = CrowdFundingUtils.md5(userPswd);
		admin.setUserPswd(userPswd);
		
		// ִ�б���
		adminMapper.insert(admin);
		
	}
	@Override
	public Admin getAdminById(Integer adminId) {
		return adminMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public void updateAdmin(Admin admin) {
		String userPswd = admin.getUserPswd();
		userPswd = CrowdFundingUtils.md5(userPswd);
		admin.setUserPswd(userPswd);
		adminMapper.updateByPrimaryKey(admin);
		
	}
	

	

}

