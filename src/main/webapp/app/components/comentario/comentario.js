app.controller('comentarioController', comentarioController);
comentarioController.$inject = ['$scope', 'todopoderosoDAO', 'userService', 'notificationService', '$http'];

function comentarioController($scope, todopoderosoDAO, userService, notificationService, $http) {
	var ctrl = this;
	this.logueado = function(){ return userService.isLogged(); };
	
	this.text='';
	
	this.comentar = function(){
		if(this.text != ''){
			todopoderosoDAO.createComentario(userService.getUserData(), this.text, this.anuncio)
			.then(function(data){
				data.creador = userService.getUserData();
				data.anuncio = ctrl.anuncio
				ctrl.anuncio.comentarios.push(data);
				ctrl.text='';
			})
			.catch(function(error){
				notificationService.addNotificacion('Error al crear comentario', error.data, 'danger');
			});
		}
	};
	
	$scope.isDuenioCartel = function(){
		return (ctrl.anuncio.creador.id == userService.getUserData().id);
	}
	
	$scope.isDuenioComentario = function(comentario){
		return (comentario.creador.id == userService.getUserData().id);
	}
	
	$scope.puedeEliminarComentario = function(comentario){
		return (userService.isLogged() && (userService.isAdmin() || $scope.isDuenioCartel() || $scope.isDuenioComentario(comentario)));
	}
	
	$scope.eliminar = function(comentario){
		if(confirm("Esta seguro de borrar este comentario?")){
			todopoderosoDAO.eliminarComentario(comentario)
			.then(function(){
				ctrl.anuncio.comentarios.splice( ctrl.anuncio.comentarios.indexOf(comentario), 1 );
			},
			function(){
				notificationService.addNotificacion('Error al borrar comentario', error.data, 'danger');
			}
			);
		}
	}
}

app.component("comentario", {
		controller: 'comentarioController',
		templateUrl: 'app/components/comentario/comentario.html',
		bindings: {
			comentarios:'<',
			anuncio:'<'
		}
});