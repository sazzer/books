define(["backbone"], function(Backbone) {
  var MainPage = Backbone.View.extend({
    tagName: "div",
    className: "main-view",
    events: {
    },
    initialize: function() {
    },
    render: function() {
      this.$el.html("Hello, World");
      return this;
    }
  });
});
