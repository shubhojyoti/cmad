(function() {
	
	angular
	.module("cmad")
	.controller("HeaderController", HeaderControllerFunction)
	.controller("LoginController", LoginControllerFunction)
	.controller("ListQuestionsController", ListQuestionsControllerFunction)
	.controller("IndividualQuestionsController", IndividualQuestionsControllerFunction)
	.controller("AskAQuestionController", AskAQuestionControllerFunction)
	.controller("DashboardController", DashboardControllerFunction)
	.controller("SearchController", SearchControllerFunction); 
	
	
	function HeaderControllerFunction(CmadSearch, CmadFunctions, $http, $log, $q, $localStorage, $window, $scope) {
        $scope.nologin = true;
        $scope.usersName = 'Stranger';
        
        $scope.setUserName = function() {
	        if ((undefined !== $localStorage.cmadToken) && (null !== $localStorage.cmadToken)) {
	            $scope.nologin = false;
	            if ((undefined !== $localStorage.cmadToken.firstName) && (null !== $localStorage.cmadToken.firstName)) {
	                $scope.usersName = $localStorage.cmadToken.firstName;
	            }
	            if ((undefined !== $localStorage.cmadToken.lastName) && (null !== $localStorage.cmadToken.lastName)) {
	                if ('Stranger' !== $scope.usersName) {
	                    $scope.usersName = $scope.usersName + " " + $localStorage.cmadToken.lastName;
	                }
	            }
	        } else {
	            $scope.nologin = true;
	        }
        }
        
        $scope.searchTerms = function() {
        	$window.location.replace("#/search");
        	var quesPromise = CmadFunctions.getSearchResults($scope.searchfield.query, 'questions', $http, $q);
            quesPromise.then(
                    function(response) {
                    	CmadSearch.setSearchQuesData(response.data);
                    }, function(response) {
                        $log.error(response.status);
                    }
            );
            var ansPromise = CmadFunctions.getSearchResults($scope.searchfield.query, 'answers', $http, $q);
            ansPromise.then(
                    function(response) {
                    	CmadSearch.setSearchAnsData(response.data);
                    }, function(response) {
                        $log.error(response.status);
                    }
            );
        }
        
        $scope.setUserName();
	}
	
	
	function LoginControllerFunction(CmadFunctions, $http, $log, $localStorage, $location, $stateParams, $q, $timeout, $scope) {
        $scope.state = $stateParams.state;
        $scope.showError = false;
        $scope.loginError = false;
        $scope.registerError = false;
        $scope.doRegister = false;
        $scope.user = {}

        $scope.loginUser = function() {
            var loginPromise = CmadFunctions.loginUser($scope.user.username, $scope.user.password, $scope, $http, $q, $localStorage, $timeout);
            loginPromise.then(
                    function(response) {
                    	$location.path("#/questions");
                    }, function(response) {
                        $log.error(response.status);
                    }
            );
        }
        
        $scope.logoutUser = function() {
        	var currToken = $localStorage.cmadToken.token;
            var url = 'services/usersession/' + currToken;
            var logoutPromise = CmadFunctions.logoutUser(url, $localStorage, $http, $q);
            logoutPromise.then(
                    function(response) {
                        $location.path("#/questions");
                    }, function(response) {
                        $log.error(response.status);
                        $location.path("#/questions");
                    }
            );
        }

        $scope.registerUser = function() {
        	var serviceurl = 'services/users';
            var registerPromise = CmadFunctions.postRequest(serviceurl, $scope.user, $http, $q);
            registerPromise.then(
                    function(response) {
                        $scope.loginUser();
                    }, function(response) {
                        $log.error(response.status);
                        $scope.showError = true;
                        $scope.registerError = true;
                    }
            );
        }
        
        if ("login" === $scope.state) {
            $scope.doRegister = false
        } else if ("register" === $scope.state) {
            $scope.doRegister = true
        } else if ("logout" === $scope.state) {
            $scope.logoutUser();
        }

    }

	
	function ListQuestionsControllerFunction(CmadFunctions, $http, $log, $q, $location, $localStorage, $window, $scope) {
        $scope.tab = 1;
        $scope.currentkey = 'createDate';

        $scope.displayQuestions = function(key, direction) {
            if ("desc" === direction) {
                direc = true;
            } else {
                direc = false;
            }
            var url = 'services/questions';
            var quesPromise = CmadFunctions.getRequest(url, $http, $q);
            quesPromise.then(
                    function(response) {
                        var newdata = CmadFunctions.sortData(response.data, key, direc);
                        $scope.questions = response.data;
                    }, function(response) {
                        $log.error(response.status);
                    }
            );
        }
        
        $scope.updateVotes = function(service, op, id) {
        	var currtoken = '';
        	if ((undefined !== $localStorage.cmadToken) && (null !== $localStorage.cmadToken)) {
        		currtoken = $localStorage.cmadToken.token;
	            var userIdPromise = CmadFunctions.getUserIdFromToken(currtoken, $http, $q);
	            userIdPromise.then(function(userId) {
	            	var url = 'services/'+service+'/'+id+'/votes?op='+op;
	            	var votePromise = CmadFunctions.putRequest(url, "", $http, $q);
	            	votePromise.then(function() {
	            		$scope.displayQuestions($scope.currentkey, 'desc');
	            	});
	            });
        	} else {
        		$window.location.replace('#/user/login');
        	}
        }
        
        $scope.displayQuestions('createDate', 'desc');
	}
	
	
	function IndividualQuestionsControllerFunction(CmadFunctions, $http, $log, $scope, $location, $localStorage, $q, $stateParams, $window) {
	    	$scope.tab = 1;
	    	$scope.submittedAnswer = false;
	        $scope.quesId = $stateParams.quesId;
	        
	        $scope.showAnswer = function() {
	        	$scope.submittedAnswer = false;
	        	var url = 'services/answers?qId='+$scope.quesId;
	        	var ansPromise = CmadFunctions.getRequest(url, $http, $q);
	            ansPromise.then(
	                    function(response) {
	                        $scope.answers = response.data;
	                    }, function(response) {
	                        $log.error(response.status);
	                    }
	            );
	        }
	        
	        $scope.showQuestion = function() {
	        	var url = 'services/questions/'+$scope.quesId;
	        	var quesPromise = CmadFunctions.getRequest(url, $http, $q);
	            quesPromise.then(
	                    function(response) {
	                        $scope.question = response.data[0];
	                        $scope.showAnswer();
	                    }, function(response) {
	                        $log.error(response.status);
	                    }
	            );
	        }
	        
	        $scope.submitAnswer = function() {
	            $scope.submittedAnswer = false;
	            if ((undefined !== $localStorage.cmadToken) && (null !== $localStorage.cmadToken)) {
		            var currtoken = $localStorage.cmadToken.token;
		            var curremail = $localStorage.cmadEmail.email;
		            var userIdPromise = CmadFunctions.getUserIdFromToken(currtoken, $http, $q);
		            userIdPromise.then(function(userId) {
		            	var url = 'services/answers/?uid='+userId+'&qid='+$scope.quesId;
		                var ansPromise = CmadFunctions.postRequest(url, $scope.answ, $http, $q);
		                ansPromise.then(
		                        function(response) {
		                            $scope.answ.ansDesc = "";
		                            $scope.submittedAnswer = true;
		                            $scope.showQuestion();
		                        }, function(response) {
		                            $log.error(response.status);
		                        }
		                );
		                
		                if (undefined !== $scope.answ1 && "YES" === $scope.answ1.notifyme) {
		                	var url = 'services/questions/'+$scope.quesId+'/subscribe?email='+curremail;
			                var subscribePromise = CmadFunctions.putRequest(url, $scope.answ, $http, $q);
			                subscribePromise.then(
			                		function(response) {
			                			$log.info("Subscribed");
			                		}, function(response) {
			                			$log.error(response.status);
			                });
		                }
		
		            });
		            
	            } else {
	            	$window.location.replace('#/user/login');
	            }
	        }
	        
	        $scope.updateVotes = function(service, op, id) {
	        	if ((undefined !== $localStorage.cmadToken) && (null !== $localStorage.cmadToken)) {
		        	var currtoken = $localStorage.cmadToken.token;
		            var userIdPromise = CmadFunctions.getUserIdFromToken(currtoken, $http, $q);
		            userIdPromise.then(function(userId) {
		            	var url = 'services/'+service+'/'+id+'/votes?op='+op;
		            	var votePromise = CmadFunctions.putRequest(url, "", $http, $q);
		            	votePromise.then(function() {
		            		$scope.showQuestion();
		            	});
		            });
	        	} else {
	        		$window.location.replace('#/user/login');
	        	}
	        }
	        
	        $scope.showQuestion();
	        var viewurl = 'services/questions/'+$scope.quesId+'/views?op=add';  
	        var addViewPromise = CmadFunctions.putRequest(viewurl, "", $http, $q);
	}
	
	
	function AskAQuestionControllerFunction(CmadFunctions, $http, $log, $q, $localStorage, $window, $scope) {	
        $scope.submitQuestion = function() {
            $scope.submittedAnswer = false;
            if ((undefined !== $localStorage.cmadToken) && (null !== $localStorage.cmadToken)) {
	            var currtoken = $localStorage.cmadToken.token;
	            var userIdPromise = CmadFunctions.getUserIdFromToken(currtoken, $http, $q);
	            userIdPromise.then(function(userId) {
	            	var url = 'services/questions/' + userId;
	                var ansPromise = CmadFunctions.postRequest(url, $scope.ques, $http, $q);
	                ansPromise.then(
	                        function(response) {
	                            $scope.ques.quesTitle = "";
	                            $scope.ques.quesDesc = "";
	                            $scope.submittedAnswer = true;
	                        }, function(response) {
	                            $log.error(response.status);
	                        }
	                );
	
	            });
            } else {
            	$window.location.replace('#/user/login');
            }
        }      
	}
	
	
	function DashboardControllerFunction(CmadFunctions, $http, $log, $q, $localStorage, $window, $scope) {
    	$scope.tab = 1;
    	$scope.submittedProfile = false;
    	if ((undefined !== $localStorage.cmadToken) && (null !== $localStorage.cmadToken)) {
	        var currtoken = $localStorage.cmadToken.token;
	        var userIdPromise = CmadFunctions.getUserIdFromToken(currtoken, $http, $q);
	        userIdPromise.then(function(userId) {
	        	var url = 'services/users/'+userId;
	        	var profPromise = CmadFunctions.getRequest(url, $http, $q);
	            profPromise.then(
	                    function(response) {
	                        $scope.user = response.data[0];
	                    }, function(response) {
	                        $log.error(response.status);
	                    }
	            );
	
	        });
        
	        $scope.updateProfile = function() {
	        	var userIdPromise = CmadFunctions.getUserIdFromToken(currtoken, $http, $q);
	            userIdPromise.then(function(userId) {
	            	var newdata = {"username": $scope.user.username, "password": $scope.user.password, "firstname": $scope.user.firstname, "lastname": $scope.user.lastname};
	            	var url = 'services/users/' + userId;
	   	        	var profUpdPromise = CmadFunctions.putRequest(url, newdata, $http, $q);
		        	profUpdPromise.then(
		                    function(response) {
		                        $scope.submittedProfile = true;
		                    }, function(response) {
		                        $log.error(response.status);
		                    }
		            );
	            });
	        };
        
	        $scope.getUserQuestions = function() {
	        	var userIdPromise = CmadFunctions.getUserIdFromToken(currtoken, $http, $q);
	            userIdPromise.then(function(userId) {
	            	var url = 'services/questions?uid='+userId;
	            	var profPromise = CmadFunctions.getRequest(url, $http, $q);
	                profPromise.then(
	                        function(response) {
	                            $scope.usrquestions = response.data;
	                        }, function(response) {
	                            $log.error(response.status);
	                        }
	                );
	            });
	        };
        
	        $scope.getUserAnswers = function() {
	        	var userIdPromise = CmadFunctions.getUserIdFromToken(currtoken, $http, $q);
	            userIdPromise.then(function(userId) {
	            	var url = 'services/answers?uId='+userId;
	            	var profPromise = CmadFunctions.getRequest(url, $http, $q);
	                profPromise.then(
	                        function(response) {
	                            $scope.usranswers = response.data;
	                        }, function(response) {
	                            $log.error(response.status);
	                        }
	                );
	            });
	        };
    	} else {
    		$window.location.replace('#/user/login');
    	}
	
	}
	
	function SearchControllerFunction(CmadSearch, $scope) {
    	$scope.tab = 1;
    	
    	$scope.$watch(
    			function () { 
    				return CmadSearch.getSearchQuesData(); 
    			}, 
    			function (newValue, oldValue) {
    				if (newValue !== oldValue) $scope.usrsearchquesresponses = newValue;
    			}
    	);
    	
    	$scope.$watch(
    			function () { 
    				return CmadSearch.getSearchAnsData(); 
    			}, 
    			function (newValue, oldValue) {
    				if (newValue !== oldValue) $scope.usrsearchansresponses = newValue;
    			}
    	);
	}

	
})();