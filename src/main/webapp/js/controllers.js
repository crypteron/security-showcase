angular.module('showcase.controllers', []).controller('PatientListController',
  function($state, patients, addState, editState) {
    this.patients = patients;
    this.addPatient = function() {
      $state.go(addState);
    }
    this.editPatient = function(patient) {
      $state.go(editState, {
        id: patient.id
      });
    }
  }).controller('PatientCreateController', function($state, newPatient, listState) {
  this.title = 'Add Patient';
  this.patient = newPatient;
  this.cancel = function() {
    $state.go(listState);
  };
  this.save = function() {
    this.patient.$save(function() {
      $state.go(listState);
    });
  }
}).controller('PatientEditController', function($state, popupService, patient, listState) {
  this.title = 'Edit Patient';
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
