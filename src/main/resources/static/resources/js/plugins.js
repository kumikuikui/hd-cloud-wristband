/**
 * 验证模块
 * 在请求头中增加验证信息
 * 使用方法，引入该脚本，在module中注入本模块即可
 * angular.module('myModule', ['plugin.auth']);
 */


var mograbUtils = angular.module('df.utils', []);

// 滑动验证组件
mograbUtils.directive('captcha', ['$http', '$q', function ($http, $q) {

  var directive = {
    restrict: 'E',
    template: '<div id="{{id}}"></div>',
    scope: {
      onComplete: '&',
      validate: '='
    },
    link: linkFunc
  };

  var loadScript = $q(function (resolve, reject) {
    var script = document.createElement('script');

    angular.element(script).on('load', function () {
      resolve(initGeetest); // 这个方法是由gt.js提供的
    });

    angular.element(script).on('error', reject);
    angular.element(document.querySelector('head')).append(script);

    script.src = location.origin+'/mo_boss_df/public/js/gt.js';
  });

  var getInitData = $http.get("/mo_boss_df/m2/captcha/geetestinit");

  var initialValidate = function (args) {
    // 使用initGeetest接口
    // 参数1：配置参数
    // 参数2：回调，回调的第一个参数验证码对象，之后可以使用它做appendTo之类的事件
    var initGeetest = args[0], response = args[1];
    var data = response.data;
    return $q(function (resolve) {
      initGeetest({
        gt: data.gt,
        challenge: data.challenge,
        product: "embed", // 产品形式，包括：float，embed，popup。注意只对PC版验证码有效
        offline: !data.success // 表示用户后台检测极验服务器是否宕机，一般不需要关注
      }, resolve);
    })
  };

  var loadCompleted = $q.all([loadScript, getInitData]).then(initialValidate);

  var id = 'captcha_' + (+new Date())

  return directive;

  function linkFunc(scope, el, attr, ctrl) {
    loadCompleted.then(function (captchaObj) {
      // 将验证码加到id为captcha的元素里
      scope.id = id;
      setTimeout(function(){
        captchaObj.appendTo('#' + scope.id);

        captchaObj.onReady(function () {
          console.log('geeTest ready');
        });

        captchaObj.onError(function(e){
          console.log("网络错误");
        })

        captchaObj.onSuccess(function () {
          var validate = captchaObj.getValidate();
          if (validate) {
            setTimeout(function () {
              scope.$apply(function () {
                scope.validate = validate;
                scope.onComplete(scope);
                setTimeout(function(){captchaObj.refresh()}, 5000);
              })
            })
          }
        });
        // 更多接口参考：http://www.geetest.com/install/sections/idx-client-sdk.html
      });
    });
  }

}]);