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
  
  var usuarioVacio = { id:'',user:'',rol:'',cartelerasModificar:[],cartelerasEliminar:[]};
  $scope.usuarioActual = usuarioVacio;
  
  $scope.setUsuarioActual = function(user){
	  $scope.usuarioActual = user;
  }
  
  $scope.vaciarUsuarioActual = function(){
	  $scope.usuarioActual = usuarioVacio;
  }
  
  $scope.crearUsuario = function(usuario){
	  if(usuario!= null && usuario.user.length>0){
		  todopoderosoDAO.createUsuario(usuario)
		  .then(function(data){
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
			  notificationService.addNotificacion('Usuario modificado correctamente', 'Nombre: '+data.user, 'success');
		  })
		  .catch(function(error){
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
  
  $scope.cambiarPermisoEliminar = function(usuario, cartelera){
	  if( $scope.tienePermisoEliminar(usuario,cartelera)){
		  $scope.eliminarPermisoEliminar(usuario,cartelera);
	  }
	  else{
		  $scope.crearPermisoEliminar(usuario,cartelera);
	  }
  }
  
  $scope.cambiarPermisoPublicar = function(usuario, cartelera){
	  console.log("enviando requerimiento");
	  if( $scope.tienePermisoPublicar(usuario,cartelera)){
		  $scope.eliminarPermisoPublicar(usuario,cartelera);
	  }
	  else{
		  $scope.crearPermisoPublicar(usuario,cartelera);
	  }
  }
  
  $scope.crearPermisoPublicar= function(usuario, cartelera){
	  todopoderosoDAO.addPermisoPublicar(usuario, cartelera)
	  .then(function(data){
		  $scope.usuarios[$scope.usuarios.indexOf(usuario)]=data;
		  notificationService.addNotificacion('Permiso agregado correctamente', 'Publicar en cartelera '+cartelera.titulo, 'success');
		},function(error){
			notificationService.addNotificacion('Error al agregar permiso','Publicar en cartelera' , 'danger');
		})
  }
  
  $scope.crearPermisoEliminar = function(usuario, cartelera){
	  todopoderosoDAO.addPermisoEliminar(usuario, cartelera)
	  .then(function(data){
		  $scope.usuarios[$scope.usuarios.indexOf(usuario)]=data;
		  notificationService.addNotificacion('Permiso agregado correctamente', 'Eliminar en cartelera '+cartelera.titulo, 'success');
		},function(error){
			notificationService.addNotificacion('Error al agregar permiso','Eliminar en cartelera' , 'danger');
		})
  }
  
  $scope.eliminarPermisoPublicar = function(usuario, cartelera){
	  todopoderosoDAO.removePermisoPublicar(usuario, cartelera)
	  .then(function(data){
		  $scope.usuarios[$scope.usuarios.indexOf(usuario)]=data;
		  notificationService.addNotificacion('Permiso eliminado correctamente', 'Publicar en cartelera '+cartelera.titulo, 'success');
		},function(error){
			notificationService.addNotificacion('Error al eliminar permiso','Publicar en cartelera' , 'danger');
		})
  }
  
  $scope.eliminarPermisoEliminar = function(usuario, cartelera){
	  todopoderosoDAO.removePermisoEliminar(usuario, cartelera)
	  .then(function(data){
		  $scope.usuarios[$scope.usuarios.indexOf(usuario)]=data;
		  notificationService.addNotificacion('Permiso eliminado correctamente', 'Eliminar en cartelera '+cartelera.titulo, 'success');
		},function(error){
			notificationService.addNotificacion('Error al eliminar permiso','Eliminar en cartelera' , 'danger');
		})
  }
  
  
  todopoderosoDAO.getRoles()
	.then(function(data){
		$scope.roles = data;
	})
	.catch(function(error){
		notificationService.addNotificacion('Error al buscar roles', error.data, 'danger');
	});
  
  todopoderosoDAO.getUsuariosPermisos()
	.then(function(data){
		$scope.usuarios = data;
	})
	.catch(function(error){
		notificationService.addNotificacion('Error al buscar usuarios', error.data, 'danger');
	});
}

app.component('userList', {
  templateUrl: 'app/components/userlist/userList.html',
  controller: UserListController,
});