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
	$scope.eliminar = function(anuncio){
		console.log(anuncio);
		if( confirm("Eliminar anuncio "+ anuncio.titulo + "?") ){
			todopoderosoDAO.eliminarAnuncio(anuncio.id)
			.then(function(data){
				console.log(data)
			})
			.catch(function(error){
				console.log("ocurrio un error, ups")
				console.log(error);
			})
		}
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