app.controller('anuncioController', anuncioController);
anuncioController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function anuncioController($scope, todopoderosoDAO, userService, $http) {
	$scope.expandido=false;
	$scope.expandir = function(){
		$scope.expandido=true;
	}
	$scope.achicar = function(){
		$scope.expandido=false;
	}
	$scope.eliminar = function(id){
		todopoderosoDAO.eliminarAnuncio(id)
		.then(function(data){
			console.log(data)
		})
		.catch(function(error){
			console.log("ocurrio un error, ups")
			console.log(error);
		})
	}
}

app.component("anuncio", {
		controller: 'anuncioController',
		templateUrl: 'app/components/anuncio/anuncio.html',
		bindings: {
			anuncio:'<',
			elim:'<'
		}
});