App.FormButtons = Em.View.extend({
    templateName: 'form-buttons',
    
    classNames: ['form-buttons'],
    
    submitLabel: function() {
        return this.getPath('controller.isNewContent')? 'Create' : 'Update';
    }.property('controller.isNewContent')
    
});