(function() {
	
	// Service for common functions
	angular
		.module("cmad")
		.service('CmadFunctions', CmadServices);
	
	function CmadServices() {
		
        // Get User ID given the Session Token
        this.getUserIdFromToken = function(token, $http, $q) {
            var userPromise = $q.defer();
            var retval = null;
            var usrPromise = $http.get('services/usersession', {
                headers: {'Authorization': 'UserToken ' + btoa(token)}
            });
            usrPromise.then(
                    function(response) {
                        console.log("Inside Service: " + response.data);
                        return userPromise.resolve(response.data[0]);
                    }, function(response) {
                        console.log(response);
                        return userPromise.reject(response);
                    }
            );
            return userPromise.promise;
        };
        
        
        // Sort Data Objects based on the given key
        this.sortData = function(arr, key, reverse) {
            if ((undefined === reverse) && (null === reverse)) {
                reverse = false;
            }
            sorted = arr.sort(function(a,b){
                  var akey = a[key], bkey = b[key];
                  return akey > bkey ? 1 : akey < bkey ? -1 : 0;
                 });
            if (reverse === true) {
                return sorted.reverse();
            } else {
                return sorted;
            }
        };

        
        // Get Request to a Service
        this.getRequest = function(url, $http, $q) {
        	var gPromise = $q.defer();
        	var getPromise = $http.get(url);
        	getPromise.then(
        			function(response) {
        				return gPromise.resolve(response);
        			}, function(response) {
        				return gPromise.reject(response);
        			}
        	);
        	return gPromise.promise;
        };
        
        
        // Post Request to a Service
        this.postRequest = function(url, data, $http, $q) {
        	var pPromise = $q.defer();
        	var postPromise = $http.post(url, data);
        	postPromise.then(
        			function(response) {
        				return pPromise.resolve(response);
        			}, function(response) {
        				return pPromise.reject(response);
        			}
        	);
        	return pPromise.promise;
        };
        
        // Put Request to a Service
        this.putRequest = function(url, data, $http, $q) {
        	var pPromise = $q.defer();
        	var putPromise;
        	if (data !== "") {
        		putPromise = $http.put(url, data);
        	} else {
        		putPromise = $http.put(url);
        	}
        	putPromise.then(
        			function(response) {
        				return pPromise.resolve(response);
        			}, function(response) {
        				return pPromise.reject(response);
        			}
        	);
        	return pPromise.promise;
        };
        
        
        //Search Terms in Question and Answer Collections
        this.getSearchResults = function(terms, collection, $http, $q) {
        	var sPromise = $q.defer();
        	var searchPromise = $http.get('services/search/'+collection+'?terms=%27'+terms+'%27');
        	searchPromise.then(
        			function(response) {
        				return sPromise.resolve(response);
        			}, function(response) {
        				return sPromise.reject(response);
        			}
        	);
        	return sPromise.promise;
        };
        
        
        //Login User
        this.loginUser = function(username, password, $scope, $http, $q, $localStorage, $timeout) {
        	var lPromise = $q.defer();
        	var loginPromise = $http.get('services/users', {
                headers: {'Authorization': 'Basic ' + btoa(username + ':' + password)}
            });
        	loginPromise.then(
                    function(response) {
                        if ((undefined !== response.data[0]) && (null !== response.data[0])) {
                            var currToken = {"token": response.data[0].token, "firstName": response.data[0].firstname, "lastName": response.data[0].lastname}
                            var currEmail = {"email": response.data[0].username};
                            $localStorage.cmadToken = currToken;
                            $localStorage.cmadEmail = currEmail;
                            return lPromise.resolve(currToken);
                        } else {
                            $scope.showError = true;
                            $scope.loginError = true;
                            $timeout(function () {
                                $scope.showError = false;
                                $scope.logError = false;
                            }, 3000);
                            return lPromise.reject(response);
                        }
                    }, function(response) { 
                        $scope.showError = true;
                        $scope.loginError = true;
                        $timeout(function () {
                            $scope.showError = false;
                            $scope.logError = false;
                        }, 3000);
                        return lPromise.reject(response);
                    }
            );
        	return lPromise.promise;
        };
        
        
        //Logout User
        this.logoutUser = function(url, $localStorage, $http, $q) {
        	var lPromise = $q.defer();
        	var logoutPromise = $http.delete(url);
            logoutPromise.then(
                    function(response) {
                        delete $localStorage.cmadToken;
                        delete $localStorage.cmadEmail;
                        lPromise.resolve(response);
                    }, function(response) {
                        delete $localStorage.cmadToken;
                        delete $localStorage.cmadEmail;
                        lPromise.reject(response);
                    }
            );
            return lPromise.promise;
        };

	}
    
})();