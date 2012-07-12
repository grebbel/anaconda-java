App.EntitySet = Em.Object.extend({

    entityName: null,

    supportsTagging: true,

    store: function() {
        return App.EntityStore.forEntity(this.get('entityName'));
    }.property('entityClass').cacheable(),

    init: function() {
        this._super();
        this.set('content', Em.ArrayProxy.create());
        if (this.get('pagination') === undefined) {
            this.set('pagination', App.Pagination.create());
        }
        this.set('selectedTags', []);
        this.set('tags', Em.ArrayProxy.create());
    },

    update: function(options) {
        options = $.extend({
            updateTags: false
        }, options);
        if (this.get('supportsTagging') && (options.updateTags || Em.empty(this.getPath('tags.content')))) {
            return $.when(this.get('store').getIndex(this), this.get('store').getIndexTags(this));
        } else {
            return this.get('store').getIndex(this);
        }
    },

    // TODO: Fix this, does not trigger.
    /*tagsDidChange: function() {
        var tags = this.get('tags.content');
        this.set('selectedTags', this.get('selectedTags').filter(function(tag) {
            return tags.contains(tag);
        }))
    }.observes('tags.content')*/

});

App.EntitySetBrowsing = Em.Mixin.create({

    refreshContent: function() {
        this.get('content').update();
    },

    gotoPage: function(page) {
        this.setPath('content.pagination.requestPage', page);
        this.refreshContent();
    },

    selectTags: function(tags) {
        this.setPath('content.selectedTags', tags);
        this.gotoPage(1);
    },

    changePageSize: function(pageSize) {
        this.setPath('content.pagination.pageSize', pageSize);
        this.set('pageSize', pageSize);
        this.gotoPage(1);
    }

});