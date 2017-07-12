app.controller('logoutnavController', logoutnavController);
logoutnavController.$inject = ['userService', '$scope', 'localstorage', '$state'];

function logoutnavController(userService, $scope, localstorage, $state) {
	$scope.logueadoOHome = function(){
		var logueado = userService.isLogged();
		if(!logueado){
			$state.go("home");
		}
		return logueado;
	}
	
	$scope.form={
		user : '',
		password: ''
	}; 
	$scope.form.password='';
	$scope.usuario= userService.getUserData();
	$scope.logout = function(){
		userService.logout();
		$state.go('home');
	}
	$scope.administrar = function(){
		return userService.isAdmin();
	}
}

app.component("logoutnav", {
		controller: 'logoutnavController',
		templateUrl: 'app/components/navbar/logoutnav/logoutnav.html',
	
});