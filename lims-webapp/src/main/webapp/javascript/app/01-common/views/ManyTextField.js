App.ManyTextField = Em.View.extend({

    tagName: 'span',

    templateName: 'many-text-field',

    content: [],

    rows: 4,

    cols: 40,

    value: function() {
        var value = '';
        var content = this.get('content');
        if (Em.empty(content) == false) {
            content.forEach(function(line, index) {
                if (index > 0) {
                    value += '\n';
                }
                value += line;
            });
        }
        return value;
    }.property('content'),
    
    didInsertElement: function() {
        this.$('textarea').val(this.get('value'));
    },

    change: function() {
        this.set('content', this.$('textarea').val().split('\n'));
    },
    
    focusOn: function() {
        this.$('textarea').focus();
    }
    

});