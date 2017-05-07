app.controller('mainController', mainController);
mainController.$inject = ['userService'];

function mainController(userService) {
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