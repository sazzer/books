/*
 Inverted IOC container v99

 https://github.com/philmander/inverted-js

 Copyright (c) 2013, Phil Mander
 Licensed under the MIT license
*/
"function"!==typeof define&&(define=require("amdefine")(module));
define("inverted/Util",function(){var k={isArray:function(a){return Array.isArray?Array.isArray(a):"[object Array]"===Object.prototype.toString.call(a)},inArray:function(a,g){if(g.indexOf)return g.indexOf(a);for(var h=0,f=g.length;h<f;h++)if(g[h]===a)return h;return-1},trim:function(a){return a?a.replace(/^\s+|\s+$/g,""):a},parseProtoReference:function(a){a=a.match(/^(.+?)(\[(.+?)\])?$/);return{protoId:k.trim(a[1]),interfaces:a[3]?k.splitCommaDelimited(a[3]):null}},matchProtoRefString:function(a){return"string"===
typeof a&&null!==a.match(/^\*[^\*]/)},splitCommaDelimited:function(a){return a?a.split(/\s*,\s*/):[]},warn:function(a){"undefined"!==typeof console&&console.warn&&(a instanceof Error?console.warn(a.message,a):console.warn(a))},createError:function(a){return new k.InvertedError(a)},InvertedError:function(a){this.message=a}};k.InvertedError.prototype=Error.prototype;k.InvertedError.prototype.print=function(){k.warn(this.message)};return k});
define("inverted/Promise",function(){var k=function(a){this._ctx=a;this._sucesses=[];this._failures=[]};k.prototype.then=function(a,g){"function"===typeof a&&this._sucesses.push(a);"function"===typeof g&&this._failures.push(g)};k.prototype.notifySuccess=function(a){for(var g=0;g<this._sucesses.length;g++)this._sucesses[g].apply(this._ctx,a)};k.prototype.notifyFailure=function(a){for(var g=0;g<this._failures.length;g++)this._failures[g].call(this._ctx,a)};return k});
define("inverted/DependencyTree",["inverted/Util"],function(k){var a=function(){this.protos=[];this.children=[];this.parent=null};a.prototype.addChild=function(){var g=new a;g.parent=this;this.children.push(g);return g};a.prototype.getChildren=function(){return this.children};a.prototype.getParent=function(){return this.parent};a.prototype.checkForCircular=function(a){var h=function(f,j){if(null!==f){for(var d=0;d<f.protos.length;d++)if(f.protos[d].id&&f.protos[d].id===j)return f.protos[d];return h(f.parent,
j)}return null};return h(this.parent,a)};a.prototype.addProto=function(a,h,f){if(f&&this.checkForCircular(a))throw a=new k.createError("Circular dependency detected for ["+a+"]"),a.circular=!0,a;this.protos.push({id:a,instance:h||null})};return a});
define("inverted/ProtoFactory",["inverted/DependencyTree","inverted/Util"],function(k,a){var g=function(h){this.config=h;this.appContext=null;this.injectAppContext=!0===this.config.injectAppContext?!0:!1;this.moduleMap={}};g.prototype.addLoadedModules=function(h){for(var a in h)h.hasOwnProperty(a)&&(this.moduleMap[a]=h[a])};g.prototype.getProto=function(h,f){f=f||new k;var j=a.parseProtoReference(h),d=this.getProtoConfig(j.protoId),c=null,e=f.addChild();"static"===d.scope?"string"===typeof d.module&&
(c=this.moduleMap[d.module]):!d.scope||"singleton"!==d.scope||"singleton"===d.scope&&!d.instance?(c=this._createInstance(j.protoId,d,j.interfaces,!0===this.injectAppContext&&!1!==d.injectAppContext||!0!==this.injectAppContext&&!0===d.injectAppContext,e),d.scope&&"singleton"===d.scope&&(d.instance=c)):c=d.instance;return c};g.prototype._createInstance=function(h,f,j,d,c){var e=null,e=this.moduleMap[f.module],b=c.checkForCircular(h);if(b)return c.addProto(h,b.instance),b.instance;b=this._createArgs(f.args,
c);f.extendsRef&&this._extendProto(e,this.getProto(f.extendsRef,c));j&&this._checkImplements(h,e.prototype,j);switch(b.length){case 0:e=new e;break;case 1:e=new e(b[0]);break;case 2:e=new e(b[0],b[1]);break;case 3:e=new e(b[0],b[1],b[2]);break;case 4:e=new e(b[0],b[1],b[2],b[3]);break;case 5:e=new e(b[0],b[1],b[2],b[3],b[4]);break;case 6:e=new e(b[0],b[1],b[2],b[3],b[4],b[5]);break;case 7:e=new e(b[0],b[1],b[2],b[3],b[4],b[5],b[6]);break;case 8:e=new e(b[0],b[1],b[2],b[3],b[4],b[5],b[6],b[7]);break;
case 9:e=new e(b[0],b[1],b[2],b[3],b[4],b[5],b[6],b[7],b[8]);break;case 10:e=new e(b[0],b[1],b[2],b[3],b[4],b[5],b[6],b[7],b[8],b[9]);break;default:throw a.createError("Could not instantiate proto. Instances have a 10 arg limit");}c.addProto(h,e);if(f.props){h=f.props;for(var g in h)h.hasOwnProperty(g)&&(j=this._createArgs([h[g]],c),"function"===typeof e[g]?(j[0]=a.isArray(j[0])?j[0]:[j[0]],e[g].apply(e,j[0])):e[g]=j[0])}if(f.mixin&&f.mixin.length){f=f.mixin;h=f.length;for(g=0;g<h;g++)b=f[g],j="string"===
typeof b?b:b.ref,b="boolean"===typeof b.override?b.override:!0,this._mixin(e,this.getProto(j,c),j,b)}this.appContext&&d&&(e.__appContext__=this.appContext);return e};g.prototype._getProtoFromFactory=function(h,f,j){j=this.getProto(h,j);if(f)return j[f].apply(j);throw a.createError("No factory method defined with "+h);};g.prototype._createArgs=function(h,f){var j=[];if(h)for(var d,c=0;c<h.length;c++){var e=h[c];if(null===e||"undefined"===typeof e)j[c]=e;else if((d="object"===typeof e&&!a.isArray(e))&&
e.ref||a.matchProtoRefString(e))d=e.ref||e.substr(1),j[c]=this.getProto(d,f);else if(d&&e.factoryRef)j[c]=this._getProtoFromFactory(e.factoryRef,e.factoryMethod,f);else if(d&&e.module)j[c]=this._createInstance("[anonymous]",e,[],e.injectAppContext,f);else if(d){j[c]={};for(var b in e)e.hasOwnProperty(b)&&((d=e[b])&&(d.ref||a.matchProtoRefString(d))?(d=d.ref||d.substr(1),j[c][b]=this.getProto(d,f)):j[c][b]=d&&d.factoryRef?this._getProtoFromFactory(d.factoryRef,d.factoryMethod,f):d&&d.module?this._createInstance("[anonymous]",
d,[],d.injectAppContext,f):d)}else j[c]=e}return j};g.prototype._extendProto=function(a,f){var j={},d;for(d in a.prototype)j[d]=a.prototype[d];a.prototype=f;a.prototype.__super__=f.constructor;for(var c in j)a.prototype[c]=j[c];a.prototype.constructor=a};g.prototype._mixin=function(a,f,j,d){for(var c in f)"function"===typeof f[c]&&(!(c in a)||c in a&&d)&&function(c){a[c]=function(){return f[c].apply(f,arguments)}}(c);a["__"+j+"__"]=f};g.prototype._checkImplements=function(h,f,j){var d,c,e,b,g;for(d=
0;d<j.length;d++){e=this.getInterfaceConfig(j[d]);g=[];for(c=0;c<e.length;c++)b=a.trim(e[c]),"function"!==typeof f[b]&&g.push(h+" does not implement the method '"+b+"'");if(g.length)throw a.createError("Interface [ "+j[d]+"] not implemented: \n\t"+g.join("\n\t"));}};g.prototype.getProtoConfig=function(h){var f=this.config.protos;h=a.trim(h);if(f&&f.hasOwnProperty(h))return f[h];throw a.createError("No proto is defined for ["+h+"]");};g.prototype.getInterfaceConfig=function(h){var f=this.config.interfaces;
h=a.trim(h);if(f&&f.hasOwnProperty(h))return f[h];throw a.createError("No interface is defined for ["+h+"]");};return g});
define("inverted/AppContext",["inverted/ProtoFactory","inverted/DependencyTree","inverted/Promise","inverted/Util"],function(k,a,g,h){var f=function(a,d,c){this.config=a;this.protoFactory=d;this.originalModule=c||module;this.allowCircular=this.config.allowCircular||!1;this.modules=[];this._loader=define.amd&&"undefined"!==typeof requirejs?require:define.amd&&"undefined"!==typeof curl?curl:this._commonRequire};f.create=function(a,d){var c=new k(a),e=new f(a,c,d);return c.appContext=e};f.loader=function(a){this._loader=
a};f.prototype.getProto=function(a,d,c){var e=this,b=new g;if(!this._loader)throw Error("No AMD loader is defined");if("string"===typeof d)throw h.createError('Inverted\'s interface has changed. Please now pass proto ID\'s as an array in a single argument\n\tgetProto(["one", "two","three"], onSuccess, onError);');"string"===typeof a&&(a=[a]);var f=[],l,k=[];for(l=0;l<a.length;l++)try{k.push(this._getDependencies(a[l]))}catch(n){if(n instanceof h.InvertedError&&!n.circular)f.push(n);else throw n;}this._loader(this.modules,
function(){var g={};for(l=0;l<e.modules.length;l++)g[e.modules[l]]=arguments[l];e.protoFactory.addLoadedModules(g);var g=[],k;for(l=0;l<a.length;l++)try{k=e.protoFactory.getProto(a[l]),g.push(k)}catch(m){if(m instanceof h.InvertedError)f.push(m);else throw m;}for(l=0;l<f.length;l++)k=f[l],"function"===typeof c&&c.call(e,k),b.notifyFailure(k),k.print();"function"===typeof d&&d.apply(e,g);b.notifySuccess(g)});return b};f.prototype._getDependencies=function(f,d){d=d||new a;var c=this.protoFactory.getProtoConfig(f);
try{d.addProto(f,null,!0),0>h.inArray(c.module,this.modules)&&this.modules.push(c.module)}catch(e){if(e.circular&&this.allowCircular)return d;throw e;}var b=d.addChild();if(c.extendsRef){var g=h.parseProtoReference(c.extendsRef).protoId;this._getDependencies(g,b)}c.args&&this._getDependenciesFromArgs(c.args,b);if(c.props)for(var k in c.props)c.props.hasOwnProperty(k)&&this._getDependenciesFromArgs([c.props[k]],b);if(c.mixin&&c.mixin.length){k=c.mixin.length;for(var m,g=0;g<k;g++)m=c.mixin[g],m="string"===
typeof m?m:m.ref,m=h.parseProtoReference(m).protoId,this._getDependencies(m,b)}return d};f.prototype._getDependenciesFromArgs=function(a,d){if(a)for(var c,e=0;e<a.length;e++){var b=a[e];if(!(null===b||"undefined"===typeof b))if((c="object"===typeof b)&&b.ref||h.matchProtoRefString(b))c=h.parseProtoReference(b.ref||b.substr(1)).protoId,this._getDependencies(c,d);else if(c&&b.factoryRef)this._getDependencies(b.factoryRef,d);else if(c&&b.module)d.addProto({module:b.module});else if(c)for(var f in b)b.hasOwnProperty(f)&&
((c=b[f])&&(c.ref||h.matchProtoRefString(c))?(c=h.parseProtoReference(c.ref||c.substr(1)).protoId,this._getDependencies(c,d)):c&&c.factoryRef?this._getDependencies(c.factoryRef,d):c&&c.module&&d.addProto({module:c.module}))}return d};f.prototype._commonRequire=function(a,d){var c=this,e=[];a.forEach(function(a){e.push(c.originalModule.require(a))});d.apply(this,e)};return f});define(["inverted/AppContext"],function(k){return k});