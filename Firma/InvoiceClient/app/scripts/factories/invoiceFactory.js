angular.module('resource.invoice', ['ngResource'])
	.factory('Invoice', function ($resource) {
	return $resource('http://localhost:8080/Firma/api/invoice/:invoiceId',null, {
        'update': { method:'PUT' }
    });
})
