app.config(['$stateProvider', 'userServiceProvider', function($stateProvider, userServiceProvider) {
	
	
	/** esta funcion no anda, la idea era hacerla andar en los estados con onEnter para que chequee
	 * si está logueado antes de cambiar de estado
	 * pero la parte del state.go no quiere andar
	 */
	/*
	var soloLogueados = function($stateProvider, userServiceProvider) {
		if (! userServiceProvider.$get().isLogged()) {
			//$stateProvider.$get().go('home');
			console.log($stateProvider.$get());
			console.log("entra");
		}
		else{
			console.log("no entra");
		}
	}
	*/

	var homeState = {
		name: 'home',
		url: '/home',
		template: '<list-cartelera></list-cartelera>'
	}

	var carteleraState = {
		name: 'cartelera',
		url: '/cartelera',
		template: '<list-cartelera></list-cartelera>'
	}

	var administrarState = {
		name: 'administrar',
		url: '/administrar',
		template: '<user-list></user-list>',
	}

	var misPublicacionesState = {
		name: 'misPublicaciones',
		url: '/misPublicaciones',
		template: '<mis-anuncios></mis-Anuncios>',
	}

	var configuracionState = {
		name: 'configuracion',
		url: '/configuracion',
		template: '<user-config></user-config>',
	}

	$stateProvider.state(homeState);
	$stateProvider.state(carteleraState);
	$stateProvider.state(administrarState);
	$stateProvider.state(misPublicacionesState);
	$stateProvider.state(configuracionState);
}]);
