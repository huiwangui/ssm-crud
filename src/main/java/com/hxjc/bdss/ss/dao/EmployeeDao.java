package com.hxjc.bdss.ss.dao;

 
import java.util.List;

import com.hxjc.bdss.ss.model.Employee;
import com.hxjc.bdss.ss.vo.EmployeeVo;
//@MyBatisDao
//@Component
public interface EmployeeDao {
    

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(Employee example);

    Employee selectByPrimaryKey(Integer empId);
    
    List<EmployeeVo> selectByExampleWithDept(Employee example);
    
    EmployeeVo selectByPrimaryKeyWithDept(Integer empId);
    
    Long countByEmpName(String empName);

	void updateByPrimaryKeySelective(Employee employee);

	void deleteByPrimaryKey(Integer id);
	
	void deleteByList(List<Integer> list);

   
}