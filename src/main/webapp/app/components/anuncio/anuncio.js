app.controller('anuncioController', anuncioController);
anuncioController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function anuncioController($scope, todopoderosoDAO, userService, $http) {
	var ctrl = this;

	ctrl.cartel= {titulo:"", cuerpo:'', comentarios:true, fecha:'', idCreador:'', idCartelera:''};
	$scope.expandido=false;
	$scope.expandir = function(){
		$scope.expandido=true;
	}
	$scope.achicar = function(){
		$scope.expandido=false;
	}
	$scope.eliminar = function(anuncio){
		ctrl.onDelete({id:anuncio.id, titulo:anuncio.titulo});
	}
	$scope.modificarCartelera = function(cartel){
		ctrl.onModify({cartel:ctrl.cartel})
	}
	$scope.datosModificar = function(){
		ctrl.cartel= {titulo:ctrl.anuncio.titulo, cuerpo:ctrl.anuncio.cuerpo, comentarios:ctrl.anuncio.comentarioHabilitado, id:ctrl.anuncio.id, fecha:ctrl.anuncio.fecha, idCreador:ctrl.anuncio.creador.id, idCartelera:''};

	}
}

app.component("anuncio", {
		controller: 'anuncioController',
		templateUrl: 'app/components/anuncio/anuncio.html',
		bindings: {
			anuncio:'<',
			elim:'<',
			onDelete:'&',
			onModify:'&'
		}
});