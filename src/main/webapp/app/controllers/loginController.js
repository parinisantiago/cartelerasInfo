app.controller('loginCtl'
		,['$scope', 'userService', '$http', 
		function($scope, userService, $http) {
		$scope.form={
				user : '',
				password: ''
		};
		$scope.usuario= userService.getUserData(); 
		userService.loginRefresh();
		$scope.loguear = function(){
			userService.login($scope.form.user, $scope.form.password)
		    .then(function(data){
					$scope.form.password='';
				})
			.catch(function(error){
				$scope.form.password='';
			})
		};
		$scope.isLogged = function(){
			return userService.isLogged();
		};
		$scope.logout = function(){
			userService.logout();
		};
		}]);