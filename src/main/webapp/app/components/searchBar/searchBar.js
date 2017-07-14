app.controller('searchBarController', searchBarController);
searchBarController.$inject = ['$scope'];

function searchBarController($scope) {
	var ctrl = this;
	ctrl.config = {
			id: 'search-bar-1',
			placeholder:'Buscar'
	}
	$scope.change = function(){
		ctrl.search = ctrl.data;
	}
}

app.component("searchBar", {
		controller: 'searchBarController',
		templateUrl: 'app/components/searchBar/searchBar.html',
		bindings: {
			search:'=',
			classes:'@',
			placeholder:'@',
			id:'@'
		}
});