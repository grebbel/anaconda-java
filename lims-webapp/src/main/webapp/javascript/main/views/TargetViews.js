App.TargetIndexView = App.MainView.extend({
    layoutName: 'index-layout',
    title: 'Targets',
    templateName: 'admin.target-index'
});

App.TargetFormView = App.FormView.extend({
    templateName: 'admin.target-form',

    validate: function() {
        var self = this;
        var isNameUnique = App.Target.isNameUnique(this.getPath('controller.content')).fail(function(message) {
            var view = self.viewFor('name');
            view.set('validationMessage', message);
            view.focusOn();
        });
        return $.when(this._super(), isNameUnique);
    }
});