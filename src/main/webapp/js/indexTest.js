
var app = angular.module('cartelerasInfo', []);

app.controller('cartelerasCtl',function($scope, $http) {
	
	$scope.carteleraActiva = null;
	$scope.carteleras = null;
    
    var cargarDatos = $http.get("REST/cartelera").then(
    						function(respuesta){
    							$scope.carteleras = respuesta.data;
    							$scope.carteleraActiva = respuesta.data[0];
    							console.log($scope.carteleras);
						    },
						    function(respuesta){
						    	console.log("error al cargar los datos");
						    	console.log(respuesta);
						    	alert("Error al cargar los datos.");
						    });
    
});

/*
		$http.post("REST/login",login).then(function(data){
			console.log("true");
			controller.isUser = true;
		},function(data){
			controller.isUser = false;
			console.log("false");
		})
*/