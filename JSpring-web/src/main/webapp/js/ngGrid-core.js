var grid = {
		
	init : function(params){
		
		var gridApp = angular.module('appModule', []);
		
		gridApp.factory('server', function($http){
		    return {
		        getData : function(params) {
		            return $http({
						method: params.method,
						url: params.selectUrl,
						data: {key: "key"},
		            	headers: {'Content-Type': 'application/x-www-form-urlencoded'}
					})
		        },
		        deleteData : function(params){
		        	 return $http({
						method: params.method,
						url: params.deleteUrl,
						data: $.param({"seq": 1}),
						headers: {'Content-Type': 'application/x-www-form-urlencoded'}
					})
		        }
		    };
		});
		
		gridApp.controller('ngCtr', ['$scope', 'server', function ($scope, server) {
		
			 //데이터 객체 생성
			$scope.dataList = {};
			$scope.getData = function() {
				server.getData(params).success( function(data, status) {
			        $scope.dataList = data.dataList;
			    }).error(function (data, status) {
				    alert(":(");
				})
			 };
			 $scope.getData();
			 
			//Row 삭제    
			$scope.deleteRow = function() {
				server.deleteData(params).success( function(data, status) {
					//alert(data.result);
					$scope.getData();
				}).error(function (data, status) {
				    alert(":(");
				})
			};
			    
			 $scope.send = function() {
				angular.forEach(idCheck, function(obj) {
					alert(obj.id)
				    /* do something for all key: value pairs */
				});
			 };
			    
			//Row 추가
			$scope.addRow = function(){		
				$scope.dataList.push({ 'name' : 'merong', 'title': '타이틀', 'date' : new Date()});
			};
			
			//disabled
			$scope.disabled = function (list) {
				return (list.disabled == true) ?  true : false
		   };
		   
		   
		}]);
		
	}

} 



	