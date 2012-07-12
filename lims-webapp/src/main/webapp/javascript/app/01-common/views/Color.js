App.Color = Em.View.extend({

    tagName: 'span',

    classNames: ['color'],

    template: Em.Handlebars.compile('<span class="paint" {{bindAttr style="view.style"}}></span>'),

    defaultColor: {
        hex: '000000'
    },

    style: function() {
        var color = this.get('color') || this.get('defaultColor');
        return 'background-color: #' + Em.get(color, 'hex');
    }.property('color')

});