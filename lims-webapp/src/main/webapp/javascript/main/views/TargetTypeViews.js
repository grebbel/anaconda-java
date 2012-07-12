App.TargetTypeIndexView = App.MainView.extend({
    layoutName: 'index-layout',
	templateName: 'admin.coded-type-index',
    title: 'Target Types',
    entityName: 'Target Type'
});

App.TargetTypeFormView = App.FormView.extend({
	templateName: 'admin.coded-type-form',
	entityName: 'Target Type',
	
    validate: function() {
        var self = this;
        var isNameUnique = App.TargetType.isNameUnique(this.getPath('controller.content')).fail(function(message) {
            var view = self.viewFor('name');
            view.set('validationMessage', message);
            view.focusOn();
        });
        var areCodesUnique = App.TargetType.areCodesUnique(this.getPath('controller.content')).fail(function(message) {
            self.viewFor('codes').set('validationMessage', message).focusOn();
        });
        return $.when(this._super(), isNameUnique, areCodesUnique);
    }
});
