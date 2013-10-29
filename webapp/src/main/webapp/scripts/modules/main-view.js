define(["backbone", "underscore"], function(Backbone, _) {
  /**
   * The main view of the application
   */
  return Backbone.View.extend({
    /** The top level tag name */
    tagName: "div",
    /** The top level class name */
    className: "main-view",
    /** The events to automatically handle */
    events: {
    },
    /** The template to render */
    template: _.template([
      '<div class="header"></div>',
      '<div class="body"></div>',
      '<div class="footer"></div>',
    ].join("")),
    /**
     * Construct the view
     */
    initialize: function() {
    },
    /**
     * Render the view
     * @param parent The node to render this into
     */
    render: function(parent) {
      this.$el.html(this.template());
      this.$el.find(".header").text(i18n('main-view.header'));
      this.$el.find(".body").text(i18n('main-view.body'));
      this.$el.find(".footer").text(i18n('main-view.footer'));
      parent.append(this.$el);
      return this;
    }
  });
});
