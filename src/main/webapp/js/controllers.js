angular.module('showcase.controllers', []).controller('PatientListController',
  function($state, title, patients, addState, editState) {
    this.title = title;
    this.patients = patients;
    this.addPatient = function() {
      $state.go(addState);
    }
    this.editPatient = function(patient) {
      $state.go(editState, {
        id: patient.id
      });
    }
  }).controller('PatientCreateController', function($state, title, newPatient, listState) {
  this.title = title;
  this.patient = newPatient;
  this.cancel = function() {
    $state.go(listState);
  };
  this.save = function() {
    this.patient.$save(function() {
      $state.go(listState);
    });
  }
}).controller('PatientEditController', function($state, title, popupService, patient, listState) {
  this.title = title;
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
    if (popupService.showPopup('Really delete ' + patient.name + '?')) {
      patient.$delete(function() {
        $state.go(listState);
      });
    }
  };
});
