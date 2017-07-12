userConfigController.$inject = ['notificationService', 'userService', 'todopoderosoDAO', 'localstorage', '$scope', '$state'];
function userConfigController(notificationService, userService, todopoderosoDAO, localstorage, $scope, $state) {
	$scope.logueadoOHome = function(){
		var logueado = userService.isLogged();
		if(!logueado){
			$state.go("home");
		}
		return logueado;
	}
	
	$scope.userData = userService.getUserData(); 
	$scope.form = { PasswordOld : null, PasswordNew : null, PasswordNew2 : null};
	$scope.vaciarform = function(){
		$scope.form = { PasswordOld : null, PasswordNew : null, PasswordNew2 : null};
	};
	
	$scope.changePassword = function(){
		todopoderosoDAO.editPasswordUsuario(userService.getUserData(), $scope.form.PasswordOld, $scope.form.PasswordNew)
		.then(
			function(data){
				notificationService.addNotificacion('Contraseña cambiada correctamente', '', 'success');
				$scope.vaciarform();
			},
			function(data){
				if(data.status == 401){
					notificationService.addNotificacion('Error al cambiar contraseña usuario:', 'antigua contraseña incorrecta', 'danger');
				}
				else{
					notificationService.addNotificacion('Error al cambiar contraseña usuario', '', 'danger');
				}
			});
	}
}

app.component("userConfig", {
		controller: userConfigController,
		templateUrl: 'app/components/userConfig/userConfig.html',
	
});