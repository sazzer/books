Y.namespace("Books.Login").Dialog = Y.Base.create("loginDialog", Y.Widget, [
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

    this._overlay = new Y.Overlay({
      srcNode: this.get("contentBox"),
      visible: false,
      centered: true,
      plugins: [
        Y.Plugin.OverlayModal,
        Y.Plugin.OverlayKeepaligned,
        {
          fn: Y.Plugin.OverlayAutohide, 
          cfg: {
            focusedOutside: false
          }
        }
      ]
    }).render();
  },

  /**
   * Cause the login dialog to be displayed
   * @method show
   */
  show: function() {
    this._overlay.show();
  }
}, {
  // Statics
  ATTRS: {
    
  },
  /** The template to render into the widget */
  _TEMPLATE: [
    '<div class="panel panel-default">',
      '<div class="panel-heading">Login</div>',
      '<div class="panel-body">',
        '<form class="form-horizontal" role="form">',
          '<div class="form-group">',
            '<label for="inputEmail" class="col-lg-2 control-label">Email</label>',
            '<div class="col-lg-10">',
              '<input type="email" class="form-control" placeholder="Email" name="inputEmail" />',
            '</div>',
          '</div>',
          '<div class="form-group">',
            '<label for="inputPassword" class="col-lg-2 control-label">Password</label>',
            '<div class="col-lg-10">',
              '<input type="password" class="form-control" placeholder="Password" name="inputPassword" />',
            '</div>',
          '</div>',
          '<div class="form-group">',
            '<div class="col-lg-offset-2 col-lg-10">',
              '<button type="submit" class="btn btn-default">Sign in</button>',
            '</div>',
          '</div>',
        '</form>',
      '</div>',
    '</div>'
  ].join(""),
  /** The class names to extract from the widget */
  _CLASS_NAMES: [
  ],
  _EVENTS: {}
});


