Y.namespace("Books").Application = Y.Base.create("application", Y.Base, [
  // Extensions
], {
  // Prototype
  initializer: function() {
    Y.log("Starting application");
    this._mainView = new Y.Books.MainView();
    this._mainView.render(Y.one("body"));
  }
}, {
  // Statics
  ATTRS: {
    
  }
});
