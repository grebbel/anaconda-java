App.TagField = Em.View.extend(App.Validatable, {

    tagName: 'span',

    source: 'tags?format=AUTOCOMPLETE&valueOnly=true',

    templateName: 'tag-field',

    classNames: ['tag-field'],

    content: [],

    nonExistingTags: null,

    createdTags: null,

    size: 60,

    tagify: null,

    contentDidChange: function() {
        var content = this.get('content');
        var tagify = this.get('tagify');
        if (Em.empty(content) == false && tagify) {
            this.get('content').forEach(function(tag) {
                tagify.tagify('add', tag);
            });
        }
    }.observes('content'),

    didInsertElement: function() {
        var self = this;
        var tagify = this.$('input').tagify({
            addTagPrompt: ''
        });
        tagify.tagify('inputField').autocomplete({
            source: this.get('source'),
            position: {
                of: tagify.tagify('containerDiv')
            },
            autoFocus: true,
            minLength: 0,
            close: function() {
                tagify.tagify('add');
            }
        });
        tagify.on('tagifyremove',
        function() {
            Em.run.next(self, self.setContent);
        }).on('tagifyadd',
        function() {
            Em.run.next(self, self.setContent);
        });
        this.set('tagify', tagify);
        this.contentDidChange();
    },

    setContent: function() {
        /* Note: we modify the 'content' array in-place, to avoid firing observers and triggering an endless loop. */
        var tags = this.tagify.tagify('serialize').split(',');
        var content = this.get('content');
        Em.assert('Content is not set.', content);
        content.splice(0, content.length);
        tags.forEach(function(tag) {
            if (tag.length > 0) {
                content.pushObject(tag);
            }
        });
    },

    validate: function() {
        this.set('nonExistingTags', null);
        this.set('createdTags', null);
        var content = this.get('content');
        var self = this;
        if (Em.empty(content) == false) {
            return App.Tag.nonExistingTags(content).fail(function(tagNames) {
                self.set('nonExistingTags', tagNames);
            });
        } else {
            return $.Deferred().resolve();
        }
    },

    createNonExistingTags: function() {
        var self = this;
        App.Tag.createTags(this.get('nonExistingTags')).done(function(tagNames) {
            self.set('nonExistingTags', null);
            self.set('createdTags', tagNames);
        });
    },

    clearCreatedTags: function() {
        this.set('createdTags', null);
    }

});