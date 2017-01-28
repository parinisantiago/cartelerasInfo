angular.module("cartelerasInfo").controller("loginController",["$scope","$http", function($scope,$http){
	
	$scope.isUser = false;
	$scope.errorMsg = false;
	
	$scope.isLogged = function(){
		return $scope.isUser;
	};
	
	$scope.isNotLogged = function(){
		return !$scope.isUser;
	};
	
	$scope.error = function(){
		return $scope.errorMsg;
	};
	
	$scope.validateLogin = function(login){
		
		$scope.errorMsg = false; //borra el mensaje de error.
		
		$http.post("REST/login",login).then(function(data){
			$scope.isUser = true;
		},function(data){
			$scope.errorMsg = true;
		})
	};
}]);