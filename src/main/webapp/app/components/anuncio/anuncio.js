app.controller('anuncioController', anuncioController);
anuncioController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function anuncioController($scope, todopoderosoDAO, userService, $http) {
}

app.component("anuncio", {
		controller: 'anuncioController',
		templateUrl: 'app/components/anuncio/anuncio.html',
		bindings: {
			anuncio:'<',
			elim:'<'
		}
});