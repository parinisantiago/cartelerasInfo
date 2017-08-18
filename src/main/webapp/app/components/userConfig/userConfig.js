userConfigController.$inject = ['notificationService', 'userService', 'todopoderosoDAO', 'localstorage', '$scope', '$state'];
function userConfigController(notificationService, userService, todopoderosoDAO, localstorage, $scope, $state) {
	$scope.logueadoOHome = function(){
		var logueado = userService.isLogged();
		if(!logueado){
			$state.go("home");
		}
		return logueado;
	}
	
	$scope.file=null;
	
	$scope.imagenPerfil = userService.getImagenPerfil();
	
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
	
	$scope.addImage = function(imagen){
		$scope.file = imagen.file;
	}
	
	$scope.removeImage = function(file){
		file.cancel();
		$scope.file = null;
	}
	
	$scope.cambiarFotoPerfil= function(){
		todopoderosoDAO.editFotoUsuario(userService.getUserData(), $scope.file)
		.then(
			function(data){
				notificationService.addNotificacion('foto perfil cambiada correctamente', '', 'success');
				userService.forceLoginRefresh()
				setTimeout(function(){
							$scope.imagenPerfil = userService.getImagenPerfil();
						}
						,1000);
				
			},
			function(data){
				notificationService.addNotificacion('Error al cambiar foto perfil de usuario', '', 'danger');
			});
	}
}

app.component("userConfig", {
		controller: userConfigController,
		templateUrl: 'app/components/userConfig/userConfig.html',
	
});