app.controller('carteleraController', carteleraController);
carteleraController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function carteleraController($scope, todopoderosoDAO, userService, $http) {
	var ctl = this
	
	ctl.deleteCartel = function(id, titulo){
		if( confirm("Eliminar anuncio "+ titulo + "?") ){
			todopoderosoDAO.eliminarAnuncio(id)
			.then(function(data){
				todopoderosoDAO.getCarteleraById(ctl.cart.id)
				.then(function(data){
					ctl.cart = data
				})
			})
			.catch(function(error){
				console.log("ocurrio un error, ups")
				console.log(error);
			})
		}
	}
	
	$scope.eliminar = function(){
		return userService.getUserData().cartelerasEliminar
	}

}

app.component("cartelera", {
		controller: 'carteleraController',
		templateUrl: 'app/components/cartelera/cartelera.html',
		bindings: {
			cart:'<'
		}
});