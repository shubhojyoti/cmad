(function() {

	// Define module URL Routing
	angular
	.module("cmad.routing", ['ui.router'])
	.config(cmadRouting)
	
	// URL Routing Function
    function cmadRouting($stateProvider, $urlRouterProvider) {
    	$urlRouterProvider.otherwise('/questions');
        $stateProvider.state('questions', {
                url: '/questions',
                templateUrl: 'partials/listquestions.html'
            }).state('ques', {
                url: '/ques/{quesId}',
                templateUrl: 'partials/question.html'
            }).state('user', {
                url: '/user/{state}',
                templateUrl: 'partials/users.html'
            }).state('askaquestion', {
                url: '/askaquestion',
                templateUrl: 'partials/askaquestion.html'
            }).state('search', {
                url: '/search',
                templateUrl: 'partials/search.html'
            }).state('dashboard', {
                url: '/dashboard',
                templateUrl: 'partials/dashboard.html'
            }
        )
    }

})();