angular.module('techShowcase', [ 'ui.router', 'ngResource', 'techShowcase.controllers', 'techShowcase.services' ])
  .config(function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('patients', {
      abstract: true,
      url: '/patients',
      template: '<ui-view/>'
    }).state('patients.list', {
      url: '',
      templateUrl: 'app/patients/patients.list.tpl.html',
      controller: 'PatientListController as patientList',
      resolve: {
        patients: function(Patients) {
          return Patients.query().$promise;
        }
      }
    }).state('patients.newPatient', {
      url: '/new',
      templateUrl: 'app/patients/patients.add.edit.tpl.html',
      controller: 'PatientCreateController as editPatient'
    }).state('patients.editPatient', {
      url: '/:id/edit',
      templateUrl: 'app/patients/patients.add.edit.tpl.html',
      controller: 'PatientEditController as editPatient',
      resolve: {
        patient: function(Patients, $stateParams) {
          return Patients.get({
            id: $stateParams.id
          }).$promise;
        }
      }
    });
    $urlRouterProvider.otherwise('/patients');
  });
