App.EntityController = Em.Controller.extend({

    content: null,

    entityName: null,

    // TODO: Rename this property to something more specific.
    routerMessage: 'showIndex',

    isNewContent: function() {
        return Em.empty(this.getPath('content.id'));
    }.property().volatile(),

    isExistingContent: function() {
        return this.get('isNewContent') === false;
    }.property().volatile(),

    store: function() {
        return App.EntityStore.forEntity(this.get('entityName'));
    }.property('entityName'),

    saveEntity: function() {
        var store = this.get('store');
        Em.assert('Could not find store.', store !== undefined);
        var deferred;
        if (this.get('isNewContent')) {
            deferred = store.createEntity(this.get('content'));
        } else {
            deferred = store.updateEntity(this.get('content'));
        }
        var self = this;
        return deferred.success(function() {
            App.router.send(self.get('routerMessage'), {
                update: true
            });
        });
    },

    cancelEdit: function() {
        App.router.send(this.get('routerMessage'));
    }

});
