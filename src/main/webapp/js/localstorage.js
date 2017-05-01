app.factory("localstorage",
		[
		function(){
		    var baseRESTurl = "REST/";
		    
		    var interfazPublica = {
				  
				  setItem: function(name, data){
					// Check browser support
					  if (typeof(Storage) !== "undefined") {
					      localStorage.setItem(name, data);
					      return true;
					  } else {
					      return false;
					  }
				  },
				  
				  getItem: function(name){
						// Check browser support
						  if (typeof(Storage) !== "undefined") {
						      return localStorage.getItem(name);
						  } else {
						      return false;
						  }
				  },
				  
				  removeItem: function(name){
						// Check browser support
						  if (typeof(Storage) !== "undefined") {
						      return localStorage.removeItem(name);
						  } else {
						      return false;
						  }
				  },
				  
				  setSessionItem: function(name, data){
						// Check browser support
						  if (typeof(Storage) !== "undefined") {
							  sessionStorage.setItem(name, data);
						      return true;
						  } else {
						      return false;
						  }
					  },
					  
				  getSessionItem: function(name){
						// Check browser support
						  if (typeof(Storage) !== "undefined") {
							  return sessionStorage.getItem(name);
						  } else {
						      return false;
						  }
				  },
				  
				  removeSessionItem: function(name){
						// Check browser support
						  if (typeof(Storage) !== "undefined") {
						      return sessionStorage.removeItem(name);
						  } else {
						      return false;
						  }
				  },
		    }
		    
		    return interfazPublica;
		}]);