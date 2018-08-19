package com.hxjc.bdss.ss.vo;

import com.hxjc.bdss.ss.model.Department;
import com.hxjc.bdss.ss.model.Employee;

public class EmployeeVo extends Employee{
	
	private Department department;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}
