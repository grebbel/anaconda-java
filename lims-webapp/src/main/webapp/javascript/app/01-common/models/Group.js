App.Group = Em.Object.extend({

    data: undefined,

    itemClass: undefined,
    
    itemProperty: null,

    title: null,

    subgroups: [],

    init: function() {
        this._initializeGroup();
    },

    _initializeGroup: function() {
        var data = this.get('data');
        var itemClass = this.get('itemClass');
        var itemProperty = this.get('itemProperty') || 'items';
        this.set('title', data.title);
        if (Em.empty(data.items) == false) {
            this.set(itemProperty, data.items.map(function(item) {
                return itemClass.create(item);
            }));
        }
        if (Em.empty(data.subgroups) == false) {
            this.set('subgroups', data.subgroups.map(function(subgroup) {
                return App.Group.create({
                    data: group,
                    itemClass: itemClass
                });
            }));
        }
        this.set('data', undefined);
        this.set('itemClass', undefined);
        return this;
    }

});