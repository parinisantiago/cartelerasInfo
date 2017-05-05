app.controller('cartelerasCtl'
		,['$scope', 'todopoderosoDAO', 'userService', '$http', 
		function($scope, todopoderosoDAO, userService, $http) {
		$scope.carteleras = null;
		$scope.carteleraActiva = null;
		
		todopoderosoDAO.getCarteleras()
				.then(function(data){
					console.log(data);
					$scope.carteleras = data;
					$scope.carteleraActiva = data[0];
				})
				.catch(function(error){
					console.log(error);
					alert(error);
				})
		
		$scope.cambiarCartelera = function(cartelera){
					$scope.carteleraActiva = cartelera;
				}
		
		$scope.addInteres = function(){
			todopoderosoDAO.addInteres($scope.carteleraActiva)
			.then(function(data){
				console.log(data);
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
    
		}]);