App.Button = Em.View.extend({
    
    tagName: 'span',
    
    didInsertElement: function() {
        this.$('button,input:button,input:submit,input:reset').button();
    }
    
});