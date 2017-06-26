app.controller('logoutnavController', logoutnavController);
logoutnavController.$inject = ['userService', '$scope', 'localstorage'];

function logoutnavController(userService, $scope, localstorage) {
	
		$scope.form={
			user : '',
			password: ''
		}; 
		$scope.form.password='';
		$scope.usuario= userService.getUserData();
		$scope.logout = function(){
			userService.logout();
			localstorage.setSessionItem('body', 'publicaciones');
		}
		$scope.publicaciones = function(){
			localstorage.setSessionItem('body', 'publicaciones');
		}
		$scope.abmUsuario = function(){
			localstorage.setSessionItem('body', 'abmUsuario');
		}
		$scope.configuracion = function(){
			localstorage.setSessionItem('body', 'configuracion');
		}
		$scope.misAnuncios = function(){
			localstorage.setSessionItem('body', 'misAnuncios');
		}
		$scope.administrar = function(){
			return (userService.getUserData().rol.id == '1')
		}
}

app.component("logoutnav", {
		controller: 'logoutnavController',
		templateUrl: 'app/components/navbar/logoutnav/logoutnav.html',
	
});