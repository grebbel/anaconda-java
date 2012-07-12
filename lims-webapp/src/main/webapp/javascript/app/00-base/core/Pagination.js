App.Pagination = Em.Object.extend({
	
	requestPage: 1,

	displayPage: 1,

	pageSize: 10,

	itemCount: 0,

	totalCount: 0,

	pageCount: function() {
		return Math.ceil(this.get('totalCount') / this.get('pageSize'));
	}.property('totalCount', 'pageSize'),

	displayOffset: function() {
		return (this.get('displayPage') - 1) * this.get('pageSize')
	}.property('displayPage', 'pageSize'),

	displayRange: function() {
		return {
			start: this.get('displayOffset') + 1,
			end: this.get('displayOffset') + this.get('itemCount'),
			total: this.get('totalCount')
		};
	}.property('displayOffset', 'itemCount', 'totalCount'),
	
	populateFrom:function(data) {
        this.set('displayPage', data.page || this.get('displayPage'));
        this.set('itemCount', data.itemCount || this.get('itemCount'));
        this.set('totalCount', data.totalCount || this.get('totalCount'));	    
	}

});

// No-op instance for use as a default argument value.
App.Pagination.NO_OP = App.Pagination.create();