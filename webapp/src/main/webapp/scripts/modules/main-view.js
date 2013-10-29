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
      '<div class="header">Header</div>',
      '<div class="body">Body</div>',
      '<div class="footer">Footer</div>',
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
      parent.append(this.$el);
      return this;
    }
  });
});
