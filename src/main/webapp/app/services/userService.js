app.factory("userService",
		['$http', '$q', 'localstorage', 
		function($http, $q, localstorage){
			//necesita <script src="https://kjur.github.io/jsrsasign/jsrsasign-latest-all-min.js"></script>
		    var baseRESTurl = "REST/";
		    var baseProfilePic = "img/perfil/";
		    
		    //segundos a restar a la fecha de expiracion para refrescar el token
		    var secondsToRefresh = 500;
		    
		    var refreshing = false;
		    
		    var getTokenExpiration = function(){
		    	var sJWT = interfazPublica.getToken();
	    		if(sJWT){
	    			var payloadObj = KJUR.jws.JWS.readSafeJSONString(b64utoutf8(sJWT.split(".")[1]));
					var expiration = JSON.parse(payloadObj.exp);
					//por alguna razon javascript lo lee distinto, casi como segundos...
					return expiration*1000;
	    		}
	    		else{
	    			return false; 
	    		}
		    }
		    
		    var sessionExpired = function(){
		    	interfazPublica.logout();
		    }
		    
		    var tokenExpired = function(){
		    	return (getTokenExpiration() <  Date.now() ); 
		    }
		    
		    var tokenCloseToExpired = function(){
		    	return (getTokenExpiration() < ( Date.now()+ (secondsToRefresh*1000) )); 
		    }
		    
		    var tienePermiso = function(permisos, cartelera){
		    	if( permisos == null || cartelera == null){
		    		return false;
		    	}
		    	else{
			    	for(var i = 0; i < permisos.length; i++){
						if(permisos[i] == cartelera.id){
							return true;
						}
					};
					return false;
		    	}
		    }
		    
		    var interfazPublica = {
		    		
		    	isAdmin: function(){
		    		return ( this.isLogged() && this.getUserData().rol.nombre == "Admin" );
		    	},
		    	
		    	isEstudiante: function(){
		    		return ( this.isLogged() && this.getUserData().rol.nombre == "Estudiante" );
		    	},
		    	
		    	isProfesor: function(){
		    		return ( this.isLogged() && this.getUserData().rol.nombre == "Profesor" );
		    	},
		    	
		    	isEmpresa: function(){
		    		return ( this.isLogged() && this.getUserData().rol.nombre == "Empresa" );
		    	},

		    	tienePermisoVer: function(cartelera){
		    		return true;
		    	},
		    	
		    	tienePermisoVerInscriptos: function(cartelera){
		    		return ( this.isLogged() && (this.isProfesor() || this.isAdmin()) );
		    	},
		    	
		    	tienePermisoPublicar: function(cartelera){
		    		return ( this.isLogged() && tienePermiso(this.getUserData().cartelerasModificar, cartelera) );
		    	},
		    	
		    	tienePermisoEliminar: function(cartelera){
		    		return ( this.isLogged() && tienePermiso(this.getUserData().cartelerasEliminar, cartelera) );
		    	},
		    	
		    	isDuenioAnuncio: function(anuncio){
		    		return ( this.isLogged() && (this.getUserData().id == anuncio.id ));
		    	},
		    	
		    	isInteresado: function(cartelera){
		    		var resultado = false;
		    		if(this.isLogged()){
						var user = this.getUserData();
						var interesados = cartelera.interesados;
						for (var int = 0; (int < interesados.length) && !resultado; int++) {
							if(interesados[int].id == user.id){
						    	resultado = true;
						    }
						}
					}
					return resultado;;
		    	},
		    	
		    	getImagenPerfil: function(){
		    		return this.getUserData().profilePic;
		    	},
		    	
		    	getToken: function(){
		    		return localstorage.getItem("token");
	    		},
		    		
		    	getUserData: function(){
		    		var sJWT = this.getToken();
		    		if(sJWT){
		    			this.loginRefresh();
		    			var payloadObj = KJUR.jws.JWS.readSafeJSONString(b64utoutf8(sJWT.split(".")[1]));
						var contenido = JSON.parse(payloadObj.content);
						return contenido;
		    		}
		    		else{
		    			return false;
		    		}
		    	},
		    	
		    	isLogged : function(){
		    		if( this.getToken() && this.getToken().length > 15 ){
		    			return true;
		    		}
		    		else{
		    			return false;
		    		};
		    	},
		    	
				login : function(user, password){
					var defered = $q.defer();
			        var promise = defered.promise;
			        $http({
			        	  method  : 'POST',
			        	  url     : baseRESTurl + "login",
			        	  data    : '{"user":"'+user+'", "password":"'+password+'"}',
			        	  headers : { 'Content-Type': 'application/json' }
			        	 })
					.then(
						function(respuesta){
							localstorage.setItem("token",respuesta.data.token);
							defered.resolve(respuesta.data);
						},
						function(respuesta){
							console.log("Error al loguear usuario.");
							console.log(respuesta);
							defered.reject(respuesta);
					    });
					
					return promise;
				  },
				  
				  logout: function(){
					  return localstorage.removeItem("token");
				  },
				  
				  loginRefresh: function(){
					  if( this.isLogged() ){
						  if( tokenExpired() ){
							  console.log("token expirado");
							  sessionExpired();
							  return false;
						  }
						  else{
							  if( tokenCloseToExpired() & !refreshing ){
								refreshing = true;
							  console.log("actualizando token");
							  $http({
					        	  	method  : 'PUT',
					        	  	url     : baseRESTurl + "login/refresh/"+this.getUserData().id,
					        	  	data    : '',
					        	  	headers : { 'Authorization': this.getToken() }
					         		})
				        	 		.then(
				        	 			function(respuesta){
				        	 				console.log("nuevo token");
				        	 				console.log(respuesta.data.token);
				        	 				localstorage.setItem("token",respuesta.data.token);
				        	 				refreshing = false;
				        	 				return true;
				        	 			},
				        	 			function(respuesta){
				        	 				console.log("Error al actualizar token.");
				        	 				console.log(respuesta);
				        	 				refreshing = false;
				        	 				return false;
				        	 			});
							  }
						  }
					  }
				  },
				  
				  forceLoginRefresh: function(){
					  if( this.isLogged() ){
						  if( tokenExpired() ){
							  console.log("token expirado");
							  sessionExpired();
							  return false;
						  }
						  else{
							  refreshing = true;
							  console.log("actualizando token");
							  $http({
					        	  	method  : 'PUT',
					        	  	url     : baseRESTurl + "login/refresh/"+this.getUserData().id,
					        	  	data    : '',
					        	  	headers : { 'Authorization': this.getToken() }
					         		})
				        	 		.then(
				        	 			function(respuesta){
				        	 				console.log("nuevo token");
				        	 				console.log(respuesta.data.token);
				        	 				localstorage.setItem("token",respuesta.data.token);
				        	 				refreshing = false;
				        	 				return true;
				        	 			},
				        	 			function(respuesta){
				        	 				console.log("Error al actualizar token.");
				        	 				console.log(respuesta);
				        	 				refreshing = false;
				        	 				return false;
				        	 			});
							  
						  }
					  }
				  }
		    }
		    
		    return interfazPublica;
		}]);