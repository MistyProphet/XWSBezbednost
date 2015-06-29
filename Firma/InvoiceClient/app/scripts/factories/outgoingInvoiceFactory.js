angular.module('resource.invoice', ['ngResource'])
	.factory('OutgoingInvoice', function ($resource) {
	return $resource('http://localhost:8080/Firma/api/slanje/:invoiceId',null, {
        'update': { method:'PUT' }
    });
})
