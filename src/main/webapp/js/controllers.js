angular.module('techShowcase.controllers', []).controller('PatientListController', function($scope, $state, Patients) {
  $scope.patients = Patients.query();
}).controller('PatientCreateController', function($scope, $state, Patients) {
  $scope.patient = new Patients();
  $scope.save = function() {
    $scope.patient.$save(function() {
      $state.go('patients');
    });
  }
}).controller('PatientEditController', function($scope, $state, $stateParams, popupService, Patients) {
  $scope.loadPatient = function() {
    $scope.patient = Patients.get({
      id : $stateParams.id
    });
  };
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
  }
  $scope.loadPatient();
});
