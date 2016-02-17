 <%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
 <script src="/js/ngGrid-core.js"></script>
<script>
	grid.init({
		selectUrl : "/admin/board/listJson.do",
		deleteUrl : "/admin/board/delete.do", 
		method : "POST"
	});
</script>
 
 <div class="row">

	 <div class="col-lg-12">
	     <h3 class="page-header">Board</h3>
	 </div>
	 
	 <div class="col-lg-12">
	  <div class="panel panel-default">
	      <div class="panel-body">
	          <div class="table-responsive">
	          
				<div ng-app="appModule">
           	   	<div ng-controller="ngCtr">
           	   
	          	<div>Total : {{dataList.length}}</div>
	          	<div style="height:2px;background-color: #DDDDDD;"></div>
	          	
	          	<div style="height:500px">
	              <table class="table table-striped table-hover" >
	                  <thead>
	                       <th width="5%"  style="text-align:center"><input type="checkbox"  id="checkAll"></th>
	                       <th width="10%" >번호</th>
	                       <th width="50%">제목</th>
	                       <th width="10%">이름</th>
	                       <th width="20%">날자</th> 
	                     </tr>
	                  </thead>
	                  <tbody>
	                       <tr ng-repeat="list in dataList">
	                          <td style="text-align:center"><input type="checkbox"></td>
	                          <td>{{ list.no }}</td>
	                          <td>{{ list.title }}</td>
	                          <td>{{ list.userNm }}</td>
	                          <td>{{ list.regDate | date:'y/MM/dd h:mm:ss a' }}</td>
	                      </tr>
	                   </tbody>
	               </table>
				</div>
				
				<button type="button" class="btn btn-primary" ng-click="getData()"><i class="fa fa-edit fa-fw"></i> 글쓰기</button>
				<button type="button" class="btn btn-primary" ng-click="deleteRow()"><i class="fa fa-edit fa-fw"></i> 삭제</button>

				</div>
				</div>
	              
	           </div>
	           
	       </div>
	   </div>
	</div>

</div>

