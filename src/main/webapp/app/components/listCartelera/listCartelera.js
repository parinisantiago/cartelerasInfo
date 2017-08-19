app.controller('listCarteleraController', listCarteleraController);
listCarteleraController.$inject = ['$scope', 'todopoderosoDAO', 'userService', 'notificationService', '$http', '$filter'];

function listCarteleraController($scope, todopoderosoDAO, userService, notificationService, $http, $filter) {
	ctl = this;
	
	
	$scope.carteleras = null;
	$scope.carteleraActiva = null;
	$scope.carteleraNueva = {titulo:''};
	$scope.files = [];
	$scope.nuevoLink = "";
	
	$scope.cartelVacio = function(){
		return JSON.parse(JSON.stringify({titulo:'', cuerpo:'', comentarioHabilitado:true, creador_id:'', cartelera_id:'', files:[], imagenesEliminar:[], linksAgregar:[]}));
	}
	
	$scope.cartel = new $scope.cartelVacio;
	
	
	$scope.addImages = function(files){
		$scope.files = files;
	}
	
	/*
	$scope.addImages = function(files, event, flow){
		console.log($scope.cartel.flow);
		var reader = new FileReader();
		for (var int = 0; int < $scope.cartel.flow.files.length; int++) {
			
			
			var fd=new FormData();
			fd.append('file',$scope.cartel.flow.files[int].file);
			console.log(fd);
			//$scope.cartel.imagenes.push(reader.readAsDataURL($scope.cartel.flow.files[int].file)) ;
		}
		console.log($scope.cartel);
	}
	*/
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
	
	ctl.modifyCartel= function(cartelmod, cart){
				console.log(cartelmod);
				if( confirm("Modificar anuncio "+ cartelmod.titulo + "?") ){
					todopoderosoDAO.modificarAnuncio(cartelmod, cart)
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
	
	ctl.actualizar = function(id, titulo, cart){
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
				cartel.creador_id = userService.getUserData().id;
				cartel.cartelera_id = $scope.carteleraActiva.id;
				cartel.comentarioHabilitado? cartel.comentarioHabilitado = true : cartel.comentarioHabilitado = false;
				angular.forEach($scope.files, function(value) {
					  this.files.push(value.file);
				},cartel);
				todopoderosoDAO.createCartel(cartel)
					.then(function(data){
						$scope.cartel = new $scope.cartelVacio;
						todopoderosoDAO.getCarteleraById(data['cartelera'].id)
						.then(function(data){
							$scope.carteleraActiva = data;
						})
						.catch(function(error){
							notificationService.addNotificacion('Error al buscar carteleras', '', 'danger');
						})
						cartel.titulo = '';
						cartel.cuerpo = '';
						cartel.comentarios = true;
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
		return ((userService.isProfesor) && $scope.carteleraActiva && (userService.getUserData().cartelerasModificar.indexOf($scope.carteleraActiva.id) != -1));
	}
	
	$scope.removeInteres = function(){
		todopoderosoDAO.removeInteres($scope.carteleraActiva)
		.then(function(data){
			$scope.carteleraActiva = data;
			notificationService.addNotificacion('Usted se ha desuscripto de ' + data.titulo, '', 'success');
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
				    if(usuario.id == user.id){
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
	
	$scope.puedeVerInscriptos = function(){
		return userService.tienePermisoVerInscriptos();
	}
	

	$scope.agregarLink = function(){
		var regex = new RegExp("^(http|https):\/\/[a-zA-Z]+\..+");
		var msg="Ingrese solo urls que empiecen con http:// o https://";
		if(regex.test($scope.nuevoLink)){
			if($scope.cartel.linksAgregar.indexOf($scope.nuevoLink) == -1){
				$scope.cartel.linksAgregar.push($scope.nuevoLink);
			}
			$scope.nuevoLink = "";
		}
		else{
			notificationService.addNotificacion('Link no valido', msg, 'danger');
		}
	}
	
	$scope.cancelarAgregarLink = function(link){
		$scope.cartel.linksAgregar.splice($scope.cartel.linksAgregar.indexOf(link),1);
	}
	
}

app.component("listCartelera", {
		controller: 'listCarteleraController',
		templateUrl: 'app/components/listCartelera/listCartelera.html',
	
});