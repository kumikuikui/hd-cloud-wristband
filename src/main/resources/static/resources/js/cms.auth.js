/**
 * CMS验证模块
 * 在请求头中增加验证信息
 * 使用方法，引入该脚本，在module中注入本模块即可
 * angular.module('myModule', ['cms.auth']);
 */

(function() {
    'use strict';

    angular
        .module('cms.auth', [])
        .factory('httpInterceptor', httpInterceptor)
        .config(['$httpProvider', function($httpProvider) {
            $httpProvider.interceptors.push('httpInterceptor');
        }]);

    httpInterceptor.$inject = [];
    var alertTime;

    function httpInterceptor() {
        var service = {
            request: requestInterceptor,
            response: responseInterceptor,
            responseError: responseErrorInterceptor
        };

        return service;
        function requestInterceptor(config){
            config.headers['token'] = getToken();
            config.headers['userId'] = getUserId();
            return config;
        }
        function responseInterceptor(response) {
            return response;
        }
        function responseErrorInterceptor(response) {
            if(alertTime && new Date - alertTime < 10000){
              return response;
            }
            if(response.status == '401'){
              alert('登录验证失败，请重新登录');
              alertTime = new Date;
              location.href = '/login.html';
            }
            return response;
        }
    }

    function getToken(){
      return localStorage['token'];
    }
    function getUserId(){
      return localStorage['userId'];
    }
})();
