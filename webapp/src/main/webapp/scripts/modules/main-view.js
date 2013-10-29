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
      '<div class="header"></div>',
      '<div class="body"></div>',
      '<div class="footer"></div>',
    ].join("")),
    /** The nodes to pick out of the template */
    nodes: {
      "header": ".header",
      "body": ".body",
      "footer": ".footer"
    },
    /**
     * Render the view
     */
    renderUi: function() {
      this.node("header").text(i18n('main-view.header'));
      this.node("body").text(i18n('main-view.body'));
      this.node("footer").text(i18n('main-view.footer'));
    }
  });
});
