App.SampleTypeIndexView = App.MainView.extend({
    layoutName: 'index-layout',
    templateName: 'admin.coded-type-index',
    title: 'Sample Types',
    entityName: 'Sample Type'
});

App.SampleTypeFormView = App.FormView.extend({
    templateName: 'admin.coded-type-form',

    validate: function() {
        var self = this;
        var isNameUnique = App.SampleType.isNameUnique(this.getPath('controller.content')).fail(function(message) {
            self.viewFor('name').set('validationMessage', message).focusOn();
        });
        var areCodesUnique = App.SampleType.areCodesUnique(this.getPath('controller.content')).fail(function(message) {
            self.viewFor('codes').set('validationMessage', message).focusOn();
        });
        return $.when(this._super(), isNameUnique, areCodesUnique);
    }
});
