<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
 
<script type="text/javascript" src="${pageContext.request.contextPath}/statics/js/jquery-1.11.0.min.js"></script>
 
<link href="${pageContext.request.contextPath}/statics/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/statics/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<!-- 标题  -->
		<div class="row">
			<div class="col-md-12">
				<h2>BDSS-CRUD</h2>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<th>#</th>
						<th>empName</th>
						<th>gender</th>
						<th>email</th>
						<th>deptName</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${pageInfo.list }" var="emp">
						<tr>
							<th>${emp.empId }</th>
							<th>${emp.empName }</th>
							<th>${emp.gender == "M" ?"男" :"女" }</th>
							<th>${emp.email }</th>
							<th>${emp.department.deptName}</th>
							<th>
								<button class="btn btn-primary btn-sm">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 编辑
								</button>
								<button class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除
								</button>
							</th>
						</tr>
					</c:forEach>
					
				</table>
			</div>
		</div>
		
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
				<div class="col-md-6">
					当前第${pageInfo.pageNum }页,总${pageInfo.pages }页,总${pageInfo.total }条记录
				</div>
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				  <ul class="pagination">
				  	<li><a href="${pageContext.request.contextPath}/emps?pn=1" >首页</a></li>
				  	<c:if test="${pageInfo.hasPreviousPage }">
					  	<li>
					      <a href="${pageContext.request.contextPath}/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
					    </li>
				  	</c:if>			    
				    <c:forEach items="${pageInfo.navigatepageNums }" var="page_num">
				    	<c:choose>
				    		<c:when test="${pageInfo.pageNum == page_num }">
				    			 <li class="active"><a href="#">${page_num }</a></li>
				    		</c:when>
				    		<c:otherwise>
				    			<li><a href="${pageContext.request.contextPath}/emps?pn=${page_num }">${page_num }</a></li>
				    		</c:otherwise>
				    	</c:choose>
				    </c:forEach> 
				    <c:if test="${pageInfo.hasNextPage }">
					    <li>
					      <a href="${pageContext.request.contextPath}/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
					        <span aria-hidden="true">&raquo;</span>
					      </a>
					    </li>
				    </c:if>
				   
				    <li><a href="${pageContext.request.contextPath}/emps?pn=${pageInfo.pages}" >尾页</a></li>
				  </ul>
				</nav>		 
			</div>
		</div>
	</div>
</body>
</html>