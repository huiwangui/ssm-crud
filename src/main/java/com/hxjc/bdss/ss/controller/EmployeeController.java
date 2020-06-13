package com.hxjc.bdss.ss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hxjc.bdss.common.util.Msg;
import com.hxjc.bdss.ss.model.Employee;
import com.hxjc.bdss.ss.service.EmployeeService;
import com.hxjc.bdss.ss.vo.EmployeeVo;

/**
 * 处理员工的CRUD
 * @author 13116
 *
 */
@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 删除员工
	 * 
	 * 单个批量二合一
	 * 批量删除：1-2-3
	 * 单个删除：1
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmpById(@PathVariable("ids")String ids){
		//批量删除
		if(ids.contains("-")){
			List<Integer> list = new ArrayList<Integer>();
			String[] strs = ids.split("-");
			for (String str : strs) {
				list.add(Integer.parseInt(str));				
			}
			employeeService.deleteByList(list);
		}else{
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		
		return Msg.success();
	}
	
	/**
	 * 
	 * 问题：
	 * 请求体中有数据，但是Employee对象封装不上
	 * 
	 * 原因：
	 * Tomcat：
	 * 		 1、将请求体中的数据，封装一个map
	 * 		 2、request.getParameter("empName")就会从这个map中取值。
	 * 		 3、SpaingMVC封装POJO对象的时候
	 * 					会把POJO中每个属性的值，request.getParameter("empName")
	 * 
	 * AJAX发送PUT请求引发的血案：
	 * 		PUT请求，请求体中的数据，request.getParameter("empName")拿不到
	 * 		Tomcat一看是PUT不会封装请求体中的数据为map，只有POST形式的请求才封装请求体为map
	 * 
	 * 解决方案(SpringMVC提供的)：
	 * 我们要能支持直接发送PUT之类的请求还要封装请求体中的数据
	 * 1、配置上HttpPutFormContentFilter；
	 * 2、它的作用：将请求体中的数据解析包装成一个map。
	 * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
	 * 
	 * 更新员工
	 * @param employee
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/emp/{empId}",method=RequestMethod.PUT)
	public Msg updateEmp(Employee employee){
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	/**
	 * 根据id查询员工
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/emp/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id") Integer id){
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * 检查用户名是否可用
	 * @param empName
	 * @return 
	 */
	@RequestMapping("/checkuser")
	@ResponseBody
	public Msg checkuser(@RequestParam("empName")String empName){
		//先判断用户名是否是合法的表达式
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regx)){
			return Msg.fail().add("va_msg", "用户名必须是2-5位中文或者6-16位数字和数字的组合");
		}
		Boolean b = employeeService.checkUser(empName);
		if(b){
			return Msg.success();
		}else{
			return Msg.fail().add("va_msg", "用户名不可用");
		}
		
	}
	
	/**
	 * rest风格的uri：
	 * /emp/{id} get查询员工
	 * /emp/ post保存员工
	 * /emp/{id} put修改员工
	 * /emp/{id} delete删除员工
	 * 员工保存
	 * 1、支持JSR303校验
	 * 2、导入hibernate-validator
	 * @param employee
	 * @return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result){
		if(result.hasErrors()){
			//校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError fieldError : fieldErrors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else{
			employeeService.saveEmp(employee);
			return Msg.success();
		}
		
	}
	
	/**
	 * 要想@ResponseBody起作用：就需要导入jackson包
	 * @ResponseBody的作用：把PageInfo类以json字符串形式返回给客户端
	 * 
	 * @param pn
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn){
		//引入PageHelper分页插件
		//在查询之前只需要调用，传入页码以及每页的大小
		PageHelper.startPage(pn, 5);
		//startPage后面紧跟的这个查询就是一个分页查询
		List<EmployeeVo>  emps = employeeService.getAll();
		//使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
		//封装了详细的分页信息，包括有我们查询出来的数据,传入连续显示的页数
		PageInfo page = new PageInfo(emps,5);
		return Msg.success().add("pageInfo", page);
	}
	/**
	 * 查询员工数据(分页查询)
	 * @param pn
	 * @param model
	 * @return 
	 */
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1")Integer pn, Model model){
		//引入PageHelper分页插件
		//在查询之前只需要调用，传入页码以及每页的大小
		PageHelper.startPage(pn, 5);
		//startPage后面紧跟的这个查询就是一个分页查询
		List<EmployeeVo>  emps = employeeService.getAll();
		//使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了
		//封装了详细的分页信息，包括有我们查询出来的数据,传入连续显示的页数
		PageInfo page = new PageInfo(emps,5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
}
