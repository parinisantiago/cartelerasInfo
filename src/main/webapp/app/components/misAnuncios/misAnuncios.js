app.controller('misAnunciosController', carteleraController);
carteleraController.$inject = ['$scope', 'localstorage', 'todopoderosoDAO', 'userService', 'notificationService', '$http', '$state'];

function carteleraController($scope, localstorage, todopoderosoDAO, userService, notificationService, $http, $state) {
	
	$scope.wrapperCartelera= {anuncios:[]};
	if(userService.isLogged()){
		todopoderosoDAO.getAnunciosUsuario(userService.getUserData())
		.then(function(data){
			$scope.wrapperCartelera.anuncios = data;
		})
		.catch(function(error){
			notificationService.addNotificacion('Error al buscar anuncios propios', '', 'danger');
		})
	}
	else{
		notificationService.addNotificacion('Usted debe loguearse para ver sus anuncios', '', 'danger');
		$state.go("home");
	}
	
}

app.component("misAnuncios", {
		controller: 'misAnunciosController',
		templateUrl: 'app/components/misAnuncios/misAnuncios.html',
});