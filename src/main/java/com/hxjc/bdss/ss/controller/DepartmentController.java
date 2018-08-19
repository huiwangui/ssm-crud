package com.hxjc.bdss.ss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hxjc.bdss.common.util.Msg;
import com.hxjc.bdss.ss.model.Department;
import com.hxjc.bdss.ss.service.DepartmentService;

/**
 * 处理与部门相关的信息
 * @author 13116
 *
 */
@Controller
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;
	
	/**
	 * 返回所有的部门信息
	 * @return
	 */
	@RequestMapping("/depts")
	@ResponseBody
	public Msg getDepts(){
		List<Department> depts = departmentService.getDepts();	 
		return Msg.success().add("depts", depts);
	}
}
