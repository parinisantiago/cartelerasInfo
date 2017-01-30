app.controller('cartelerasCtl'
		,['$scope', 'todopoderosoDAO', '$http', 
		function($scope, todopoderosoDAO, $http) {
		$scope.carteleras = null;
		$scope.carteleraActiva = null;
		//usar sesion
		$scope.usuario = null;
		
		todopoderosoDAO.getCarteleras()
				.then(function(data){
					$scope.carteleras = data;
					$scope.carteleraActiva = data[0];
				})
				.catch(function(error){
					console.log(error);
					alert(error);
				})
		
		var cambiarCartelera = function(cartelera){
					console.log(cartelera);
					$scope.carteleraActiva = cartelera;
				}
    
    //var cargarDatos = CarteleraService.getCarteleras(); 
    					/*$http.get("REST/cartelera").then(
    						function(respuesta){
    							$scope.carteleras = respuesta.data;
    							$scope.carteleraActiva = respuesta.data[0];
    							console.log($scope.carteleras);
						    },
						    function(respuesta){
						    	console.log("error al cargar los datos");
						    	console.log(respuesta);
						    	alert("Error al cargar los datos.");
						    });
						    */
    
		}]);