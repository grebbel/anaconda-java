App.Target = App.Entity.extend({

    init: function() {
        this._super();
        this.set('tags', this.get('tags') || []);
        this.set('codes', this.get('codes') || []);
        this.set('targetTypeId', this.getPath('targetType.id') || null);
    }

});

App.Target.find = function(id) {
    return App.EntityStore.find('Target', id);
};

App.Target.store = App.EntityStore.create(App.EntityProcessing, {

    entityName: 'Target',

    basePath: '/targets',

    defaultSort: {
        field: 'name',
        ascending: true
    },

    convertToUpdateData: function(target) {
        return target.getProperties('name', 'description', 'tags', 'codes', 'targetTypeId');
    }

});

App.Target.isNameUnique = function(target) {
    return App.Validations.isNameUnique(target, App.api.isTargetNameUnique);
};
