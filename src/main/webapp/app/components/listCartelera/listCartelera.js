app.controller('listCarteleraController', listCarteleraController);
listCarteleraController.$inject = ['$scope', 'todopoderosoDAO', 'userService', 'notificationService', '$http', '$filter'];

function listCarteleraController($scope, todopoderosoDAO, userService, notificationService, $http, $filter) {
	ctl = this
	
	$scope.carteleras = null;
	$scope.carteleraActiva = null;
	$scope.carteleraNueva = {titulo:''};
	$scope.cartel= {titulo:'', cuerpo:'', comentarios:'algo', fecha:'', idCreador:'', idCartelera:''};
	
	$scope.cambiarTitulo=false;
	$scope.tituloViejo='';
	
	todopoderosoDAO.getCarteleras()
			.then(function(data){
				$scope.carteleras = data;
				$scope.carteleraActiva = data[0];
			})
			.catch(function(error){
				notificationService.addNotificacion('Error al buscar carteleras', '', 'danger');
			})
	
	ctl.actualizar = function(id, titulo, cart){
				console.log('llegamos')
				if( confirm("Eliminar anuncio "+ titulo + "?") ){
					todopoderosoDAO.eliminarAnuncio(id)
					.then(function(data){
						todopoderosoDAO.getCarteleras()
						.then(function(data){
							$scope.carteleras = data;
							todopoderosoDAO.getCarteleraById(cart)
							.then(function(data){
								$scope.carteleraActiva = data;
							})
						})
					})
					.catch(function(error){
						console.log("ocurrio un error, ups")
						console.log(error);
					})
				}

			}
	
	$scope.publicarCartelera= function(cartel){
				cartel.fecha= $filter('date')(new Date(), 'yyyy-MM-dd hh:mm:ss');
				cartel.idCreador = userService.getUserData().id;
				cartel.idCartelera = $scope.carteleraActiva.id;
				cartel.comentarios? cartel.comentarios = true : cartel.comentarios = false;
				todopoderosoDAO.createCartel(cartel)
					.then(function(data){
						todopoderosoDAO.getCarteleraById(data['cartelera'].id)
						.then(function(data){
							$scope.carteleraActiva = data;
						})
						.catch(function(error){
							notificationService.addNotificacion('Error al buscar carteleras', '', 'danger');
						})
						cartel.titulo = ''
						cartel.cuerpo = ''
						cartel.comentarios = ''
						notificationService.addNotificacion('Se agrego su anuncio correctamente', '', 'info');
					})
					.catch(function(error){
						console.log(error);
						notificationService.addNotificacion('Error al crear cartel', '', 'danger');
					})
			}
	
	$scope.crearCartelera = function(cartelera){
				todopoderosoDAO.createCartelera(cartelera)
				.then(function(data){
						todopoderosoDAO.getCarteleras()
							.then(function(data){
								$scope.carteleras = data;
								$scope.carteleraActiva = data[data.length - 1];
							})
						notificationService.addNotificacion('Cartelera creada correctamente', '', 'info');
				})
				.catch(function(error){
					console.log(error);
					notificationService.addNotificacion('Error al crear cartelera', '', 'danger');
				})
				cartelera=''
			}

	$scope.cambiarCartelera = function(cartelera){
				$scope.carteleraActiva = cartelera;
				$scope.cambiarTitulo = false;
			}
	
	$scope.logueado = function(){
		return userService.isLogged();
	}
	
	
	$scope.isAdmin= function(){
		return(userService.isLogged() && (userService.isAdmin()));
	}
	
	$scope.eliminarCartelera = function(cartelera){
		if( confirm("Eliminar cartelera "+cartelera.titulo + "?") ){
			todopoderosoDAO.eliminarCartelera(cartelera)
			.then(function(data){
				var index = -1;
				for (var i = 0; i < $scope.carteleras.length; i++) {
					if($scope.carteleras[i].id == cartelera.id){
						index = i;
						break;
					}
				}
				if(index >= 0){
					$scope.carteleras.splice(index,1);
				}
				$scope.carteleraActiva = $scope.carteleras[0]; 
				$scope.cambiarTitulo=false;
				notificationService.addNotificacion('Cartelera eliminada ' + cartelera.titulo, '', 'success');
			},
			function(error){
				notificationService.addNotificacion('Error al cartelera ' + '', '', 'danger');
			});
		}
	}
	
	$scope.editarTitulo = function(){
		$scope.cambiarTitulo = true;
		$scope.tituloViejo=angular.copy($scope.carteleraActiva.titulo);
	}
	
	$scope.cancelarEditar = function(){
		$scope.cambiarTitulo = false;
		$scope.carteleraActiva.titulo = $scope.tituloViejo;
	}
	
	$scope.guardarEditar = function(){
		todopoderosoDAO.editCartelera($scope.carteleraActiva)
		.then(function(data){
			for (var i = 0; i < $scope.carteleras.length; i++) {
				if($scope.carteleras[i].id == data.id){
					$scope.carteleras[i] = data;
					break;
				}
			}
			$scope.carteleraActiva = data;
			$scope.cambiarTitulo=false;
			notificationService.addNotificacion('Titulo de cartelera modificado ' + data.titulo, '', 'success');
		},
		function(error){
			$scope.cancelarEditar();
			notificationService.addNotificacion('Error al modificadar cartelera ' + '', '', 'danger');
		});
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
	
	$scope.publicar = function(){
		return ((userService.getUserData().rol.id == '2') && (userService.getUserData().cartelerasModificar.indexOf($scope.carteleraActiva.id) != -1));
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