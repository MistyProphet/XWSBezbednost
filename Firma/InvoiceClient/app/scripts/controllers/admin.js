'use strict';

 angular.module('resource.admin', ['resource.invoice',
 	'angular-md5'])

 .controller('adminCtrl', function (Artikal, $scope, $routeParams, $modal, $log, $location, InvoiceItem) {

$scope.lista=["1","2","3"];

		//preuzimanje parametra iz URL
		//preuzimanje fakure sa servera. Posto smo u Invoice factory rutu definisali kao '...invoice/:invoiceId' invoiceId ce se proslediti kao parametar rute na server 
		Artikal.query({'invoiceId':"1"}).$promise.then(function (data) {
			$scope.articles = data;
		});
	

	//modalni dijalog za stavku fakutre
	//modalni dijalog za stavku fakutre
	$scope.openModal = function (invoiceItem, size) {


		var modalInstance = $modal.open({
			templateUrl: 'views/mikaView.html',
			controller: 'lagerListCtrl',
			size: size,
			scope: $scope ,
			resolve: {
				invoiceItem: function () {
					return $scope.invoiceItem;
				}
			}
		});
		modalInstance.result.then(function (data) {
			var selectedDoc = data.selectedDoc;
			
			//ako stavka fakture nema id i ako je akcija 'save' znaci da je nova i dodaje se u listu. ako ima, svakako se manja u listi
			if( data.action==='add'){
				selectedDoc.$create(function () {
				$location.path('/lager-list');

			},
            function (response) {
                if (response.status === 500) {
                    $scope.greska = "greska";
                }
               
            }
		);
				$location.path('/lager-list');
					
			}
			//ako stavka treba da se obrise izbaci se iz niza
			
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};

$scope.koriguj = function (invoiceItem, size) {


		var modalInstance = $modal.open({
			templateUrl: 'views/brisanjeUloge.html',
			controller: 'lagerListCtrl',
			size: size,
			scope: $scope ,
			resolve: {
				invoiceItem: function () {
					return $scope.invoiceItem;
				}
			}
		});
		modalInstance.result.then(function (data) {
			var selectedDoc = data.selectedDoc;
			
			//ako stavka fakture nema id i ako je akcija 'save' znaci da je nova i dodaje se u listu. ako ima, svakako se manja u listi
			if( data.action==='delete'){
				selectedDoc.$obrisi(function () {
				$location.path('/lager-list');

			},
            function (response) {
                if (response.status === 500) {
                    $scope.greska = "greska";
                }
               
            }
		);
				$location.path('/prometni-dokumenti');
					
			}
			//ako stavka treba da se obrise izbaci se iz niza
			
		}, function () {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};	


$scope.setSelected = function (selectedDoc) {
   $scope.selectedDoc = selectedDoc;
};

$scope.setSelectedUloga = function (selectedDoc) {
   $scope.selectedUloga = selectedDoc;
};

$scope.okDelete = function () {
		$modalInstance.close({'invoiceItem':$scope.invoiceItem,
								'action':'delete'});
	};
$scope.okAdd = function () {
		$modalInstance.close({'invoiceItem':$scope.invoiceItem,
								'action':'add'});
	};
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
	};
$scope.articles = "";
	$scope.options = Artikal.query();
	$log.info($scope.articles.length);//0
	//kada smo kliknuli na red u tabeli prelazimo na stranicu za editovanje fakture sa zadatim id-om
 	$scope.dodaj = function () {
 		if($scope.selectedDoc){
 			$location.path('/lager-list/'+$scope.selectedDoc.idMagacinskaKartica);
 		}
 		else{
			$location.path('/invoice/new');
 		}
 	}
});

