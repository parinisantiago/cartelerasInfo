userNotificationsController.$inject = ['todopoderosoDAO', '$scope', '$interval', 'notificationService', 'userService'];
function userNotificationsController(todopoderosoDAO, $scope, $interval, notificationService, userService) {
	$scope.notificaciones = [];
	
	$scope.borrar = function (entry){
		todopoderosoDAO.removeNotificacion(entry)
		.then(
			function(data){
				$scope.notificaciones.splice($scope.notificaciones.indexOf(entry),1);
			},
			function(error){
				notificationService.addNotificacion('Error al eliminar notificacion', entry.descripcion, 'danger');
			}
		);
	}
	
	$scope.borrarTodo = function(){
		$scope.notificaciones.forEach(function(entry) {
			$scope.borrar(entry);
		});
	}
	
	$scope.hayNotificaciones = function(){
		return $scope.notificaciones.length > 0;
	}
	
	$scope.actualizar = function(){
		todopoderosoDAO.getNotificaciones(userService.getUserData())
		.then(function(data){
			$scope.notificaciones = data;
		})
		.catch(function(error){
			notificationService.addNotificacion('Error al buscar notificaciones', '', 'danger');
		})
	}
	
	$scope.actualizar();
	
	$scope.buscarActualizaciones = $interval(function(){
			if(userService.isLogged()){
				$scope.actualizar()
			}
			else{
				$interval.cancel($scope.buscarActualizaciones);
			}
		}, 15000);
	//cuando se destruye el componente se cancela el pedido de actualizaciones
	$scope.$on('$destroy', function () { $interval.cancel($scope.buscarActualizaciones); })
}

app.component("userNotifications", {
		controller: userNotificationsController,
		templateUrl: 'app/components/userNotifications/userNotifications.html',
	
});