define(["backbone", "inverted", "jsperanto"], function(Backbone, inverted) {
  $.jsperanto.init(function(t) {
    define("i18n", [], function() {
      return t;
    });
    var containerDefinition = {
      protos: {
        "i18n": {
          "scope": "static",
          "module": "i18n"
        },
        "router": {
          "scope": "singleton",
          "module": "router",
          "injectAppContext": true
        },
        "main-view": {
          "scope": "singleton",
          "module": "main-view",
          "injectAppContext": true,
          "props": {
            "i18n": "*i18n",
            "router": "*router"
          }
        },
        "search:screen": {
          "scope": "prototype",
          "module": "search/screen"
        }
      }
    };

    var container = inverted.create(containerDefinition, window);

    container.getProto("main-view", function(mainView) {
      mainView.render($("body"));
      Backbone.history.start();
    }, function(error) {
        $("body").append("Error loading application: " + error);
    });
  }, {
    fallbackLang: "en"
  });
});
