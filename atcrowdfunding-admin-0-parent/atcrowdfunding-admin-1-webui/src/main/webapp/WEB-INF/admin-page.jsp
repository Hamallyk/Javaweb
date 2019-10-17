<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="UTF-8">
<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/my-admin.js"  charset="UTF-8" ></script>
<script type="text/javascript">
$(function() {
	// 初始化全局变量
	window.totalRecord = ${requestScope['PAGE_INFO'].total};
	window.pageSize = ${requestScope['PAGE_INFO'].pageSize};
	window.pageNum = ${requestScope['PAGE_INFO'].pageNum};
	
	// 每一次页面最初显示的时候都会把keyword设置为最新值
	window.keyword = "${param.keyword}";
	
	// 对分页导航条显示进行初始化
	initPagination();
	$("#summaryBox").click(function() {
		
		// 获取当前#summaryBox的勾选状态
		// this代表当前多选框对象（DOM对象）
		// checked属性为true时表示被勾选，为false时表示没有被勾选
		// 使用checkStatus设置.itemBox的状态
		$(".itemBox").prop("checked",this.checked);
	});
	
	$("#batchRemoveBtn").click(function(){
		var adminIdArray = new Array();
		var loginAcctArray = new Array();
		
		$(".itemBox:checked").each(function(){
			var adminId = $(this).attr("adminId");
			adminIdArray.push(adminId);
			var loginAcct = $(this)				// 当前checkbox对象
			.parent("td")	// 包含checkbox的td
			.next()			// 当前td的下一个兄弟元素，也就是下一个td
			.text();		// 下一个td的标签内部的文本

			loginAcctArray.push(loginAcct);
		});
		
		if(adminIdArray.length == 0) {
			
			// 给出提示
			alert("请勾选您要删除的记录！");
			
			// 函数停止执行
			return ;
		}
		
		// 给出确认提示，让用户确认是否真的删除这两条记录
		var confirmResult = confirm("您真的要删除"+loginAcctArray+"信息吗？操作不可逆，请谨慎决定！");
		
		// 如果用户点击了取消，那么让函数停止执行
		if(!confirmResult) {
			return ;
		}
		
		doBatchRemove(adminIdArray);
	});
	
	$(".uniqueRemovebtn").click(function(){
		var adminId= $(this).attr("adminid");
		var loginAcct = $(this).parents("tr").children("td:eq(2)").text();
		
		var confirmResult = confirm("您真的要删除"+loginAcct+"这条记录吗？");
		
		if(!confirmResult) {
			return ;
		}
		
		// 为了能够使用批量删除的操作，将adminId存入数组
		var adminIdArray = new Array();
		
		adminIdArray.push(adminId);
		doBatchRemove(adminIdArray);
	});
});


</script>


<body>

	<%@ include file="/WEB-INF/include-nav.jsp"%>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="/WEB-INF/include-sidebar.jsp"%>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表${totalRecord}
						</h3>
					</div>
					<div class="panel-body">
						<form action="admin/query/for/search.html" class="form-inline"
							role="form" style="float: left;" method="post">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input name="keyword" class="form-control has-success"
										type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button type="submit" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button id="batchRemoveBtn" type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
					<a href="admin/to/add/page.html" class="btn btn-primary" style="float: right;"><i class="glyphicon glyphicon-plus"></i> 新增</a>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input id="summaryBox" type="checkbox"></th>
										<th>账号</th>
										<th>名称</th>
										<th>邮箱地址</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${empty requestScope['PAGE_INFO'].list}">
										<tr>
											<td style="text-align: center;" colspan="6">抱歉！没有符合您要求的查询结果！</td>
										</tr>
									</c:if>
									<c:if test="${!empty requestScope['PAGE_INFO'].list}">
										<c:forEach items="${requestScope['PAGE_INFO'].list}"
											var="admin" varStatus="myStatus">
											<tr>
												<td>${myStatus.count }</td>
												<td><input adminId="${admin.id }" class="itemBox"
													type="checkbox"></td>
												<td>${admin.loginAccet }</td>
												<td>${admin.userName }</td>
												<td>${admin.email }</td>
												<td>
													<button type="button" class="btn btn-success btn-xs">
														<i class=" glyphicon glyphicon-check"></i>
													</button>
													<a href="admin/to/edit/page.html?adminId=${admin.id }&pageNum=${requestScope['PAGE_INFO'].pageNum}" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></a>
													<button  adminId="${admin.id }" type="button" adminId="${admin.id }" class="btn btn-danger btn-xs uniqueRemovebtn">
														<i class=" glyphicon glyphicon-remove"></i>
													</button>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="6" align="center">
											<div id="Pagination" class="pagination">
												<!-- 这里显示分页 -->
											</div>
										</td>
									</tr>
								</tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
