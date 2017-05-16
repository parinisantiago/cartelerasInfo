app.factory("todopoderosoDAO",
		['$http', '$q', 'userService',
		function($http, $q, userService){
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
				  },
				  addInteres : function(cartelera){
						var defered = $q.defer();
				        var promise = defered.promise;
				        $http({
				        	  method  : 'PUT',
				        	  url     : baseRESTurl + "cartelera/" + cartelera.id + "/interes",
				        	  data    : '',
				        	  headers : { 'Authorization' : userService.getToken() }
				        	 })
				        .then(
							function(respuesta){
								defered.resolve(respuesta.data);
							},
							function(respuesta){
								console.log("Error al registrar interes en la cartelera "+cartelera.nombre);
								console.log(respuesta);
								alert("Error al registrar interes en la cartelera "+cartelera.nombre);
								defered.reject(respuesta);
						    });
						
						return promise;
					  },
					  removeInteres : function(cartelera){
							var defered = $q.defer();
					        var promise = defered.promise;
					        $http({
					        	  method  : 'DELETE',
					        	  url     : baseRESTurl + "cartelera/" + cartelera.id + "/interes",
					        	  data    : '',
					        	  headers : { 'Authorization' : userService.getToken() }
					        	 })
					        .then(
								function(respuesta){
									defered.resolve(respuesta.data);
								},
								function(respuesta){
									console.log("Error al quitar interes en la cartelera "+cartelera.nombre);
									console.log(respuesta);
									alert("Error al quitar interes en la cartelera "+cartelera.nombre);
									defered.reject(respuesta);
							    });
							
							return promise;
					  },
					  
					  getUsuarios : function(){
							var defered = $q.defer();
					        var promise = defered.promise;
							$http.get(baseRESTurl + "usuario").then(
								function(respuesta){
									defered.resolve(respuesta.data);
								},
								function(respuesta){
									console.log("Error al cargar los datos de los usuarios.");
									console.log(respuesta);
									defered.reject(respuesta);
							    });
							
							return promise;
					  },
					  createCartelera: function(cartelera){
						  	var defered = $q.defer();
					        var promise = defered.promise;
					        $http({
					        	method	: 'POST',
					        	url		: baseRESTurl + "cartelera",
					        	data    : '{"titulo":"'+cartelera.titulo+'"}',
					        	headers : { 'Authorization' : userService.getToken() }
					        })
					        .then(
									function(respuesta){
										defered.resolve(respuesta.data);
									},
									function(respuesta){
										console.log("Error al crear usuario ");
										console.log(respuesta);
										defered.reject(respuesta);
								    });
								
								return promise;
					  },
					  createUsuario : function(usuario){
						  	var defered = $q.defer();
					        var promise = defered.promise;
					        if(usuario.password != null){
					        	passString=',"password":"'+usuario.password+'"';
					        }
					        else{
					        	passString=',"password":"'+usuario.user+'"';
					        }
					        $http({
					        	  method  : 'POST',
					        	  url     : baseRESTurl + "usuario",
					        	  data    : '{ "user":"'+usuario.user+'"'+passString+',"rol_id":"'+usuario.rol.id+'"}',
					        	  headers : { 'Authorization' : userService.getToken() }
					        	 })
					        .then(
								function(respuesta){
									defered.resolve(respuesta.data);
								},
								function(respuesta){
									console.log("Error al crear usuario ");
									console.log(respuesta);
									defered.reject(respuesta);
							    });
							
							return promise;
					  },
					  
					  editUsuario : function(usuario){
						  	var defered = $q.defer();
					        var promise = defered.promise;
					        var passString = '';
					        if(usuario.password != null){
					        	passString=',"password":"'+usuario.password+'"';
					        }
					        $http({
					        	  method  : 'PUT',
					        	  url     : baseRESTurl + "usuario/" + usuario.id,
					        	  data    : '{ "user":"'+usuario.user+'"' + passString+',"rol_id":"'+usuario.rol.id+'"}',
					        	  headers : { 'Authorization' : userService.getToken() }
					        	 })
					        .then(
								function(respuesta){
									defered.resolve(respuesta.data);
								},
								function(respuesta){
									console.log("Error al editar usuario");
									console.log(respuesta);
									defered.reject(respuesta);
							    });
							
							return promise;
					  },
					  
					  removeUsuario : function(usuario){
						  	var defered = $q.defer();
					        var promise = defered.promise;
					        $http({
					        	  method  : 'DELETE',
					        	  url     : baseRESTurl + "usuario/" + usuario.id,
					        	  data    : '',
					        	  headers : { 'Authorization' : userService.getToken() }
					        	 })
					        .then(
								function(respuesta){
									defered.resolve(respuesta.data);
								},
								function(respuesta){
									console.log("Error al eliminar usuario");
									console.log(respuesta);
									defered.reject(respuesta);
							    });
							
							return promise;
					  },
					  
					  getRoles : function(){
							var defered = $q.defer();
					        var promise = defered.promise;
							$http.get(baseRESTurl + "rol").then(
								function(respuesta){
									defered.resolve(respuesta.data);
								},
								function(respuesta){
									console.log("Error al cargar los datos de los roles.");
									console.log(respuesta);
									alert("Error al cargar los datos de los roles.");
									defered.reject(respuesta);
							    });
							
							return promise;
						  },
						  
						  getNotificaciones : function(usuario){
							  	var defered = $q.defer();
						        var promise = defered.promise;
						        $http.get(baseRESTurl + "notificacion/user/" + usuario.id).then(
										function(respuesta){
											defered.resolve(respuesta.data);
										},
										function(respuesta){
											console.log("Error al cargar los datos de las notificaciones del usuario "+usuario.user);
											console.log(respuesta);
											defered.reject(respuesta);
									    });
								
								return promise;
						  },
						  
						  removeNotificacion: function(notificacion){
							  	var defered = $q.defer();
						        var promise = defered.promise;
						        $http({
						        	  method  : 'DELETE',
						        	  url     : baseRESTurl + "notificacion/" + notificacion.id,
						        	  data    : '',
						        	  headers : { 'Authorization' : userService.getToken() }
						        	 })
						        .then(
									function(respuesta){
										defered.resolve(respuesta.data);
									},
									function(respuesta){
										console.log("Error al eliminar notificacion");
										console.log(respuesta);
										defered.reject(respuesta);
								    });
								
								return promise;
						  },
		    }
		    
		    return interfazPublica;
		}]);