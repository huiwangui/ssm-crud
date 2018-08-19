package com.hxjc.bdss.ss.service;

import java.util.List;

import com.hxjc.bdss.ss.model.Employee;
import com.hxjc.bdss.ss.vo.EmployeeVo;

public interface EmployeeService {

	List<EmployeeVo> getAll();

	void saveEmp(Employee employee);

	Boolean checkUser(String empName);

	Employee getEmp(Integer id);

	void updateEmp(Employee employee);

	void deleteEmp(Integer id);

	void deleteByList(List<Integer> list);

}
