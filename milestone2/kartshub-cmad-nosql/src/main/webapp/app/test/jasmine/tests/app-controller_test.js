
describe("Testing Controllers", function() {

    beforeEach(module('cmad'));

    var $controller, $rootScope;
    beforeEach(inject(function(_$controller_, _$rootScope_, $injector) {
        $controller = _$controller_;
        $rootScope = _$rootScope_;
        $localStorage = $injector.get('$localStorage');
    }));

    describe('HeaderController', function() {
        var scope, controller, cmadsearch, cmadfunctions, http, log, q, window;

        beforeEach(inject(function(_CmadSearch_, _CmadFunctions_, _$http_, _$log_, _$q_, _$window_) {
        	cmadsearch = _CmadSearch_;
        	cmadfunctions = _CmadFunctions_;
        	http = _$http_;
        	log = _$log_;
        	q = _$q_;
        	window = _$window_;
        }));
        
        describe('When user John Doe logs in', function() {
	        beforeEach(function() {
	          scope = $rootScope.$new();
	          $localStorage.cmadToken = {"token": "SomeRandomToken", "firstName": "John", "lastName": "Doe"};
	          controller = $controller('HeaderController', { $scope: scope,
	        	  											 $localStorage: $localStorage,
	        	  											 $http: http,
	        	  											 $log: log,
	        	  											 $q: q,
	        	  											 $window: window,
	        	  											 CmadSearch: cmadsearch,
	        	  											 CmadFunctions: cmadfunctions});
	        });
        
	        afterEach(function() {
	        	delete $localStorage.cmadToken;
	        });
        	
	        it('User\'s Name should show as John Doe in the navigation bar', function() {
	        	expect(scope.usersName).toBe("John Doe");
	        });
	        
	        it('The logged in flag should be set to true', function() {
	        	expect(scope.nologin).toBe(false);
	        });
	        
	       
        });
        
        describe('When no user is logged in', function() {
        	
        	beforeEach(function() {
  	          scope = $rootScope.$new();
  	          controller = $controller('HeaderController', { $scope: scope,
  	        	  											 $localStorage: $localStorage,
  	        	  											 $http: http,
  	        	  											 $log: log,
  	        	  											 $q: q,
  	        	  											 $window: window,
  	        	  											 CmadSearch: cmadsearch,
  	        	  											 CmadFunctions: cmadfunctions});
  	        });
        	
	        it('User\'s Name should show as Stranger in the navigation bar', function() {
	        	expect(scope.usersName).toBe("Stranger");
	        });
	        
	        it('The logged in flag should be set to false', function() {
	        	expect(scope.nologin).toBe(true);
	        });
	        
        });
        

    });

});
