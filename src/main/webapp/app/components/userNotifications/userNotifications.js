userNotificationsController.$inject = ['todopoderosoDAO', '$scope', 'notificationService', 'userService'];
function userNotificationsController(todopoderosoDAO, $scope, notificationService, userService) {
	$scope.notificaciones = [];
	
	$scope.borrarTodo = function(){
		$scope.notificaciones.forEach(function(entry) {
			todopoderosoDAO.removeNotificacion(entry)
			.then(function(data){
				$scope.notificaciones.split($scope.notificaciones.indexof(entry),1);
			})
			.catch(function(error){
				notificationService.addNotificacion('Error al eliminar notificacion', entry.descripcion, 'danger');
			})
		});
	}
	
	$scope.hayNotificaciones = function(){
		return $scope.notificaciones.length > 0;
	}
	
	$scope.actualizar = function(){
		todopoderosoDAO.getNotificaciones(userService.getUserData())
		.then(function(data){
			data.forEach(function(entry) {
				$scope.notificaciones.push(entry);
			});
		})
		.catch(function(error){
			notificationService.addNotificacion('Error al buscar notificaciones', '', 'danger');
		})
	}
	
	$scope.actualizar();
	
	
}

app.component("userNotifications", {
		controller: userNotificationsController,
		templateUrl: 'app/components/userNotifications/userNotifications.html',
	
});