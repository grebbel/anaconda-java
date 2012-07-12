App.IconButton = Em.View.extend({

    tagName: 'span',

    primary: null,

    secondary: null,

    text: true,

    didInsertElement: function() {
        this.$('button,input:button,input:submit').button({
            icons: {
                primary: this.get('primary'),
                secondary: this.get('secondary')
            },
            text: this.get('text')
        });
    }

});