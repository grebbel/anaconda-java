App.RequestFormView = App.FormView.extend({
    
	templateName: 'request-form',
	
	title: function() {
	    return this.getPath('controller.content.externalId') || '<No identifier>' ;
	}.property('controller.content.externalId')
	
})