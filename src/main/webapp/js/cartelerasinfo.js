angular.module("cartelerasInfo", [])

angular.module("cartelerasInfo").controller("loginController",["$http", function($http){
	this.login = {};
	
	this.validateLogin = function(login){
		
		var controller = this;
		
		$http.post("/login",login).success(function(data){
			controller.isUser = data;
		});
	};
}]);