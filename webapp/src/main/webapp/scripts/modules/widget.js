define(["backbone", "underscore"], function(Backbone, _) {
  /**
   * Base class to represent a widget, doing a lot of work for us automatically
   */
  return Backbone.View.extend({
    /** The top level tag name */
    tagName: "div",
    /** The events to automatically handle */
    events: {
    },
    /** The list of nodes to pull out */
    nodes: {},
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
      if (this.template) {
        this.$el.html(this.template());
      }

      if (this.nodes) {
        var boundNodes = {};
        _.each(this.nodes, function(selector, name) {
          boundNodes[name] = this.$el.find(selector);
        }, this);
        this.boundNodes = boundNodes;
      }

      if (this.renderUi) {
        this.renderUi();
      }

      if (parent) {
        parent.append(this.$el);
      }
      return this;
    },

    /**
     * Get the node with the provided name. This finds the nodes that were named for the
     * render step to find and won't find anything created afterwards
     *
     * @param name The name of the node
     * @return the node, or undefined if it isn't known. 
     */
    node: function(name) {
      return this.boundNodes[name];
    }
  });
});

