app.controller('anuncioController', anuncioController);
anuncioController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function anuncioController($scope, todopoderosoDAO, userService, $http) {
	var ctrl = this;
	
	$scope.baseImgAnuncioUrl="img/upload/";
	
	$scope.imagenesEliminar = [];
	$scope.files = [];

	$scope.cartelVacio = function(){
		return JSON.parse(JSON.stringify({titulo:'', cuerpo:'', comentarioHabilitado:true, creador_id:'', cartelera_id:'', files:[], imagenesEliminar:[]}));
	};
	ctrl.cartel = new $scope.cartelVacio;
	
	$scope.addImages = function(files){
		$scope.files = files;
	}
	
	$scope.removeImagen = function(nombre){
		$scope.imagenesEliminar.push(nombre) ;
	}
	
	$scope.cancelRemoveImagen = function(nombre){
		$scope.imagenesEliminar.splice($scope.imagenesEliminar.indexOf(nombre),1);
	} 
	
	$scope.imagenesSinBorrar = function(item){
		return $scope.imagenesEliminar.indexOf(item) == -1;
	}
	
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
		ctrl.cartel= {titulo:ctrl.anuncio.titulo, cuerpo:ctrl.anuncio.cuerpo, comentarioHabilitado:ctrl.anuncio.comentarioHabilitado, id:ctrl.anuncio.id, fecha:ctrl.anuncio.fecha, creador_id:ctrl.anuncio.creador.id, cartelera_id:'', files: $scope.files, imagenesEliminar:$scope.imagenesEliminar};
		ctrl.onModify({cartel:ctrl.cartel})
	}
	$scope.datosModificar = function(){
		ctrl.cartel= {titulo:ctrl.anuncio.titulo, cuerpo:ctrl.anuncio.cuerpo, comentarioHabilitado:ctrl.anuncio.comentarioHabilitado, id:ctrl.anuncio.id, fecha:ctrl.anuncio.fecha, creador_id:ctrl.anuncio.creador.id, cartelera_id:'', files: $scope.files, imagenesEliminar:$scope.imagenesEliminar};
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