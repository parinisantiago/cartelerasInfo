app.factory("userService",
		['$http', '$q', 'localstorage', 
		function($http, $q, localstorage){
			//necesita <script src="https://kjur.github.io/jsrsasign/jsrsasign-latest-all-min.js"></script>
		    var baseRESTurl = "REST/";
		    
		    //segundos a restar a la fecha de expiracion para refrescar el token
		    var secondsToRefresh = 500;
		    
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
		    	alert("sesion expirada, por favor vuelva a loguearse");
		    	interfazPublica.logout();
		    }
		    
		    var tokenExpired = function(){
		    	return (getTokenExpiration() <  Date.now() ); 
		    }
		    
		    var tokenCloseToExpired = function(){
		    	return (getTokenExpiration() < ( Date.now()+ (secondsToRefresh*1000) )); 
		    }
		    
		    var interfazPublica = {
		    		
		    	isAdmin: function(){
		    		return ( this.issLogged() && this.getToken().rol.nombre == "Admin" );
		    	},
		    	
		    	isEstudiante: function(){
		    		return ( this.issLogged() && this.getToken().rol.nombre == "Estudiante" );
		    	},
		    	
		    	isProfesor: function(){
		    		return ( this.issLogged() && this.getToken().rol.nombre == "Profesor" );
		    	},
		    	
		    	isEmpresa: function(){
		    		return ( this.issLogged() && this.getToken().rol.nombre == "Empresa" );
		    	},
		    	
		    	getToken: function(){
		    		return localstorage.getItem("token");
	    		},
		    		
		    	getUserData: function(){
		    		var sJWT = this.getToken();
		    		if(sJWT){
		    			var payloadObj = KJUR.jws.JWS.readSafeJSONString(b64utoutf8(sJWT.split(".")[1]));
						var contenido = JSON.parse(payloadObj.content);
						this.loginRefresh();
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
							console.log(respuesta);
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
						  }
						  else{
							  if( tokenCloseToExpired() ){
							  console.log("actualizando token");
							  $http({
					        	  	method  : 'PUT',
					        	  	url     : baseRESTurl + "login/refresh/"+this.getUserData().userID,
					        	  	data    : '',
					        	  	headers : { 'Authorization': this.getToken() }
					         		})
				        	 		.then(
				        	 			function(respuesta){
				        	 				console.log("nuevo token");
				        	 				console.log(respuesta.data.token);
				        	 				localstorage.setItem("token",respuesta.data.token);
				        	 				return true;
				        	 			},
				        	 			function(respuesta){
				        	 				console.log("Error al actualizar token.");
				        	 				console.log(respuesta);
				        	 				return false;
				        	 			});
							  }
						  }
					  }
				  }
		    }
		    
		    return interfazPublica;
		}]);