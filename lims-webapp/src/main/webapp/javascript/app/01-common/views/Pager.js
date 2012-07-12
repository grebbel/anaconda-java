App.Pager = Wijmo.Pager.extend({

	requestPage: null,

	displayPage: null,

	pageCount: null,

	didInsertElement: function() {
		this._super();
		this.$().css('visibility', 'hidden');
	},

	pageIndexChanging: function(event, arg) {
		this.get('controller').gotoPage(arg.newPageIndex + 1);
		arg.handled = true;
	},
	
	shouldShow: function() {
		return this.get('pageCount') > 0;
	}.property('pageCount'),

	pageDisplayPropertyDidChange: function() {
		Em.run.once(this, function() {
			this.set('pageIndex', this.get('displayPage') - 1);
			this.$().css('visibility', this.get('shouldShow')? 'visible' : 'hidden');
		});
	}.observes('displayPage', 'shouldShow')

});
