app.controller('comentarioController', comentarioController);
comentarioController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function comentarioController($scope, todopoderosoDAO, userService, $http) {
}

app.component("comentario", {
		controller: 'comentarioController',
		templateUrl: 'app/components/comentario/comentario.html',
		bindings: {
			comentarios:'<'
		}
});