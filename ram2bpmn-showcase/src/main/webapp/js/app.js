window.alertProxy = window.alert;
window.alert = function(message){
	throw message;
};

angular.module('app', [])
.controller('RamBpmnCtrl', RamBpmnCtrl)
.controller('RASCICtrl', RASCICtrl)
.filter('toArray', function() {
  return function(items) {
    var filtered = [];
    angular.forEach(items, function(item) {
      filtered.push(item);
    });
   return filtered;
  };
});






