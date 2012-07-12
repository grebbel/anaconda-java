App.TagIndexView = App.MainView.extend({
    layoutName: 'index-layout',
    title: 'Tags',
	templateName: 'admin.tag-index'
});

App.TagFormView = App.FormView.extend({
	templateName: 'admin.tag-form',
	
    validate: function() {
        var self = this;
        var isNameUnique = App.Tag.isNameUnique(this.getPath('controller.content')).fail(function(message) {
            var view = self.viewFor('name');
            view.set('validationMessage', message);
            view.focusOn();
        });
        return $.when(this._super(), isNameUnique);
    }
});
