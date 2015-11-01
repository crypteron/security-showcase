angular.module('techShowcase.services', []).factory('Patients', function($resource) {
  return $resource('patients/:id', {
    id: '@id'
  }, {
    update: {
      method: 'PUT'
    }
  });
}).service('popupService', function($window) {
  this.showPopup = function(message) {
    return $window.confirm(message);
  }
});
