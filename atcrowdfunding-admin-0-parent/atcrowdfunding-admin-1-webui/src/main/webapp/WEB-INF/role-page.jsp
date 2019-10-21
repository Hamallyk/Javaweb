<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="UTF-8">
<%@ include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="script/jquery.pagination.js"></script>
<script type="text/javascript" src="script/my-role.js" charset="UTF-8" ></script>
<script type="text/javascript">
$(function(){
	
	initGlobalVariable();
	showPage();
	$("#searchBtn").click(function(){
		var keyword = $.trim($("#keywordInput").val());
		if(keyword==null || keyword==""){
			layer.msg("请输入关键词!");
			return;
		}
		window.keyword=keyword;
		showPage();
	});
	
	$("#summaryBox").click(function(){	
		$(".itemBox").prop("checked",this.checked);
	});
	$("#batchRemoveBtn").click(function(){
		
		var length = $(".itemBox:checked").length;
		
		if(length == 0) {
			layer.msg("请至少选择一条！");
			return ;
		}
		
		window.roleIdArray = new Array();
		
	
		$(".itemBox:checked").each(function(){
			
			var roleId = $(this).attr("roleid");
			window.roleIdArray.push(roleId);
			
		});
	
		showRemoveConfirmModal();
	});
	
	$("#confirmModalBtn").click(function(){
		
		var requestBody = JSON.stringify(window.roleIdArray);
		
		$.ajax({
			"url":"role/batch/remove.json",
			"type":"post",
			"data":requestBody,
			"contentType":"application/json;charset=UTF-8",
			"dataType":"json",
			"success":function(response){
				
				var result = response.result;
				
				if(result == "SUCCESS") {
					layer.msg("操作成功！");
							
					showPage();
				}
				
				if(result == "FAILED") {
					layer.msg(response.message);
				}
						
				$("#confirmModal").modal("hide");
				
			},
			"error":function(response){
				layer.msg(response.message);
			}
		});
		
	});
	
	$("#roleTableBody").on("click",".removeBtn", function(){
		
		var roleId = $(this).attr("roleId");
		
		window.roleIdArray = new Array();
		
		window.roleIdArray.push(roleId);
		
		showRemoveConfirmModal();
	});
	$("#addBtn").click(function(){
		
		$("#addModal").modal("show");
		
	});
	
	$("#addModalBtn").click(function(){
		

		var roleName = $.trim($("#roleNameInput").val());
		
		if(roleName == null || roleName == "") {
			layer.msg("请输入有效角色名称！");
			return ;
		}
		
	
		$.ajax({
			"url":"role/save/role.json",
			"type":"post",
			"data":{
				"roleName":roleName
			},
			"dataType":"json",
			"success":function(response){
				
				var result = response.result;
				
				if(result == "SUCCESS") {
					layer.msg("操作成功！");
					
					// 3.操作成功重新分页
					// 前往最后一页
					window.pageNum = 999999;
					showPage();
				}
				
				if(result == "FAILED") {
					layer.msg(response.message);
				}
				
			
				$("#addModal").modal("hide");
				
		
				$("#roleNameInput").val("");
				
			},
			"error":function(response){
				layer.msg(response.message);
			}
		});
		
	});
	$("#roleTableBody").on("click",".editBtn",function(){
		
		window.roleId = $(this).attr("roleId");
		var roleName = $(this).parents("tr").children("td:eq(2)").text();
		
		$("#roleNameInputEdit").val(roleName);
		
		$("#editModal").modal("show");
	});
	
	$("#editModalBtn").click(function(){
		
	
		var roleName = $.trim($("#roleNameInputEdit").val());
		
		if(roleName == null || roleName == "") {
			layer.msg("请输入有效角色名称！");
			
			return ;
		}
		
		// 2.发送请求
		$.ajax({
			"url":"role/update/role.json",
			"type":"post",
			"data":{
				"id":window.roleId,
				"name":roleName
			},
			"dataType":"json",
			"success":function(response){
				var result = response.result;
				
				if(result == "SUCCESS") {
					layer.msg("操作成功！");
					
					showPage();
				}
				
				if(result == "FAILED") {
					layer.msg(response.message);
				}
				

				$("#editModal").modal("hide");
				
			}
		});
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
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="keywordInput" class="form-control has-success" type="text"
										placeholder="请输入查询条件">
								</div>
							</div>
							<button id="searchBtn" type="button" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button id="batchRemoveBtn" type="button" class="btn btn-danger"
							style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 批量删除
						</button>
						<button id="addBtn" type="button" class="btn btn-primary"
							style="float: right;">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<table class="table  table-bordered">
								<thead>
									<tr>
										<th width="30">#</th>
										<th width="30"><input id="summaryBox" type="checkbox"></th>
										<th>名称</th>
										<th width="100">操作</th>
									</tr>
								</thead>
								<tbody id="roleTableBody"></tbody>
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

	<%@ include file="/WEB-INF/include-modal-role-confirm.jsp" %>
	<%@ include file="/WEB-INF/include-modal-role-add.jsp" %>
	<%@ include file="/WEB-INF/include-modal-role-edit.jsp" %>


</body>
</html>