define(["underscore", "widget"], function(_, Widget) {
  /**
   * The main view of the application
   */
  return Widget.extend({
    /** The top level tag name */
    tagName: "div",
    /** The top level class name */
    className: "main-view",
    /** The template to render */
    template: _.template([
      '<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">',
        '<div class="container">',
          '<div class="navbar-header">',
            '<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">',
              '<span class="sr-only">Toggle navigation</span>',
              '<span class="icon-bar"></span>',
              '<span class="icon-bar"></span>',
              '<span class="icon-bar"></span>',
            '</button>',
            '<a class="navbar-brand" href="#" i18n="pageName"></a>',
          '</div>',
          '<div class="collapse navbar-collapse">',
            '<ul class="nav navbar-nav">',
            '</ul>',
            '<div class="col-xs-5 col-sm-3 pull-right">',
              '<form class="search navbar-form" role="search">',
                '<div class="input-group">',
                  '<label class="sr-only" for="search" i18n="searchLabel" />',
                  '<input type="search" name="search" class="search form-control" />',
                  '<div class="input-group-btn">',
                    '<button class="btn btn-default" type="submit">',
                      '<span class="glyphicon glyphicon-search"></span>',
                    '</button>',
                  '</div>',
                '</div>',
              '</form>',
            '</div>',
          '</div>',
        '</div>',
      '</div>',
      '<div class="container main-area">',
      '</div>'
    ].join("")),
    /** The nodes to pick out of the template */
    nodes: {
      "searchForm": "form.search",
      "searchInput": "input.search",
      "mainArea": "div.main-area"
    },
    /** Definition of events to auto-wire */
    events: {
      "submit form.search": "_onSubmitSearchForm"
    },
    /**
     * Render the view
     * @method renderUi
     */
    renderUi: function() {
      this.node("searchInput").attr("placeholder", this.i18n("main-view.searchLabel"));
    },

    /**
     * Handle when the search form is submitted
     * @method onSubmitSearchForm
     * @param e The event
     * @private
     */
    _onSubmitSearchForm: function(e) {
      var searchInput = this.node("searchInput"),
          searchString = searchInput.val();
      e.preventDefault();

      if (searchString) {
        searchInput.val("");
        this.router.search(searchString);
      }
    },

    /**
     * Actually display the search screen for the given query
     * @param query The query to search for
     * @method displaySearch
     */
    displaySearch: function(query) {
      var mainArea = this.node("mainArea");
      this.__appContext__.getProto("search:screen", function(searchScreen) {
        searchScreen.render(mainArea);
      });
    }
  });
});
