(function() {
	
	// Factory for common functions
	angular
		.module("cmad")
		.service('CmadSearch', CmadSearchFunctions);
	
	function CmadSearchFunctions() {
	    var searchdata = {
	        searchQuesData: [{quesId: '', quesTitle: '', quesDesc: '', ansId: '', ansDesc: '', score: ''}],
	        searchAnsData: [{quesId: '', quesTitle: '', quesDesc: '', ansId: '', ansDesc: '', score: ''}]
	    };

	    return {
	        getSearchQuesData: function () {
	            return searchdata.searchQuesData;
	        },
	        getSearchAnsData: function () {
	            return searchdata.searchAnsData;
	        },
	        setSearchQuesData: function (data) {
	            searchdata.searchQuesData = data;
	        },
	        setSearchAnsData: function (data) {
	            searchdata.searchAnsData = data;
	        }
	    };
	}
    
})();