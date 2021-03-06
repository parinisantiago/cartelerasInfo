app.controller('loginController', loginController);
loginController.$inject = ['userService', 'notificationService', '$scope'];

function loginController(userService, notificationService, $scope) {
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
			notificationService.addNotificacion('Error al loguearse', 'Usuario o contraseña inválidos', 'warning');
		})
	};
}

app.component("cpLogin", {
		controller: 'loginController',
		templateUrl: 'app/components/login/login.html',
});