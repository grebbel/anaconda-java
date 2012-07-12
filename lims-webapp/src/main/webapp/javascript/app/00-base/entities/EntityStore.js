App.EntityStore = Em.Object.extend({

    /* Properties */

    baseUri: 'api/v1',

    entityName: null,

    defaultPagination: App.Pagination.NO_OP,

    indexPath: null,

    defaultTags: [],

    defaultSort: {},

    defaultOptions: function() {
        return {
            pagination: this.get('defaultPagination'),
            tags: this.get('defaultTags'),
            sort: this.get('defaultSort')
        };
    }.property('defaultPagination', 'defaultTags', 'defaultSort').cacheable(),

    /* Index operations */

    getIndex: function(receiver, options) {
        if (receiver instanceof App.EntitySet) {
            options = $.extend(true, options, {
                pagination: receiver.get('pagination'),
                tags: receiver.get('selectedTags'),
            });
            receiver = receiver.get('content');
        }
        options = $.extend(true, $.extend(true, {},
        this.get('defaultOptions')), options);
        var self = this;
        return $.get(this.get('indexPath'), this._createParams(options)).success(function(data) {
            receiver.set('content', self.deserializeEntities(data));
            self.populatePagination(options.pagination, data);
        });
    },

    getIndexTags: function(receiver) {
        if (receiver instanceof App.EntitySet) {
            receiver = receiver.get('tags');
        }
        return $.get(this.get('tagsPath')).success(function(data) {
            /*var tags = data.map(function(tag) {
                return tag.name;
            });*/
            receiver.set('content', data);
        });
    },

    /* Entity operations */

    getEntity: function(id, receiver) {
        var self = this;
        return $.get(this._detailPath(id)).success(function(data) {
            receiver.set('content', self.deserializeEntity(data));
        });
    },

    updateEntity: function(entity) {
        var data = entity;
        if (App.EntityProcessing.detect(this)) {
            data = this.convertToUpdateData(entity);
        }
        return $.ajax({
            type: 'PUT',
            url: this._detailPath(entity.get('id')),
            contentType: 'application/json',
            data: JSON.stringify(data || entity)
        });
    },

    createEntity: function(entity) {
        var data;
        if (App.EntityProcessing.detect(this)) {
            data = this.convertToUpdateData(entity);
        }
        return $.ajax({
            type: 'POST',
            url: this.get('indexPath'),
            contentType: 'application/json',
            data: JSON.stringify(data || entity)
        });
    },

    deleteEntity: function(id) {
        id = Em.get(id, 'id') || id;
        return $.ajax({
            type: 'DELETE',
            url: this._detailPath(id)
        });
    },

    /* Factory operations. Can be overridden. */

    deserializeEntities: function(data) {
        if (App.EntityProcessing.detect(this)) {
            data = this.processIndexData(data);
        }
        var self = this;
        return data.items.map(function(item) {
            return self.deserializeEntity(item);
        });
    },

    deserializeEntity: function(data) {
        if (App.EntityProcessing.detect(this)) {
            data = this.processEntityData(data);
        }
        var cls = this.get('entityClass');
        var entity;
        if (cls) {
            entity = cls.create(data);
        } else {
            entity = Em.Object.create(data);
        }
        if (App.EntityProcessing.detect(this)) {
            entity = this.processEntity(entity);
        }
        return entity;
    },

    populatePagination: function(pagination, data) {
        pagination.populateFrom(data.metadata);
    },

    /* Path and query operations */

    indexPath: function() {
        return 'api/v1' + this.get('basePath');
    }.property('entityName'),

    tagsPath: function() {
        return this.get('indexPath') + '/tags'
    }.property('indexPath'),

    entityClass: function() {
        return App.Entity.entityClassFor(this.get('entityName'));
    }.property('entityName'),

    _detailPath: function(id) {
        return this.get('indexPath') + '/' + id;
    },

    _createParams: function(options) {
        options = $.extend(true, {
            pagination: App.Pagination.NO_OP,
            tags: []
        },
        options);
        var params = {
            'page.page': options.pagination.get('requestPage'),
            'page.size': options.pagination.get('pageSize') || App.config.get('pageSize'),
            tags: options.tags
        };
        if (options.sort) {
            params['page.sort'] = options.sort.field;
            if (options.sort.ascending === false) {
                params['page.sort.dir'] = 'desc';
            }
        }
        return params;
    }

});

App.EntityStore.forEntity = function(entityName) {
    var cls = App.Entity.entityClassFor(entityName);
    if (cls && cls.store) {
        return cls.store;
    } else {
        return undefined;
    }
};

App.EntityStore.find = function(entityName, id) {
    var proxy = App.EntityProxy.create({
        id: id
    });
    var store = App.EntityStore.forEntity(entityName);
    Em.assert('Could not find store for entity ' + entityName, store);
    store.getEntity(id, proxy);
    return proxy;
}
    
/* Mixin for customizing entity processing */

App.EntityProcessing = Em.Mixin.create({

    processIndexData: function(data) {
        return data;
    },

    processEntityData: function(data) {
        if (data['date']) {
            var date = new Date(data.date);
            data.date = date;
            data.displayDate = date.toDateString();
        };
        return data;
    },

    processEntity: function(entity) {
        return entity;
    },

    convertToUpdateData: function(entity) {
        return entity;
        /*
        // http://stackoverflow.com/questions/8669340/ember-model-to-json
        var v,
        ret = [];
        for (var key in this) {
            if (this.hasOwnProperty(key)) {
                v = this[key];
                if (v === 'toString') {
                    continue;
                }
                // ignore useless items
                if (Ember.typeOf(v) === 'function') {
                    continue;
                }
                ret.push(key);
            }
        }
        return entity.getProperties(ret);*/
    }

});

