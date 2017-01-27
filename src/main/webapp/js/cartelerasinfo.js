angular.module("cartelerasInfo", [])

angular.module("cartelerasInfo").controller("loginController",["$http", function($http){
	this.login = {};
	
	this.validateLogin = function(login){
		this.isUser = false;
		var controller = this;
		
		$http.post("REST/login",login).then(function(data){
			console.log("true");
			controller.isUser = true;
		},function(data){
			controller.isUser = false;
			console.log("false");
		})
	};
}]);