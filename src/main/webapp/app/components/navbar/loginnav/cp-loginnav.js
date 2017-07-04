app.controller('loginnavController', logoutnavController);
logoutnavController.$inject = ['userService', '$scope', 'todopoderosoDAO', 'notificationService'];

function logoutnavController(userService, $scope, todopoderosoDAO, notificationService) {
	$scope.registro = { user :'', password: '', rol: {id:0, nombre:''} };
	$scope.roles = [];
	
	todopoderosoDAO.getRoles()
	.then(function(data){
		for (var int = 0; int < data.length; int++) {
			if( data[int].nombre == 'Estudiante' || data[int].nombre == 'Empresa'){
				$scope.roles.push(data[int]);
			}
		}
	})
	.catch(function(error){
		notificationService.addNotificacion('Error al buscar roles', error.data, 'danger');
	});
	
	
	$scope.registrar = function(usuario){
		todopoderosoDAO.registrarUsuario(usuario)
		.then(function(data){
				notificationService.addNotificacion('Usuario registrado correctamente, ahora puede loguearse', 'Nombre: '+data.user, 'success');
			},
			function(data){
				notificationService.addNotificacion('Error al crear usuario', 'Nombre: '+ usuario.user, 'danger');
			})
	}
}

app.component("loginnav", {
	controller: 'loginnavController',
	templateUrl: 'app/components/navbar/loginnav/loginnav.html',
});