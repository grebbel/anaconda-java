App.User = Em.Object.extend({

    email: null,

    roles: [],

    name: Em.Object.extend({
        firstName: null,
        prefix: null,
        lastName: null
    }),

    hasRole: function(roleName) {
        var roles = this.get('roles');
        var hasRole = false;
        if (Em.empty(roles) == false) {
            hasRole = roles.some(function(role) {
                return role.name == roleName || 'ROLE_' + role.name.toUpperCase() == roleName;
            });
        }
        return hasRole;
    },
    
    hasUserRole: function() {
        return this.hasRole('ROLE_USER');
    }.property().volatile(),

    hasAdminRole: function() {
        return this.hasRole('ROLE_ADMIN');
    }.property().volatile()

});

App.User.find = function(id) {
    return App.EntityStore.find('User', id);
};

App.User.getCurrent = function() {
    var proxy = App.EntityProxy.create();
    App.api.getCurrentUser(proxy);
    return proxy;
};

App.User.store = App.EntityStore.create({
    
    entityName: 'User',
    
    basePath: '/users'
    
});