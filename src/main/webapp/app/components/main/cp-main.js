app.controller('mainController', mainController);
mainController.$inject = ['userService', 'localstorage'];

function mainController(userService, localstorage) {
	var ctl = this;
	
	ctl.isLogged = function(){
		return userService.isLogged();
	};
	
	ctl.isNotLogged = function(){
		return !userService.isLogged();
	}
}

app.component("cpMain", {
		transclude: true,
		controller: 'mainController',
		templateUrl: 'app/components/main/main.html',
		bindings:{
			loggin: '&'
		}
});