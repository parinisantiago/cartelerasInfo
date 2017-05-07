app.controller('logoutnavController', logoutnavController);
logoutnavController.$inject = ['userService', '$scope'];

function logoutnavController(userService, $scope) {
		$scope.logout = function(){
			userService.logout();
		}
}

app.component("logoutnav", {
		controller: 'logoutnavController',
		templateUrl: 'app/components/navbar/logoutnav/logoutnav.html',
	
});