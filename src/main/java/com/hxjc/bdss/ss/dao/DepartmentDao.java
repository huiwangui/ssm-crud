package com.hxjc.bdss.ss.dao;

 
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.hxjc.bdss.common.annotation.MyBatisDao;
import com.hxjc.bdss.ss.model.Department;
//@Component
public interface DepartmentDao {
    long countByExample(Department example);

    int deleteByExample(Department example);

    int deleteByPrimaryKey(Integer deptId);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByExample(Department example);

    Department selectByPrimaryKey(Integer deptId);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") Department example);

    int updateByExample(@Param("record") Department record, @Param("example") Department example);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}