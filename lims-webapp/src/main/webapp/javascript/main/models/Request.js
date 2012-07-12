App.Request = App.Entity.extend({
    
    init: function() {
        this._super();
        this.set('date', new Date().getTime());
        this.set('sampleTypeId', this.getPath('sampleType.id') || null);
        var targets = this.get('targets');
        if (Em.empty(targets) == false) {
            this.set('targetIds', targets.map(function(target) {
                return Em.get(target, 'id');
            }));
        }
    }
    
});

App.Request.find = function(id) {
    return App.EntityStore.find('Request', id);
};

App.Request.store = App.EntityStore.create(App.EntityProcessing, {
    
    entityName: 'Request',
    
    basePath: '/requests',
    
    defaultSort: {
        field: 'date',
        ascending: false
    },
    
    convertToUpdateData: function(request) {
        return request.getProperties('externalId', 'description', 'date', 'targetIds', 'sampleTypeId');
    }
    
});
