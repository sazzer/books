define(["underscore", "widget"], function(_, Widget) {
  /**
   * The main view of the application
   */
  return Widget.extend({
    /** The top level tag name */
    tagName: "div",
    /** The top level class name */
    className: "search-screen",
    /** The template to render */
    template: _.template([
      '<div class="temp">Search results here</div>'
    ].join("")),
    /** The nodes to pick out of the template */
    nodes: {
      "temp": "div.temp"
    },
    /** Definition of events to auto-wire */
    events: {
    },
    /**
     * Render the view
     * @method renderUi
     */
    renderUi: function() {
      this.node("temp").append(this._query);
    },
    setSearch: function(query) {
      this._query = query;
    }
  });
});

