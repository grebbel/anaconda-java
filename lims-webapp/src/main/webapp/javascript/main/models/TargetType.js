App.TargetType = App.Entity.extend({
    
    init: function() {
        this._super();
        this.set('codes', this.get('codes') || []);
    }
    
});

App.TargetType.find = function(id) {
    var proxy = App.EntityProxy.create({
        id: id
    });
    App.TargetType.store.getEntity(id, proxy);
    return proxy;
};

App.TargetType.store = App.EntityStore.create(App.EntityProcessing, {
    entityName: 'TargetType',

    basePath: '/target-types',

    // not supported
    getIndexTags: undefined,

    defaultSort: {
        field: 'name',
        ascending: true
    },

    convertToUpdateData: function(sampleType) {
        return sampleType.getProperties('name', 'description', 'codes');
    }
});

App.TargetType.isNameUnique = function(targetType) {
    return App.Validations.isNameUnique(targetType, App.api.isTargetTypeNameUnique);
};

App.TargetType.areCodesUnique = function(targetType) {
    return App.Validations.areCodesUnique(targetType, App.api.areTargetTypeCodesUnique);
}