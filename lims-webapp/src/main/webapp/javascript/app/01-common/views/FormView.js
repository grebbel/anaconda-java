App.FormView = App.MainView.extend(App.Validatable, {

    layoutName: 'form-layout',

    entityName: function() {
        return this.getPath('controller.entityName');
    }.property('controller.entityName'),

    title: function() {
        return this.getPath('controller.content.name') || '<Untitled>';
    }.property('controller.content.name'),

    validatableChildViews: function() {
        return this.get('childViews').filter(function(view) {
            return App.Validatable.detect(view);
        });
    }.property('childViews', 'childViews.@each'),

    submit: function() {
        var self = this;
        this.validate().done(function() {
            self.get('controller').saveEntity();
        });
        return false;
    },

    viewFor: function(name) {
        return this.get('childViews').find(function(view) {
            return view.get('name') === name;
        });
    },

    validate: function() {
        var views = this.get('validatableChildViews');
        if (Em.empty(views) == false) {
            var promises = views.map(function(view) {
                return view.validate();
            });
            return $.when.apply(this, promises).fail(function() {
                views[0].focusOn();
            });
        } else {
            return $.Deferred().resolve().promise();
        }
    }

});
