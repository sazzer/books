define(["main-view", "router", "jsperanto"], function(MainView, Router) {
  $.jsperanto.init(function(t) {
    window.i18n = t;
    var router = Router;

    var mainView = new MainView({router: router});
    mainView.render($("body"));
  }, {
    fallbackLang: "en"
  });
});
