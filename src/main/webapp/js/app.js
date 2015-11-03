angular.module('showcase', [ 'ui.router', 'ngResource', 'showcase.controllers', 'showcase.services' ]).config(
  function($stateProvider, $urlRouterProvider) {
    $stateProvider.state('patients', {
      abstract: true,
      url: '/patients',
      template: '<ui-view/>',
      resolve: {
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
        title: function() {
          return 'All Patients Java Secure';
        },
        patients: function(Patients) {
          return Patients.query().$promise;
        }
      }
    }).state('patients.newPatient', {
      url: '/new',
      templateUrl: 'app/patients/patients.add.edit.tpl.html',
      controller: 'PatientCreateController as editPatient',
      resolve: {
        title: function() {
          return 'Add Patient Java Secure';
        },
        newPatient: function(Patients) {
          return new Patients();
        }
      }
    }).state('patients.editPatient', {
      url: '/:id/edit',
      templateUrl: 'app/patients/patients.add.edit.tpl.html',
      controller: 'PatientEditController as editPatient',
      resolve: {
        title: function() {
          return 'Edit Patient Java Secure';
        },
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
        title: function() {
          return 'All Patients Java Insecure';
        },
        patients: function(InsecurePatients) {
          return InsecurePatients.query().$promise;
        }
      }
    }).state('insecurePatients.newPatient', {
      url: '/new',
      templateUrl: 'app/patients/patients.add.edit.tpl.html',
      controller: 'PatientCreateController as editPatient',
      resolve: {
        title: function() {
          return 'Add Patient Java Insecure';
        },
        newPatient: function(InsecurePatients) {
          return new InsecurePatients();
        }
      }
    }).state('insecurePatients.editPatient', {
      url: '/:id/edit',
      templateUrl: 'app/patients/patients.add.edit.tpl.html',
      controller: 'PatientEditController as editPatient',
      resolve: {
        title: function() {
          return 'Edit Patient Java Insecure';
        },
        patient: function(InsecurePatients, $stateParams) {
          return InsecurePatients.get({
            id: $stateParams.id
          }).$promise;
        }
      }
    });
    $urlRouterProvider.otherwise('/patients');
  });
