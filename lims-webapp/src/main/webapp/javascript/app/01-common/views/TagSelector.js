App.TagSelector = Em.View.extend({

    templateName: 'tag-selector',

    classNames: ['tag-selector'],

    change: function() {
        var a = []
        this.$('input:checked').each(function() {
            a.push($(this).val());
        });
        this.get('controller').selectTags(a);
    }

});

App.TagSelectorItem = Em.View.extend({

    tagName: 'span',

    content: null,

    selectedTags: [],

    name: function() {
        return this.getPath('content.name')
    }.property('content.name'),

    title: function() {
        var title = this.getPath('content.description');
        if (Em.empty(title)) {
            title = this.get('name');
        }
        return title;
    }.property('content.description'),

    checked: function() {
        return this.get('selectedTags').contains(this.get('name'));
    }.property('selectedTags'),

    didInsertElement: function() {
        this.$().parent().buttonset();
    }

})