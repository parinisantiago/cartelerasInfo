app.controller('logoutnavController', logoutnavController);
logoutnavController.$inject = ['userService', '$scope', 'localstorage'];

function logoutnavController(userService, $scope, localstorage) {
		$scope.logout = function(){
			userService.logout();
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