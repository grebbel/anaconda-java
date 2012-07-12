App.ChartTypeSelector = Em.View.extend({
    
    templateName: 'chart-type-selector',

    value: 'linear',

    didInsertElement: function() {
        this.$('input:radio[value="' + this.get('value') + '"]').attr('checked', 'checked');
        this.$().buttonset();
    },

    change: function() {
        this.set('value', this.$('input:radio:checked').val());
    }

});