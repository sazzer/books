define(["backbone", "underscore"], function(Backbone, _) {
  /**
   * Simple bean definition that represents a class in a module
   */
  var SimpleBeanDefinition = Backbone.Model.extend({
    /** The name of the module to load for the bean definition */
    module: undefined,
    /**
     * Construct the bean definition
     * @param module The module to load for the bean
     */
    initialize: function(module) {
      this.module = module;
    },
    /**
     * Actually build the bean
     * @param container The container that is needed to get dependencies
     * @param success The callback for when the bean is successfully loaded
     * @param failure The callback for if the bean fails to load
     */
    build: function(container, success, failure) {
      require([this.module], function(module) {
        success(new module());
      });
    }

  });
  var Container = Backbone.Model.extend({
    /** The cache of singleton beans that we have created */
    cache: {},
    /** The bean definitions to use to build the beans */
    definitions: {},
    /**
     * Initialize the container with an initial set of bean definitions
     * @param definitions the bean definitions to use. Optional
     */
    initialize: function(definitions) {
      this.registerBeanDefinitions(definitions);
    },
    /**
     * Register a set of bean definitions into the container
     * @param definitions The bean definitions to register
     */
    registerBeanDefinitions: function(definitions) {
      _.each(definitions, function(definition, name) {
        if (this.definitions[name]) {
          console.warn("Bean definition for duplicate name: " + name);
        }
        else {
          var scope = definition.scope || "singleton",
              beanDefinition = this._buildBeanDefinition(definition);
          if (beanDefinition) {
            console.log("Registering bean definition: " + name);
            this.definitions[name] = {
              scope: scope,
              definition: beanDefinition
            };
          }
          else {
            console.error("No bean definition built for bean: " + name);
          }
        }
      }, this);
    },

    /**
     * Build a bean definition
     * @param definition The definition to build the bean definition from
     * @return the bean definition
     */
    _buildBeanDefinition: function(definition) {
      var type = definition.type || "bean",
          beanDefinitionFactory = this["_buildBeanDefinition" + type];
      if (beanDefinitionFactory) {
        return beanDefinitionFactory(definition);
      }
      else {
        console.error("Bean definition of unsupported type: " + type + " for bean: " + name);
      }
    },

    /**
     * Build a bean definition for a bean of type "bean"
     * @param definition The definition to build the bean definition from
     * @return the bean definition
     */
    _buildBeanDefinitionbean: function(definition) {
      return new SimpleBeanDefinition(definition.module);
    },
    /**
     * Register a singleton object with the container. This is an object that was constructed
     * outside of the container and passed in to be used by other container beans
     * @param name The name of the singleton object
     * @param object The object
     */
    register: function(name, object) {
      console.log("Registering bean: " + name);
      this.cache[name] = object;
      this.trigger("container:register", {
        name: name,
        object: object
      });
    },
    /**
     * Get an object from the container
     * @param cfg The configuration of the object to get
     * @param cfg.name The name of the object to get. Required.
     * @param cfg.callback The callbacks to use. Optional
     * @param cfg.callback.success The callback to call when the object is successfully loaded. Optional
     * @param cfg.callback.failure The callback to call if an error occurred. Optional
     * @param cfg.callback.context The context of the callbacks
     */
    getObject: function(cfg) {
      var name = cfg.name,
          callbacks = cfg.callback || {},
          successCallback = callbacks.success || function() {},
          failureCallback = callbacks.failure || function() {};

      if (callbacks.context) {
        successCallback = _.bind(successCallback, callbacks.context);
        failureCallback = _.bind(failureCallback, callbacks.context);
      }

      if (this.cache[name]) {
        successCallback(this.cache[name], name, this);
      }
      else if (this.definitions[name]) {
        var definition = this.definitions[name];

        definition.definition.build(this, _.bind(function(bean) {
          console.log("Build bean: " + name);
          if (definition.scope == Container.SCOPE_SINGLETON) {
            this.register(name, bean);
          }
          successCallback(bean, name, this);
        }, this), _.bind(function(error) {
          console.warn("Failed to build bean: " + name + ": " + error);
          failureCallback(error, name, this);
        }, this));
      }
      else {
        failureCallback(Container.ERROR_UNKNOWN_BEAN, name, this);
      }
    }
  }, {
    SCOPE_SINGLETON: "singleton",
    SCOPE_PROTOTYPE: "prototype",
    ERROR_UNKNOWN_BEAN: "unknown_bean"
  });
  return Container;
});
