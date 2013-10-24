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
  }
}, {
  // Statics
  ATTRS: {
    
  },
  /** The template to render into the widget */
  _TEMPLATE: [
    "<div id='header' class='c header}'>",
    "</div>",
    "<div id='body' class='c body}'>",
    "</div>",
    "<div id='footer' class='c footer}'>",
    "</div>"
  ].join(""),
  /** The class names to extract from the widget */
  _CLASS_NAMES: [
    "header",
    "body",
    "footer"
  ]
});

