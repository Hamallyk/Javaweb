package com.atguigu.crowd.funding.text;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crowd.funding.entity.Admin;
import com.atguigu.crowd.funding.mapper.AdminMapper;
import com.atguigu.crowd.funding.service.api.AdminService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdFundingTest {
	@Autowired
	private DataSource dateSource;
	@Autowired
	private AdminService adminService;
	@Autowired
	private AdminMapper adminMapper;
	@Test
	public void batchSaveAdmin() {
		for(int i = 0; i < 500; i++) {
			adminMapper.insert(new Admin(null, "loginAccet"+i, "1111111"+i, "userName"+i, "email"+i, null));
		}
	}
	@Test
	public void testAdminMapperSearch (){
		String keyword="p";
		List<Admin> list = adminMapper.selectAdminListByKeyword(keyword);
		for (Admin admin : list) {
			System.out.println(admin);
		}
	}
	
	
	@Test
	public void testMybatis() {
		List<Admin> adminList=adminService.getAll();
		for (Admin admin : adminList) {
			System.out.println(admin);
		}
	}
	@Test
	public void testConnetion() throws SQLException {
		java.sql.Connection connection = dateSource.getConnection();
		System.out.println(connection);
	}
	@Test
	public void addAdmin() {
		for(int i = 0; i < 20; i++) {
			adminMapper.insert(new Admin(null, "AAA", "1111111"+i, "userName"+i, "email"+i, null));
			adminMapper.insert(new Admin(null, "BBB", "1111111"+i, "userName"+i, "email"+i, null));
		}
	}
	
}
