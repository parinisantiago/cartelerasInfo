app.controller('carteleraController', carteleraController);
carteleraController.$inject = ['$scope', 'todopoderosoDAO', 'userService', '$http'];

function carteleraController($scope, todopoderosoDAO, userService, $http) {
}

app.component("cartelera", {
		controller: 'carteleraController',
		templateUrl: 'app/components/cartelera/cartelera.html',
		bindings: {
			cart:'<'
		}
});