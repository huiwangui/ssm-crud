package com.hxjc.bdss.ss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxjc.bdss.ss.dao.DepartmentDao;
import com.hxjc.bdss.ss.model.Department;
import com.hxjc.bdss.ss.service.DepartmentService;
@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	DepartmentDao departmentDao;
	@Override
	public List<Department> getDepts(){
		return departmentDao.selectByExample(null);
	}
}
