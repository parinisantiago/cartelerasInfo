angular.module("cartelerasInfo", [])

angular.module("cartelerasInfo").controller("loginController",["$http", function($http){
	this.login = {};
	
	this.validateLogin = function($http){
		
		var controller = this;
		
		http.post("/login",this.login).success(function(data){
			controller.isUser = data;
		});
	};
}]);