app.controller('carteleraController', carteleraController);
carteleraController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function carteleraController($scope, todopoderosoDAO, userService, $http) {
	var ctl = this
	
	ctl.deleteCartel = function(id, titulo){
		ctl.onUpdate({id:id, titulo:titulo, cart:ctl.cart.id})
	}
	
	ctl.modifyCartel = function(cartel){
		ctl.onModify({cartelmod:cartel, cart:ctl.cart.id})
	}
	
}

app.component("cartelera", {
		controller: 'carteleraController',
		templateUrl: 'app/components/cartelera/cartelera.html',
		bindings: {
			cart:'<',
			busqueda:'<',
			onUpdate: '&',
			onModify: '&'
		}
});