angular.module('techShowcase', [ 'ui.router', 'ngResource', 'techShowcase.controllers', 'techShowcase.services' ])
.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider.state('patients', {
    url : '/patients',
    templateUrl : 'app/patients/patients.list.tpl.html',
    controller : 'PatientListController',
      resolve: {
          patients: function(Patients){
              return Patients.query().$promise;
          }
      }
  }).state('patients.newPatient', {
    url : '/new',
    templateUrl : 'app/patients/patients.add.edit.tpl.html',
    controller : 'PatientCreateController'
  }).state('patients.editPatient', {
    url : '/:id/edit',
    templateUrl : 'app/patients/patients.add.edit.tpl.html',
    controller : 'PatientEditController',
      resolve: {
          patient: function(Patients, $stateParams){
              return Patients.get({
                  id : $stateParams.id
              }).$promise;
          }
      }
  });
  $urlRouterProvider.otherwise('/patients');
});
