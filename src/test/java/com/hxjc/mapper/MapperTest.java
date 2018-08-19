package com.hxjc.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hxjc.bdss.ss.dao.DepartmentDao;
import com.hxjc.bdss.ss.dao.EmployeeDao;
import com.hxjc.bdss.ss.model.Employee;

/**
 * 测试dao层的工作
 * @author 13116
 * 推荐Spring的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 *1、导入SpringTest
 *2、@ContextConfiguration指定spring配置文件的位置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class MapperTest {
	@Autowired
	EmployeeDao employeeDao;
	@Autowired
	DepartmentDao departmentDao;
	@Autowired
	SqlSession sqlSession;
	@Test
	public void testCRUD(){
		/*
		Department department = new Department(null, "测试部");
		departmentDao.insertSelective(department);
		*/
		//2.批量插入多个员工；批量，使用可以执行批量操作的sqlSession
		EmployeeDao mapper = sqlSession.getMapper(EmployeeDao.class);
		for (int i = 0; i < 1000; i++) {
			String uid = UUID.randomUUID().toString().substring(0, 5)+i;
			Employee employee = new Employee(null, uid, "M",uid+ "@qq.com", 1);
			employeeDao.insertSelective(employee);
			
		}
		System.out.println("批量完成");
		
	}
	@Test
	public void testDelete(){
		//Map<String, Object> map = new HashMap<>();
		//map.put("status", );
		List<Integer> list = new ArrayList<>();
		list.add(9);
		list.add(10);
		employeeDao.deleteByList(list);
		System.out.println("----");
	}
}
