define(["backbone"], function(Backbone) {
  var router = new Backbone.Router();
  router.route("", "index", function() {
    console.log("Loading index");
  });
  router.route("search/:query", "search", function(query) {
      console.log("Loading search: " + query);
  });
  router.index = function() {
    router.navigate("", {trigger: true});
  }
  router.search = function(query) {
    router.navigate("search/" + query, {trigger: true});
  }

  Backbone.history.start();
  return router;
});

