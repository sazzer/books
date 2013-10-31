define(["backbone", "underscore"], function(Backbone, _) {
  return Backbone.Router.extend({
    /**
     * The actual routes that we want to support
     */
    routes: {
      "": function() {
      },
      "search/:query": "_onSearch"
    },
    /**
     * Route to a given URL
     * @param url The URL to route to
     */
    _route: function(url) {
      this.navigate(url, {trigger: true});
    },
    /**
     * Route to the index page
     */
    index: function() {
      this._route("");
    },
    /**
     * Route to the search page
     * @param query The query to search for
     */
    search: function(query) {
      this._route("search/" + query)
    },

    _onSearch: function(query) {
      this.__appContext__.getProto("main-view", function(mainView) {
        mainView.displaySearch(query);
      });
    }

  });
});

