UserListController.$inject = ['todopoderosoDAO', 'notificationService', '$scope'];
function UserListController(todopoderosoDAO, notificationService, $scope) {
	$scope.roles = [];
	$scope.usuarios=[];
	$scope.carteleras=[];

	todopoderosoDAO.getCarteleras()
	.then(function(data){
		$scope.carteleras = data;
	})
	.catch(function(error){
		notificationService.addNotificacion('Error al buscar carteleras', '', 'danger');
	})
	
	todopoderosoDAO.getRoles()
	.then(function(data){
		$scope.roles = data;
	})
	.catch(function(error){
		notificationService.addNotificacion('Error al buscar roles', error.data, 'danger');
	});

	todopoderosoDAO.getUsuarios()
	.then(function(data){
		$scope.usuarios = data;
	})
	.catch(function(error){
		notificationService.addNotificacion('Error al buscar usuarios', error.data, 'danger');
	});

	var usuarioVacio = { id:'',user:'',rol:'',cartelerasModificar:[],cartelerasEliminar:[]};
	$scope.usuarioActual = usuarioVacio;
	$scope.usuarioOriginal = usuarioVacio;
	
	$scope.setUsuarioActual = function(user){
		$scope.usuarioActual = angular.copy(user);
		$scope.usuarioOriginal = user;
	}

	$scope.vaciarUsuarioActual = function(){
		$scope.usuarioActual = usuarioVacio;
		$scope.usuarioOriginal = usuarioVacio; 
	}

	$scope.crearUsuario = function(usuario){
		if(usuario!= null && usuario.user.length>0){
			todopoderosoDAO.createUsuario(usuario)
			.then(function(data){
				data.cartelerasModificar = [];
				data.cartelerasEliminar = [];
				$scope.usuarios.push(data);
				notificationService.addNotificacion('Usuario creado correctamente', 'Nombre: '+data.user, 'success');
			})
			.catch(function(error){
				notificationService.addNotificacion('Error al crear usuario', '', 'danger');
			})
		}
		else{
			notificationService.addNotificacion('Falta completar datos del usuario', '', 'danger');
		}
	}

	$scope.modificarUsuario = function(usuario){
		$scope.action='modificar';
		if(usuario!= null && usuario.id>0){
			todopoderosoDAO.editUsuario(usuario)
			.then(function(data){
				console.log($scope.usuarios);
				console.log(data);
				console.log($scope.usuarios.indexOf($scope.usuarioOriginal));
				
				$scope.usuarios[$scope.usuarios.indexOf($scope.usuarioOriginal)] = data;
				notificationService.addNotificacion('Usuario modificado correctamente', 'Nombre: '+data.user, 'success');
			},function(error){
				notificationService.addNotificacion('Error al modificar usuario', error.data, 'danger');
			})
		}

	}

	$scope.eliminarUsuario = function(usuario){
		if(confirm("Eliminar usuario "+usuario.user+"?")){
			todopoderosoDAO.removeUsuario(usuario)
			.then(function(data){
				if($scope.usuarioActual == usuario){
					$scope.usuarioActual = usuarioVacio;
				}
				$scope.usuarios.splice($scope.usuarios.indexOf(usuario) ,1);
				notificationService.addNotificacion('Usuario eliminado correctamente', 'Nombre: '+usuario.user, 'success');
			})
			.catch(function(error){
				notificationService.addNotificacion('Error al eliminar usuario', error.data, 'danger');
			})
		}
	}

	$scope.tienePermiso = function(permisos, cartelera){
		for(var i = 0; i < permisos.length; i++){
			if(permisos[i].id == cartelera.id){
				return true;
			}
		};
		return false;
	}

	$scope.tienePermisoPublicar = function (usuario, cartelera){
		if( usuario != null && cartelera != null){
			return $scope.tienePermiso(usuario.cartelerasModificar, cartelera);
		}
		else{
			return false;
		}

	}

	$scope.tienePermisoEliminar = function (usuario, cartelera){
		if( usuario != null && cartelera != null){
			return $scope.tienePermiso(usuario.cartelerasEliminar, cartelera);
		}
		else{
			return false;
		}
	}

	$scope.cambiarPermisoEliminar= function(usuario, cartelera){
		if( $scope.tienePermisoEliminar(usuario,cartelera)){
			usuario.cartelerasEliminar.splice(usuario.cartelerasEliminar.indexOf(cartelera),1);
		}
		else{
			usuario.cartelerasEliminar.push(cartelera);
		}
	}
	
	$scope.cambiarPermisoPublicar = function(usuario, cartelera){
		if( $scope.tienePermisoPublicar(usuario,cartelera)){
			usuario.cartelerasModificar.splice(usuario.cartelerasModificar.indexOf(cartelera),1);
		}
		else{
			usuario.cartelerasModificar.push(cartelera);
		}
	}


}

app.component('userList', {
	templateUrl: 'app/components/userlist/userList.html',
	controller: UserListController,
});