app.controller('anuncioController', anuncioController);
anuncioController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http', 'notificationService'];

function anuncioController($scope, todopoderosoDAO, userService, $http, notificationService) {
	var ctrl = this;
	
	$scope.baseImgAnuncioUrl="img/upload/";
	
	$scope.imagenesEliminar = [];
	$scope.files = [];
	$scope.nuevoLink="";
	$scope.linksEliminar = [];
	$scope.linksAgregar = [];

	$scope.cartelVacio = function(){
		return JSON.parse(JSON.stringify({titulo:'', cuerpo:'', comentarioHabilitado:true, creador_id:'', cartelera_id:'', files:[], imagenesEliminar:[], linksAgregar:[], links:[]}));
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
		ctrl.cartel= {titulo:ctrl.anuncio.titulo, cuerpo:ctrl.anuncio.cuerpo, comentarioHabilitado:ctrl.anuncio.comentarioHabilitado, id:ctrl.anuncio.id, fecha:ctrl.anuncio.fecha, creador_id:ctrl.anuncio.creador.id, cartelera_id:'', imagenesEliminar:$scope.imagenesEliminar, files:[],  linksAgregar: $scope.linksAgregar, linksEliminar: $scope.linksEliminar};
		angular.forEach($scope.files, function(value) {
			  this.files.push(value.file);
		},ctrl.cartel);
		ctrl.onModify({cartel:ctrl.cartel})
	}
	$scope.datosModificar = function(){
		ctrl.cartel= {titulo:ctrl.anuncio.titulo, cuerpo:ctrl.anuncio.cuerpo, comentarioHabilitado:ctrl.anuncio.comentarioHabilitado, id:ctrl.anuncio.id, fecha:ctrl.anuncio.fecha, creador_id:ctrl.anuncio.creador.id, cartelera_id:'', files: [], imagenesEliminar:$scope.imagenesEliminar, linksAgregar: $scope.linksAgregar, linksEliminar: $scope.linksEliminar};
		angular.forEach($scope.files, function(value) {
			  this.files.push(value.file);
		},ctrl.cartel);
	}
	
	$scope.agregarLink = function(){
		var regex = new RegExp("^(http|https):\/\/[a-zA-Z]+\..+");
		var msg="Ingrese solo urls que empiecen con http:// o https://";
		if(regex.test($scope.nuevoLink)){
			if($scope.linksAgregar.indexOf($scope.nuevoLink) == -1){
				$scope.linksAgregar.push($scope.nuevoLink);
			}
			$scope.nuevoLink = "";
		}
		else{
			notificationService.addNotificacion('Link no valido', msg, 'danger');
		}
	}
	
	$scope.cancelarAgregarLink = function(link){
		$scope.linksAgregar.splice($scope.linksAgregar.indexOf(link),1);
	}
	
	$scope.cancelarEliminarLink = function(link){
		$scope.linksEliminar.splice($scope.linksEliminar.indexOf(link),1);
		ctrl.anuncio.links.push(link);
	}
	
	$scope.eliminarLink = function(link){
		$scope.linksEliminar.push(link);
		ctrl.anuncio.links.splice(ctrl.anuncio.links.indexOf(link),1);
	}
	
	$scope.permisoEliminar = function(){
		return (userService.tienePermisoEliminar(ctrl.cartelera) || isDuenioAnuncio(ctrl.anuncio));
	}
	
	$scope.permisoModificar = function(){
		return (userService.tienePermisoEliminar(ctrl.cartelera) || isDuenioAnuncio(ctrl.anuncio));
	}
	
}

app.component("anuncio", {
		controller: 'anuncioController',
		templateUrl: 'app/components/anuncio/anuncio.html',
		bindings: {
			anuncio:'<',
			cartelera:'<',
			onDelete:'&',
			onModify:'&'
		}
});