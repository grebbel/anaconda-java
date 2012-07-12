App.SampleType = App.Entity.extend({
    
    init: function() {
        this._super();
        this.set('codes', this.get('codes') || []);
    }
    
});

App.SampleType.find = function(id) {
    var proxy = App.EntityProxy.create({
        id: id
    });
    App.SampleType.store.getEntity(id, proxy);
    return proxy;
};

App.SampleType.store = App.EntityStore.create(App.EntityProcessing, {
    entityName: 'SampleType',

    basePath: '/sample-types',

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

App.SampleType.isNameUnique = function(sampleType) {
    return App.Validations.isNameUnique(sampleType, App.api.isSampleTypeNameUnique);
};

App.SampleType.areCodesUnique = function(sampleType) {
    return App.Validations.areCodesUnique(sampleType, App.api.areSampleTypeCodesUnique);
}
