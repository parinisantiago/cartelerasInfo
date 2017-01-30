app.factory("todopoderosoDAO",
		['$http', '$q', 
		function($http, $q){
		    var baseRESTurl = "REST/";
		
		    var interfazPublica = {
		    	
				getCarteleras : function(){
					var defered = $q.defer();
			        var promise = defered.promise;
					$http.get(baseRESTurl + "cartelera").then(
						function(respuesta){
							defered.resolve(respuesta.data);
						},
						function(respuesta){
							console.log("Error al cargar los datos de las carteleras.");
							console.log(respuesta);
							alert("Error al cargar los datos de las carteleras.");
							 defered.reject(respuesta);
					    });
					
					return promise;
				  },
				  getCarteleraById : function(id){ 
					  	return $http.get(baseRESTurl + "cartelera/"+id).then(
						function(respuesta){
							return respuesta.data;
						},
						function(respuesta){
							console.log("Error al cargar los datos de la cartelera con id "+id);
							console.log(respuesta);
							alert("Error al cargar los datos de la cartelera con id "+id);
					    	return null;
					    });
				  }
		    }
		    
		    return interfazPublica;
		}]);