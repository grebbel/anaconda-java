App.Entity = Em.Object.extend({

    isModified: false,

    isSelected: false,

    isLoaded: true

});

App.Entity.entityClassFor = function(entityName) {
    var cls = App[Em.String.classify(entityName)]
    if (Em.typeOf(cls) == 'class') {
        return cls;
    } else {
        return undefined;
    }
};

App.Entity.instanceName = function(entityName) {
    return entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
};

App.EntityProxy = Em.ObjectProxy.extend({

    id: null,

    isLoaded: false,

    init: function() {
        this._super();
        this._loaded = $.Deferred();
    },

    contentDidChange: function() {
        this.set('isLoaded', Em.empty(this.get('content')) == false);
        this.set('id', this.getPath('content.id'));
    }.observes('content'),
    
    whenLoaded: function(callback) {
        this._loaded.done(callback);
    },

    isLoadedDidChange: function() {
        if (this.get('isLoaded')) {
            this._loaded.resolve(this);
        }
    }.observes('isLoaded')

});
