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
      '<div>Search results here</div>'
    ].join("")),
    /** The nodes to pick out of the template */
    nodes: {
    },
    /** Definition of events to auto-wire */
    events: {
    }
  });
});

