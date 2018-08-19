package com.hxjc.bdss.ss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxjc.bdss.ss.dao.EmployeeDao;
import com.hxjc.bdss.ss.model.Employee;
import com.hxjc.bdss.ss.service.EmployeeService;
import com.hxjc.bdss.ss.vo.EmployeeVo;
@Service 
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeDao employeeDao;

	/*  
	 * 查询所有员工 
	 */
	@Override
	public List<EmployeeVo> getAll() {
		 
		return employeeDao.selectByExampleWithDept(null);
	}

	/*  
	 * 保存员工 
	 */
	@Override
	public void saveEmp(Employee employee) {
		employeeDao.insertSelective(employee);
		
	}

	/*  
	 * 检验用户名是否可用 
	 * true:表示用户名可用
	 */
	@Override
	public Boolean checkUser(String empName) {
		Long count = employeeDao.countByEmpName(empName);
		return count == 0;
	}

	/*  
	 * 根据员工id查询员工
	 */
	@Override
	public Employee getEmp(Integer id) {
		 
		return employeeDao.selectByPrimaryKey(id);
	}

	/*  
	 * 更新员工
	 */
	@Override
	public void updateEmp(Employee employee) {
		 employeeDao.updateByPrimaryKeySelective(employee);
		
	}

	/*  
	 * 删除员工
	 */
	@Override
	public void deleteEmp(Integer id) {
		 employeeDao.deleteByPrimaryKey(id);
		
	}

	@Override
	public void deleteByList(List<Integer> list) {
		 employeeDao.deleteByList(list);
		
	}
	
	
}
