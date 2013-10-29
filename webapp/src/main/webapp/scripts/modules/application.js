define(["main-view", "jsperanto"], function(MainView) {
  $.jsperanto.init(function(t) {
    window.i18n = t;
    var mainView = new MainView();
    mainView.render($("body"));
  }, {
    fallbackLang: "en"
  });
});
