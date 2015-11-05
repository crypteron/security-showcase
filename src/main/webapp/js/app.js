angular.module(
  'showcase',
  [ 'ui.router', 'ngResource', 'ngAnimate', 'mgcrea.ngStrap', 'angular-loading-bar', 'errorHandler',
    'showcase.controllers', 'showcase.services' ]).config(function($stateProvider, $urlRouterProvider) {
  $stateProvider.state('patients', {
    abstract: true,
    url: '/patients',
    template: '<ui-view/>',
    resolve: {
      type: function() {
        return 'Java Secure';
      },
      addState: function() {
        return 'patients.newPatient';
      },
      editState: function() {
        return 'patients.editPatient';
      },
      listState: function() {
        return 'patients.list';
      }
    }
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
    controller: 'PatientCreateController as editPatient',
    resolve: {
      newPatient: function(Patients) {
        return new Patients();
      }
    }
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
  }).state('insecurePatients', {
    abstract: true,
    url: '/insecurePatients',
    template: '<ui-view/>',
    resolve: {
      type: function() {
        return 'Java Insecure';
      },
      addState: function() {
        return 'insecurePatients.newPatient';
      },
      editState: function() {
        return 'insecurePatients.editPatient';
      },
      listState: function() {
        return 'insecurePatients.list';
      }
    }
  }).state('insecurePatients.list', {
    url: '',
    templateUrl: 'app/patients/patients.list.tpl.html',
    controller: 'PatientListController as patientList',
    resolve: {
      patients: function(InsecurePatients) {
        return InsecurePatients.query().$promise;
      }
    }
  }).state('insecurePatients.newPatient', {
    url: '/new',
    templateUrl: 'app/patients/patients.add.edit.tpl.html',
    controller: 'PatientCreateController as editPatient',
    resolve: {
      newPatient: function(InsecurePatients) {
        return new InsecurePatients();
      }
    }
  }).state('insecurePatients.editPatient', {
    url: '/:id/edit',
    templateUrl: 'app/patients/patients.add.edit.tpl.html',
    controller: 'PatientEditController as editPatient',
    resolve: {
      patient: function(InsecurePatients, $stateParams) {
        return InsecurePatients.get({
          id: $stateParams.id
        }).$promise;
      }
    }
  });
  $urlRouterProvider.otherwise('/insecurePatients');
});
