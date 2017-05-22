app.controller('listCarteleraController', listCarteleraController);
listCarteleraController.$inject = ['$scope', 'todopoderosoDAO', 'userService', 'notificationService', '$http'];

function listCarteleraController($scope, todopoderosoDAO, userService, notificationService, $http) {
	$scope.carteleras = null;
	$scope.carteleraActiva = null;
	$scope.carteleraNueva = '';
	todopoderosoDAO.getCarteleras()
			.then(function(data){
				$scope.carteleras = data;
				$scope.carteleraActiva = data[0];
			})
			.catch(function(error){
				notificationService.addNotificacion('Error al buscar carteleras', '', 'danger');
			})
	
	$scope.crearCartelera = function(cartelera){
				todopoderosoDAO.createCartelera(cartelera)
				.then(function(data){
						todopoderosoDAO.getCarteleras()
							.then(function(data){
								$scope.carteleras = data;
								$scope.carteleraActiva = data[data.length - 1];
							})
						notificationService.addNotificacion('Cartelera creada correctamente', '', 's');
				})
				.catch(function(error){
					console.log(error);
					notificationService.addNotificacion('Error al crear cartelera', '', 'danger');
				})
			}

	$scope.cambiarCartelera = function(cartelera){
				$scope.carteleraActiva = cartelera;
			}
	
	$scope.logueado = function(){
		return userService.isLogged();
	}
	
	
	$scope.admin= function(){
		return(userService.isLogged() && (userService.getUserData().rol.nombre == "Admin"));
	}
	
	$scope.addInteres = function(){
		todopoderosoDAO.addInteres($scope.carteleraActiva)
		.then(function(data){
			$scope.carteleraActiva = data;
			notificationService.addNotificacion('Usted se ha inscripto en ' + data.titulo, '', 'success');
		})
		.catch(function(error){
			notificationService.addNotificacion('Error al inscribirse cartelera', '', 'danger');
		});
	}
	
	$scope.removeInteres = function(){
		todopoderosoDAO.removeInteres($scope.carteleraActiva)
		.then(function(data){
			$scope.carteleraActiva = data;
		})
		.catch(function(error){
			notificationService.addNotificacion('Error al desuscribirse', '', 'danger');
		});
	}
	
	$scope.interesado = function(){
		if(!$scope.carteleraActiva){
			return false;
		}
		else{
			if(userService.isLogged()){
				var resultado = false;
				var user = userService.getUserData();
				var interesados = $scope.carteleraActiva.interesados; 
				interesados.forEach(function(usuario) {
				    if(usuario.id == user.userID){
				    	resultado = true;
				    }
				});
				return resultado;
			}
			else{
				return false;
			}
		}
	}
}

app.component("listCartelera", {
		controller: 'listCarteleraController',
		templateUrl: 'app/components/listCartelera/listCartelera.html',
	
});