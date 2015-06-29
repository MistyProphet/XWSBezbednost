'use strict';

 angular.module('invoices', ['resource.invoice',
 	'angular-md5'])

 .controller('outgoingInvoicesListCtrl', function (OutgoingInvoice, $scope, $location, md5, $log) {
	$scope.invoices = OutgoingInvoice.query();
    $scope.reverseSort = false;
    $scope.orderProp = 'suplierName';
	$log.info($scope.invoices.length);//0
	//kada smo kliknuli na red u tabeli prelazimo na stranicu za editovanje fakture sa zadatim id-om
 	$scope.insertOrEditInvoice = function (invoice) {
 		if(invoice){
 			$location.path('/invoice/'+invoice.id);
 		}
 		else{
			$location.path('/invoice/new');
 		}
 	}
 });
