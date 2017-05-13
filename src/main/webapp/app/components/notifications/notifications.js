notificationsController.$inject = ['notificationService','$timeout'];
function notificationsController(notificationService,$timeout) {
	var ctrl = this;
	this.notificacion=null;
	this.show = false;
	
	this.timeout = function(notificacion){
		
		var func = function(params){
				if(angular.equals(params[0],params[1].notificacion)){
			  		params[1].nextNotificacion();
				}
	  		};
  		$timeout(func, notificacion.tiempo, true, [notificacion, this]);
	};
	
	this.nextNotificacion = function(){
		notificationService.removeNotificacion(this.notificacion);
		this.notificacion = null;
		this.show = false;
		var fnot = notificationService.getNotificaciones()[0];
		if(fnot != null){
  			this.addNotificacion(fnot);
  		}
	};
	
	this.addUpdate = function(notificacion){
		this.addNotificacion(notificacion);
	};
	
	this.removeUpdate = function(notificacion){
		if(angular.equals(notificacion,ctrl.notificacionActual)){
			this.notificacion = null;
			this.show = false;
		}
	};
	
	this.addNotificacion = function(notif){
		if(this.notificacion == null){
			this.notificacion = notif;
			this.show=true;
			if(this.notificacion.desaparece){
				this.timeout(this.notificacion);
			}
		}
	};
	
	notificationService.addObserver(this);
}

app.component('notifications', {
  templateUrl: 'app/components/notifications/notifications.html',
  controller: notificationsController,
});