App.FocusButton = Em.View.extend({

    tagName: 'span',
    
    classNames: ['focus'],

    template: Em.Handlebars.compile(
    '<button {{bindAttr title="view.title"}}></button>'
    ),
    
    title: 'Focus',

    didInsertElement: function() {
        this.$('button').button({
            icons: {
                primary: 'ui-icon-search'
            },
            text: false
        });
    },

    analysis: null,

    click: function() {
        this.get('controller').focusOn(this.get('analysis'));
    }

})