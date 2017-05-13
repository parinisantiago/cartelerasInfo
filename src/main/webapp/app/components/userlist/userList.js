UserListController.$inject = ['todopoderosoDAO', 'notificationService'];
function UserListController(todopoderosoDAO, notificationService) {
  var ctrl = this;
  ctrl.roles = [];
  ctrl.usuarios=[];
  var usuarioVacio = { id:'',user:'',rol:''};
  ctrl.usuarioActual = usuarioVacio;
  
  ctrl.setUsuarioActual = function(user){
	  ctrl.usuarioActual = user;
  }
  
  ctrl.vaciarUsuarioActual = function(){
	  ctrl.usuarioActual = usuarioVacio;
  }
  
  ctrl.crearUsuario = function(usuario){
	  console.log(usuario);
	  todopoderosoDAO.createUsuario(usuario)
	  .then(function(data){
		  ctrl.usuarios.push(data);
		  notificationService.addNotificacion('Usuario creado correctamente', 'Nombre: '+data.user, 'success');
		})
		.catch(function(error){
			notificationService.addNotificacion('Error al crear usuario', error.data, 'danger');
		})
  }
  
  ctrl.modificarUsuario = function(usuario){
	  ctrl.action='modificar'
	  todopoderosoDAO.editUsuario(usuario)
	  .then(function(data){
		  notificationService.addNotificacion('Usuario modificado correctamente', 'Nombre: '+data.user, 'success');
		})
		.catch(function(error){
			notificationService.addNotificacion('Error al modificar usuario', error.data, 'danger');
		})
  }
  
  ctrl.eliminarUsuario = function(usuario){
	  if(confirm("Eliminar usuario "+usuario.user+"?")){
		  todopoderosoDAO.removeUsuario(usuario)
		  .then(function(data){
			  if(ctrl.usuarioActual == usuario){
				  ctrl.usuarioActual = usuarioVacio;
			  }
			  ctrl.usuarios.splice(ctrl.usuarios.indexOf(usuario) ,1);
			  notificationService.addNotificacion('Usuario eliminado correctamente', 'Nombre: '+usuario.user, 'success');
			})
			.catch(function(error){
				notificationService.addNotificacion('Error al eliminar usuario', error.data, 'danger');
			})
	  }
  }
  
  todopoderosoDAO.getRoles()
	.then(function(data){
		ctrl.roles = data;
	})
	.catch(function(error){
		notificationService.addNotificacion('Error al buscar roles', error.data, 'danger');
	});
  
  todopoderosoDAO.getUsuarios()
	.then(function(data){
		ctrl.usuarios = data;
	})
	.catch(function(error){
		notificationService.addNotificacion('Error al buscar usuarios', error.data, 'danger');
	});
}

app.component('userList', {
  templateUrl: 'app/components/userlist/userList.html',
  controller: UserListController,
});