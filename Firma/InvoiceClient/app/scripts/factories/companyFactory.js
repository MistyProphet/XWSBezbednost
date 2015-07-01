angular.module('resource.company', ['ngResource'])
	.factory('Company', function ($resource) {
	return $resource('http://localhost:8080/Firma/api/company',null, {
        'get': { method:'GET' }
    });
})
