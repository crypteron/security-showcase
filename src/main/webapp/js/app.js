angular.module('techShowcase', [ 'ui.router', 'ngResource', 'techShowcase.controllers', 'techShowcase.services' ]);

angular.module('techShowcase').config(function($stateProvider, $httpProvider) {
  $stateProvider.state('patients', {
    url : '/patients',
    templateUrl : 'app/patients/patients.list.tpl.html',
    controller : 'PatientListController'
  }).state('newPatient', {
    url : '/patients/new',
    templateUrl : 'app/patients/patients.add.edit.tpl.html',
    controller : 'PatientCreateController'
  }).state('editPatient', {
    url : '/patients/:id/edit',
    templateUrl : 'app/patients/patients.add.edit.tpl.html',
    controller : 'PatientEditController'
  });
}).run(function($state) {
  $state.go('patients');
});
