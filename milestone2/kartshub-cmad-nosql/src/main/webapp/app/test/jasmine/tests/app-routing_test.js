
describe("Testing URL Routing", function() {
	
	function mockTemplate(templateRoute, tmpl) {
		$templateCache.put(templateRoute, tmpl || templateRoute);
	}

	function goTo(url) {
		console.log("GOTO HERE");
		$location.url(url);
		$rootScope.$digest();
	}

	function goFrom(url) {
		return {toState: function (state, params) {
			$location.replace().url(url); //Don't actually trigger a reload
			$state.go(state, params);
			$rootScope.$digest();
		}};
	}
	
	beforeEach(module('cmad.routing'));	
	beforeEach(module('stateMock'));
	
	var $controller, $rootScope, $state, $location, $mockTemplate, $goTo, $goFrom;
	
    beforeEach(inject(function(_$controller_, _$rootScope_, $injector, _$state_, _$location_, _$templateCache_) {
        $controller = _$controller_;
        $rootScope = _$rootScope_;
        $state = _$state_;
        $location = _$location_;
        $templateCache = _$templateCache_;
        $mockTemplate = mockTemplate();
        $goTo = goTo();
        $goFrom = goFrom();
    }));
    
    describe('When URL is empty', function () {
        it('should go to the questions page', function () {
        	$state.expectTransitionTo("questions");
        	$state.go("questions");
        	expect($state.current.name).toEqual('questions');
        	$state.ensureAllTransitionsHappened();
        });
    });
    
    describe('When URL is questions', function () {
        it('should go to the questions page', function () {
          	$state.expectTransitionTo("questions");
          	$state.go("questions");
        	expect($state.current.name).toEqual('questions');
        	$state.ensureAllTransitionsHappened();
        });
    });
    
    describe('When URL is Ask a Question', function () {
        it('should go to the askaquestion page', function () {
          	$state.expectTransitionTo("askaquestion");
          	$state.go("askaquestion");
        	expect($state.current.name).toEqual('askaquestion');
        	$state.ensureAllTransitionsHappened();
        });
    });
    
})