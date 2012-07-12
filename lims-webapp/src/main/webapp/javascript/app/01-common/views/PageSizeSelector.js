App.PageSizeSelector = Em.View.extend({

	pageSize: null,
	
	templateName: 'page-size-selector',
	
	didInsertElement: function() {
		this.$('select').val(this.get('pageSize'));
	},
	
	change: function() {
	    var pageSize = Number($('select').val());
		this.get('controller').changePageSize(pageSize);
	},
	
	pageSizeDidChange: function() {
		this.$('select').val(this.get('pageSize'));
	}.observes('pageSize')
	
});
