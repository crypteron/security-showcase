angular.module('errorHandler', [ 'mgcrea.ngStrap' ]).factory('errorInterceptor', function($q, $injector) {
  return {
    responseError: function(rejection) {
      $injector.invoke(function($alert) {
        $alert({
          title: 'An unknown error has occured',
          type: 'danger',
          placement: 'top',
          animation: 'am-fade-and-slide-top'
        });
      });
      return $q.reject(rejection);
    }
  };
}).config(function($httpProvider) {
  $httpProvider.interceptors.push('errorInterceptor');
});
