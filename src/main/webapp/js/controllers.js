angular.module('showcase.controllers', []).controller('PatientListController',
  function($state, type, patients, addState, editState) {
    this.type = type;
    this.patients = patients;
    this.addPatient = function() {
      $state.go(addState);
    }
    this.editPatient = function(patient) {
      $state.go(editState, {
        id: patient.id
      });
    }
  }).controller('PatientCreateController', function($state, type, newPatient, listState) {
  this.title = 'Add Patient';
  this.type = type;
  this.patient = newPatient;
  this.cancel = function() {
    $state.go(listState);
  };
  this.save = function() {
    this.patient.$save(function() {
      $state.go(listState);
    });
  }
}).controller('PatientEditController', function($state, type, popupService, patient, listState) {
  this.title = 'Edit Patient';
  this.type = type;
  this.patient = patient;

  this.cancel = function() {
    $state.go(listState);
  };

  this.save = function() {
    this.patient.$update(function() {
      $state.go(listState);
    });
  };

  this.deletePatient = function(patient) {
    if (popupService.showPopup('Really delete ' + patient.firstName + '?')) {
      patient.$delete(function() {
        $state.go(listState);
      });
    }
  };
});
