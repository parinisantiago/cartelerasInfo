app.controller('loginCtl'
		,['$scope', 'userService', '$http', 
		function($scope, userService, $http) {
		$scope.form={
				user : '',
				password: ''
		}; 
		userService.loginRefresh();
		$scope.loguear = function(){
			userService.login($scope.form.user, $scope.form.password)
		    .then(function(data){
					$scope.form.password='';
					$scope.usuario= userService.getUserData();
				})
			.catch(function(error){
				$scope.form.password='';
			})
		};
		$scope.isLogged = function(){
			$scope.usuario= userService.getUserData();
			return userService.isLogged();
		};
		$scope.logout = function(){
			userService.logout();
		};
		}]);