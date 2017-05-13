app.factory("notificationService",
		[
		function(){
		    
			var notificacion = function notificacion(titulo, cuerpo, tipo, desaparece, tiempo ){
				this.titulo = titulo;
				this.cuerpo = cuerpo;
				this.tipo = typeof tipo == 'string' || typeof tipo != 'undefined'? tipo : 'info';
				this.desaparece = typeof desaparece == 'boolean' ? desaparece : true;
				this.tiempo = typeof tiempo == 'number'? tiempo : 3000;
			};
		    
			var observers = [];
			
			var notificaciones = [];
			
		    var interfazPublica = {
		    	
		    	getNotificaciones : function(){ return notificaciones; },
		    	
		    	addNotificacion : function(titulo, cuerpo, tipo, desaparece, tiempo){
		    		var notif = new notificacion(titulo, cuerpo, tipo, desaparece, tiempo);
		    		notificaciones.push(notif);
		    		angular.forEach(observers, function(value, key) {
		    			  if(value){ value.addUpdate(notif);}
		    			  else{this.removeObserver(value);}
		    			});
		    	},
		    	
		    	removeNotificacion : function(notif){
		    		notificaciones.splice(notificaciones.indexOf(notif),1);
		    		angular.forEach(observers, function(value, key) {
		    			  if(value){ value.removeUpdate(notif);}
		    			  else{this.removeObserver(value);}
		    			});
		    	},
		    	
		    	addObserver : function(observer){
		    		observers.push(observer);
		    	},
		    	
		    	removeObserver : function(observer){
		    		observers.split(observers.indexOf(observer),1);
		    	},
		    	
		    	notify : function(){
		    		angular.forEach(observers, function(value, key) {
		    			  if(value){ value.update();}
		    			  else{this.removeObserver(value);}
		    			});
		    	}
		    }
		    
		    return interfazPublica;
		}]);