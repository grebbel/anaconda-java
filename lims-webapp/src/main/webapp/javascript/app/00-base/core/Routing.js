App.IndexRoute = Em.Route.extend({

    route: '/',

    entityName: null,

    entitySupportsTagging: true,

    viewClass: function() {
        return App[this.get('entityName') + 'IndexView'];
    }.property('entityName'),
    
    controllerName: function() {
        return App.Entity.instanceName(this.get('entityName')) + 'IndexController';
    }.property('entityName'),

    connectOutlets: function(router, options) {
        var viewClass = this.get('viewClass');
        Em.assert('Cannot find viewClass ', viewClass !== undefined);
        var controller = router.get(this.get('controllerName'));
        Em.assert('Cannot find controller ' + this.get('controllerName'), controller !== undefined);

        var content = controller.get('content');
        if (content === undefined) {
            var pageSize = App.getPath('config.pageSizes.' + this.get('entityName').toLowerCase() + 'Index') || 10;
            content = App.EntitySet.create({
                entityName: this.get('entityName'),
                supportsTagging: this.get('entitySupportsTagging'),
                pagination: App.Pagination.create({
                    pageSize: pageSize
                })
            });
        }
        content.update({
            updateTags: true
        }).always(function() {
            router.get('applicationController').connectOutlet({
                viewClass: viewClass,
                controller: controller,
                context: content
            });
        });
    }

});

App.EntityBased = Em.Mixin.create({

    entityClass: function() {
        return App.Entity.entityClassFor(this.get('entityName'));
    }.property('entityName')

});

App.DetailRoute = Em.Route.extend(App.EntityBased, {

    route: '/:id',

    entityName: null,

    waitForLoaded: true,

    connectOutlets: function(router, context) {
        var entityClass = this.get('entityClass');
        Em.assert('Cannot find entity class', entityClass !== undefined);
        var viewClass = this.get('viewClass');
        Em.assert('Cannot find viewClass ', viewClass !== undefined);
        var controller = router.get(this.get('controllerName'));
        Em.assert('Cannot find controller ' + this.get('controllerName'), controller !== undefined);

        if (this.get('waitForLoaded')) {
            entityClass.find(context.id).whenLoaded(function(entity) {
                router.get('applicationController').connectOutlet({
                    viewClass: viewClass,
                    controller: controller,
                    context: entity
                });
            });
        } else {
            router.get('applicationController').connectOutlet({
                viewClass: viewClass,
                controller: controller,
                context: entityClass.find(context.id)
            });
        }
    }
});

App.DisplayBased = Em.Mixin.create({

    viewClass: function() {
        return App[this.get('entityName') + 'DetailView'];
    }.property('entityName'),

    controllerName: function() {
        return  App.Entity.instanceName(this.get('entityName')) + 'DetailController';
    }.property('entityName')

});

App.FormBased = Em.Mixin.create({

    viewClass: function() {
        var viewClass = this.get('entityName') + 'FormView';
        return App[viewClass];
    }.property('entityName'),

    controllerName: function() {
        return  App.Entity.instanceName(this.get('entityName')) + 'FormController';
    }.property('entityName')

});

App.Secured = Em.Mixin.create({

    roles: null,

    enter: function(router) {
        this._super();
        var roles = this.get('roles');
        var userInRole = Em.empty(roles) || roles.some(function(role) {
            return App.user.hasRole(role);
        });
        if (userInRole == false) {
            throw "The current user does not have the required permission.";
        }
    },
});

App.ShowRoute = App.DetailRoute.extend(App.DisplayBased, {
    route: '/:id'
});

App.EditRoute = App.DetailRoute.extend(App.FormBased, {
    route: '/:id/edit'    
});

App.CreateRoute = Em.Route.extend(App.EntityBased, App.FormBased, {
    route: '/new',

    connectOutlets: function(router) {
        var controller = router.get(this.get('controllerName'));
        Em.assert('Cannot find controller', controller !== undefined);
        var viewClass = this.get('viewClass');
        Em.assert('Cannot find viewClass ', viewClass !== undefined);
        router.get('applicationController').connectOutlet({
            viewClass: viewClass,
            controller: controller,
            context: this.createEntity(router)
        });

    },

    createEntity: function(router) {
        return this.get('entityClass').create();
    }
});

App.createEntityRoutes = function(options) {
    options = $.extend(true, {
        roles: [],
        index: true,
        show: true,
        create: true,
        edit: true,
        entitySupportsTagging: true
    },
    options);
    Em.assert('Route not specified.', options.route);
    Em.assert('Entity name not specified.', options.entityName);
    if (Em.typeOf(options.index) == 'boolean' && options.index) {
        options.index = App.IndexRoute.create({
            entityName: options.entityName,
            entitySupportsTagging: options.entitySupportsTagging
        });
    }
    if (Em.typeOf(options.show) == 'boolean' && options.show) {
        options.show = App.ShowRoute.create({
            entityName: options.entityName
        });
    }
    if (Em.typeOf(options.create) == 'boolean' && options.create) {
        options.create = App.CreateRoute.create({
            entityName: options.entityName
        });
    }
    if (Em.typeOf(options.edit) == 'boolean' && options.edit) {
        options.edit = App.EditRoute.create({
            entityName: options.entityName
        });
    }
    var route = Em.Route.create(App.Secured, {
        roles: options.roles,
        route: options.route,
        index: options.index,
        show: options.show,
        create: options.create,
        edit: options.edit
    });
    return route;
};