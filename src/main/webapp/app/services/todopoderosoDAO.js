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
					        	data    : '{"titulo":"'+cartelera+'"}',
					        	headers : { 'Authorization' : userService.getToken() }
					        })
					        .then(
									function(respuesta){
										defered.resolve(respuesta.data);
									},
									function(respuesta){
										console.log("Error al crear cartelera");
										console.log(respuesta);
										defered.reject(respuesta);
								    });
								
								return promise;
					  },
					  createCartel: function(cartel){
						  var defered = $q.defer();
						  var promise = defered.promise;
						  $http({
							  method	: 'POST',
							  url		: baseRESTurl + "anuncio",
							  data		: '{"titulo":"'+cartel.titulo+'","cuerpo":"'+cartel.cuerpo+'","comentarioHabilitado":"'+cartel.comentarios+'","creador_id":"'+cartel.idCreador+'","cartelera_id":"'+cartel.idCartelera+'"}',
							  headers	: {'Authorization': userService.getToken()}
						  })
						  .then(
							function(respuesta){
								defered.resolve(respuesta.data);
							},
							function(respuesta){
								console.log("Error al crear el anuncio ");
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
						  var usuarioJSON = {
						  			user : usuario.user,
						  			rol_id : usuario.rol.id,
						  			cartelerasModificar_id : [],
						  			cartelerasEliminar_id : [],
						  	}
						  	for (var int = 0; int < usuario.cartelerasModificar.length; int++) {
								usuarioJSON.cartelerasModificar_id.push(usuario.cartelerasModificar[int].id);
							}
						  	for (var int = 0; int < usuario.cartelerasEliminar.length; int++) {
								usuarioJSON.cartelerasEliminar_id.push(usuario.cartelerasEliminar[int].id);
							}
						  	if(usuario.password != null){
					        	usuarioJSON.password = usuario.password;
					        }
						  	
						  	var defered = $q.defer();
					        var promise = defered.promise;
					        $http({
					        	  method  : 'PUT',
					        	  url     : baseRESTurl + "usuario/" + usuario.id,
					        	  data    : angular.toJson(usuarioJSON),
					        	  headers : { 'Authorization' : userService.getToken() }
					        	 })
					        .then(
								function(respuesta){
									defered.resolve(respuesta.data);
								},
								function(respuesta){
									console.log("Error al modificar usuario");
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
						  createComentario: function(usuario, texto, anuncio){
							  	var defered = $q.defer();
						        var promise = defered.promise;
						        $http({
						        	  method  : 'POST',
						        	  url     : baseRESTurl + "comentario",
						        	  data    : '{ "texto":"'+texto+'"'+', "creador_id":"'+usuario.id+'"'+ ', "anuncio_id":"'+ anuncio.id +'"}',
						        	  headers : { 'Authorization' : userService.getToken() }
						        	 })
						        .then(
									function(respuesta){
										defered.resolve(respuesta.data);
									},
									function(respuesta){
										console.log("Error al crear comentario ");
										console.log(respuesta);
										defered.reject(respuesta);
								    });
								
								return promise;
						  },
						  
						  getAnunciosUsuario: function(usuario){
								var defered = $q.defer();
						        var promise = defered.promise;
								$http.get(baseRESTurl + "/anuncio/usuario/"+usuario.id).then(
									function(respuesta){
										defered.resolve(respuesta.data);
									},
									function(respuesta){
										console.log("Error al cargar los anuncios del usuario.");
										console.log(respuesta);
										defered.reject(respuesta);
								    });
								
								return promise;
						  },
		    }
		    
		    return interfazPublica;
		}]);