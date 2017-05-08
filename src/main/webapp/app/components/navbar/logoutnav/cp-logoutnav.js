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
}

app.component("logoutnav", {
		controller: 'logoutnavController',
		templateUrl: 'app/components/navbar/logoutnav/logoutnav.html',
	
});