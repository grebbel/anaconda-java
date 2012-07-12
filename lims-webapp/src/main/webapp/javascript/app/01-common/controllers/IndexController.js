App.IndexController = Em.Controller.extend(App.EntitySetBrowsing, {

    entityName: null,

    selectedEntity: null,

    contentDescription: function() {
        return this.getPath('selectedEntity.name');
    }.property('selectedEntity'),

    store: function() {
        return App.EntityStore.forEntity(this.get('entityName'));
    }.property('entityName'),
    
    createEntity: function(event) {
      App.router.send('createEntity');
    },

    deleteEntity: function(event) {
        this.set('selectedEntity', event.context);
        var dialog = this._createDeleteConfirmationDialog(event.context);
        dialog.open();
    },

    _createDeleteConfirmationDialog: function(entity) {
        var self = this;
        var buttons = {};
        buttons['Delete ' + this.get('entityName')] = function() {
            self._doDeleteEntity(entity);
            $(this).dialog('destroy');
        };
        buttons['Cancel'] = function() {
            $(this).dialog('destroy');
        }
        return App.Dialog.create({
            selector: '.entity-delete-confirmation.dialog',
            title: 'Delete ' + this.get('entityName'),
            buttons: buttons
        });
    },

    _doDeleteEntity: function(entity) {
        var store = this.get('store');
        Em.assert('Could not find store', store !== undefined);
        var self = this;
        store.deleteEntity(entity).success(function() {
            self.refreshContent();
        });

    }

});