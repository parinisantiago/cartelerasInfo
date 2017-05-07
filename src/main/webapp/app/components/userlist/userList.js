UserListController.$inject = ['todopoderosoDAO'];
function UserListController(todopoderosoDAO) {
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
		  console.log(data);
		})
		.catch(function(error){
			console.log(error);
			alert(error);
		})
  }
  
  ctrl.modificarUsuario = function(usuario){
	  ctrl.action='modificar'
	  todopoderosoDAO.editUsuario(usuario)
	  .then(function(data){
		  console.log(data);
		})
		.catch(function(error){
			console.log(error);
			alert(error);
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
			})
			.catch(function(error){
				console.log(error);
				alert(error);
			})
	  }
  }
  
  todopoderosoDAO.getRoles()
	.then(function(data){
		ctrl.roles = data;
	})
	.catch(function(error){
		console.log(error);
	});
  
  todopoderosoDAO.getUsuarios()
	.then(function(data){
		ctrl.usuarios = data;
	})
	.catch(function(error){
		console.log(error);
	});
}

app.component('userList', {
  templateUrl: 'app/components/userlist/userList.html',
  controller: UserListController,
});