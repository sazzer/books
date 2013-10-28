Y.namespace("Books").MainView = Y.Base.create("mainView", Y.Widget, [
  // Extensions
  Y.MakeNode
], {
  // Prototype

  /**
   * Render the widget
   * @method renderUI
   */
  renderUI: function() {
    this.get("contentBox").append(this._makeNode());
    this._locateNodes();

    this._loginDialog = new Y.Books.Login.Dialog().render();
  },
  /**
   * Handle when the home link is clicked
   * @method _onClickHome
   * @param e {EventFacade} The event facade
   * @private
   */
  _onClickHome: function(e) {
    e.preventDefault();
    return false;
  },
  /**
   * Handle when the account button is clicked
   * @method _onClickAccount
   * @param e {EventFacade} The event facade
   * @private
   */
  _onClickAccount: function(e) {
    e.preventDefault();
    this._loginDialog.show();
    return false;
  },
  /**
   * Handle when a search is submitted
   * @method _onSubmitSearch
   * @param e {EventFacade} The event facade
   * @private
   */
  _onSubmitSearch: function(e) {
    e.preventDefault();
    return false;
  }
}, {
  // Statics
  ATTRS: {
    
  },
  /** The template to render into the widget */
  _TEMPLATE: [
    '<div class="navbar navbar-inverse navbar-fixed-top">',
      '<div class="container">',
        '<div class="navbar-header">',
          '<a class="navbar-brand {c homeLink}" href="#">Personal Catalogue</a>',
        '</div>',
        '<div class="navbar-collapse collapse">',
          '<ul class="nav navbar-nav">',
          '</ul>',
          '<form class="navbar-form navbar-right {c searchForm}">',
            '<div class="form-group">',
              '<input type="search" placeholder="Search" class="form-control">',
            '</div>',
            '<button type="submit" class="btn btn-success">Go</button>"',
          '</form>',
          '<div class="navbar-form navbar-right">',
            '<button type="button" class="btn btn-default {C accountButton}">',
              '<span class="glyphicon glyphicon-user"></span>',
            '</button>',
          '</div>',
        '</div>',
      '</div>',
    '</div>'
  ].join(""),
  /** The class names to extract from the widget */
  _CLASS_NAMES: [
    "homeLink",
    "searchForm",
    "accountButton"
  ],
  _EVENTS: {
    "homeLink": {
      type: "click",
      when: "before",
      fn: "_onClickHome"
    },
    "searchForm": {
      type: "submit",
      when: "before",
      fn: "_onSubmitSearch"
    },
    "accountButton": {
      type: "click",
      when: "before",
      fn: "_onClickAccount"
    }
  }
});

