angular.module("cartelerasInfo").controller("loginController",["$scope","$http", function($scope,$http){
	$scope.isUser = false;
	
	$scope.validateLogin = function(login){
		$http.post("REST/login",login).then(function(data){
			$scope.isUser = true;
		},function(data){
			$scope.isUser = false;
		})
	};
}]);