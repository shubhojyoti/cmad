(function() {

	// Define module, URL Routing, State and Directive
    angular
    	.module("cmad", ['cmad.routing', 'ngStorage', 'ngAnimate', 'ngSanitize'])
    	.directive('cmadheader', cmadHeaderDirective);
    
    // CMAD Header Directive
    function cmadHeaderDirective() {
    	var directive = {};
		directive.restrict = 'E';
		directive.templateUrl = 'partials/header.html';
		return directive;
    }

})();
