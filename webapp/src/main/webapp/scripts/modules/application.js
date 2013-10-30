define(["container/container", "jsperanto"], function(Container) {
  $.jsperanto.init(function(t) {
    var containerDefinition = {
      "main-view": {
        "type": "bean",
        "scope": "singleton",
        "module": "main-view"
      }
    };

    window.i18n = t;
    var container = new Container(containerDefinition);
    container.register("i18n", t);
    var mainView = container.getObject({
      name: "main-view",
      callback: {
        success: function(mainView) {
          mainView.render($("body"));
        },
        failure: function(error, beanName) {
          $("body").append("Error loading application: " + error + " when loading bean: " + beanName);
        }
      }
    });
  }, {
    fallbackLang: "en"
  });
});
