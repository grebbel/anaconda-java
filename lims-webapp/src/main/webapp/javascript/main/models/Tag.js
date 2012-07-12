App.Tag = App.Entity.extend();

App.Tag.find = function(id) {
    return App.EntityStore.find('Tag', id);
};

App.Tag.store = App.EntityStore.create(App.EntityProcessing, {

    entityName: 'Tag',

    basePath: '/tags',

    // Not supported.
    getIndexTags: undefined,

    defaultSort: {
        field: 'name',
        ascending: true
    },

    convertToUpdateData: function(tag) {
        return tag.getProperties('name', 'category', 'description');
    }

});

App.Tag.isNameUnique = function(tag) {
    return App.Validations.isNameUnique(tag, App.api.isTagNameUnique);
};

App.Tag.nonExistingTags = function(tagNames) {
    return $.Deferred(function() {
        var self = this;
        App.api.getNonExistingTagNames(tagNames).success(function(names) {
            if (Em.empty(names)) {
                self.resolve();
            } else {
                self.reject(names);
            }
        }).fail(function() {
            self.resolve();
        });
    }).promise();
}


App.Tag.createTags = function(tagNames) {
    return $.post('tags', JSON.stringify(tagNames));
};