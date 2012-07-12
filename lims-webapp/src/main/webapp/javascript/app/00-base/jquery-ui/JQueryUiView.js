/**
 * View for use with JQuery UI widgets.
 * 
 * This class overrides didInsertElement() to delay the JQuery UI widget initialization by 100 milliseconds. This
 * ensures that complex widgets such as the Accordion are initialized correctly in the DOM.
 * 
 * Override initElement() to initialize the widget.
 * 
 */
App.JQueryUiView = Em.View.extend({

	willInsertElement: function() {
		this._super();
		this.set('isVisible', false);
	},

	didInsertElement: function() {
		this._super();
		Em.run.later(this, function() {
			this.set('isVisible', true);
			this.initElement();
		}, 100);
	},

	/**
	 * Override to initialize the element.
	 */
	initElement: function() {
	}
});
