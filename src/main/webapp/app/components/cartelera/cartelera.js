app.controller('carteleraController', carteleraController);
carteleraController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function carteleraController($scope, todopoderosoDAO, userService, $http) {
	var ctl = this
	
	ctl.deleteCartel = function(id, titulo){
		ctl.onUpdate({id:id, titulo:titulo, cart:ctl.cart.id})
	}
	
	$scope.eliminar = function(){
		return userService.getUserData().cartelerasEliminar
	}

}

app.component("cartelera", {
		controller: 'carteleraController',
		templateUrl: 'app/components/cartelera/cartelera.html',
		bindings: {
			cart:'<',
			busqueda:'<',
			onUpdate: '&'
		}
});