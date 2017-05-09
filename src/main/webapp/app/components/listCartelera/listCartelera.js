app.controller('listCarteleraController', listCarteleraController);
listCarteleraController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function listCarteleraController($scope, todopoderosoDAO, userService, $http) {
	$scope.carteleras = null;
	$scope.carteleraActiva = null;
	$scope.carteleraNueva = { id:'',habilitado:'', titulo:''};
	todopoderosoDAO.getCarteleras()
			.then(function(data){
				$scope.carteleras = data;
				$scope.carteleraActiva = data[0];
				console.log(userService.getUserData().rol.id);
			})
			.catch(function(error){
				console.log(error);
				alert(error);
			})
	
	$scope.crearCartelera = function(cartelera){
				cartelera.habilitado = 1;
				todopoderosoDAO.createCartelera(cartelera)
					.then(function(data){
						$scope.carteleras = data;
						$scope.carteleraActiva = data[0];
						console.log(userService.getUserData().rol.id);
				})
				.catch(function(error){
					console.log(error);
					alert(error);
				})
			}

	$scope.cambiarCartelera = function(cartelera){
				$scope.carteleraActiva = cartelera;
			}
	
	$scope.logueado = function(){
		console.log(userService.isLogged());
		return userService.isLogged();
	}
	
	$scope.admin= function(){
		return(userService.getUserData().rol.nombre == 'Admin');
	}
	
	$scope.addInteres = function(){
		todopoderosoDAO.addInteres($scope.carteleraActiva)
		.then(function(data){
			$scope.carteleraActiva = data;
			console.log($scope.carteleraActiva);
		})
		.catch(function(error){
			console.log(error);
		});
	}
	
	$scope.removeInteres = function(){
		todopoderosoDAO.removeInteres($scope.carteleraActiva)
		.then(function(data){
			$scope.carteleraActiva = data;
		})
		.catch(function(error){
			console.log(error);
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