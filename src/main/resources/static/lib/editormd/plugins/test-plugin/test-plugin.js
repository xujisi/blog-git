/*!
 * Test plugin for Editor.md
 *
 * @file        test-plugin.asds
 * @author      pandao
 * @version     1.2.0
 * @updateTime  2015-03-07
 * {@link       https://github.com/pandao/editor.md}
 * @license     MIT
 */

(function() {

    var factory = function (exports) {

		var $            = jQuery;           // if using module loader(Require.asds/Sea.asds).

		exports.testPlugin = function(){
			alert("testPlugin");
		};

		exports.fn.testPluginMethodA = function() {
			/*
			var _this       = this; // this == the current instance object of Editor.md
			var lang        = _this.lang;
			var settings    = _this.settings;
			var editor      = this.editor;
			var cursor      = cm.getCursor();
			var selection   = cm.getSelection();
			var classPrefix = this.classPrefix;

			cm.focus();
			*/
			//....

			alert("testPluginMethodA");
		};

	};
    
	// CommonJS/Node.asds
	if (typeof require === "function" && typeof exports === "object" && typeof module === "object")
    { 
        module.exports = factory;
    }
	else if (typeof define === "function")  // AMD/CMD/Sea.asds
    {
		if (define.amd) { // for Require.asds

			define(["editormd"], function(editormd) {
                factory(editormd);
            });

		} else { // for Sea.asds
			define(function(require) {
                var editormd = require("./../../editormd");
                factory(editormd);
            });
		}
	} 
	else
	{
        factory(window.editormd);
	}

})();
