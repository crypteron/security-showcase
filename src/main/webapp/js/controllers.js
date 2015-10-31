angular.module('techShowcase.controllers', []).controller('PatientListController', function($scope, $state, patients) {
  $scope.patients = patients;
}).controller('PatientCreateController', function($scope, $state, Patients) {
  $scope.patient = new Patients();
  $scope.save = function() {
    $scope.patient.$save(function() {
      $state.go('patients');
    });
  }
}).controller('PatientEditController', function($scope, $state, $stateParams, popupService, patient) {
  // Load patient
    $scope.patient = patient;

  $scope.save = function() {
    $scope.patient.$update(function() {
      $state.go('patients');
    });
  };

  $scope.deletePatient = function(patient) {
    if (popupService.showPopup('Really delete ' + patient.name + '?')) {
      patient.$delete(function() {
        $state.go('patients');
      });
    }
  };
});
