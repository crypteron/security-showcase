angular.module('techShowcase.controllers', []).controller('PatientListController', function($state, patients) {
  this.patients = patients;
}).controller('PatientCreateController', function($state, Patients) {
  this.patient = new Patients();
  this.save = function() {
    this.patient.$save(function() {
      $state.go('patients.list');
    });
  }
}).controller('PatientEditController', function($state, $stateParams, popupService, patient) {
  // Load patient
  this.patient = patient;

  this.save = function() {
    this.patient.$update(function() {
      $state.go('patients.list');
    });
  };

  this.deletePatient = function(patient) {
    if (popupService.showPopup('Really delete ' + patient.name + '?')) {
      patient.$delete(function() {
        $state.go('patients.list');
      });
    }
  };
});
