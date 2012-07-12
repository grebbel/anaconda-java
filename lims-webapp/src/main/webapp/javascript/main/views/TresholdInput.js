App.TresholdInput = Em.View.extend(App.Waitable, {
    
    tagName: 'span',

    template: Em.Handlebars.compile(
    '<input class="treshold" type="number" {{bindAttr value="view.value"}} {{bindAttr step="view.step"}} min="0" />'
    ),

    analysis: null,

    value: 0.1,

    step: 0.05,
    
    valueDelay: 300,
    
    change: function() {
        var value = this.$('input').val();
        var valueDelay = this.get('valueDelay');
        if (valueDelay > 0) {
            var self = this;
            this.waitFor(valueDelay).done(function() {
                self.set('value', value);
            });
        } else {
            this.set('value', value);
        }
        this.setPath('analysis.isSelected', true);            
    },

    keyDown: function(event) {
        if (event.which == 13) {
            event.preventDefault();
            this.change();
        }
    }

});