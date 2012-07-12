App.Router = Em.Router.extend({

    //enableLogging: true,
    location: 'hash',

    root: Em.Route.create({

        index: Em.Route.create({
            route: '/',
            redirectsTo: 'dashboard'
        }),

        dashboard: Em.Route.create({
            
            route: '/dashboard',
            
            index: Em.Route.create({
                
                route: '/',
                
                connectOutlets: function(router) {
                    router.get('activityController').startUpdate().done(function() {
                        router.get('applicationController').connectOutlet({
                            viewClass: App.DashboardView,
                            controller: router.get('dashboardController')
                        });                    
                        router.get('dashboardController').connectOutlet({
                            viewClass: App.ActivityFeedView,
                            controller: router.get('activityController'),
                            outletName: 'sidebar'
                        });                        
                    });
                },
                
                exit: function(router) {
                    this._super.apply(this, arguments);                    
                    router.get('activityController').stopUpdate();
                },
                
                createRequest: Em.Route.transitionTo('requests.create'),
                
            })
        }),
        
        tasks: Em.Route.create(App.Secured, {
            roles: ['ROLE_USER'],
            route: '/tasks',
            index: Em.Route.create({
                route: '/',
                connectOutlets: function(router) {
                    router.get('applicationController').connectOutlet({
                        viewClass: App.TaskIndexView,
                        controller: router.get('taskController')
                    });
                    router.get('taskController').update();
                }
            })
        }),

        analyses: App.createEntityRoutes({
            route: '/analyses',
            entityName: 'Analysis',
            show: true,
            edit: false
        }),

        requests: App.createEntityRoutes({
            entityName: 'Request',
            route: '/requests',
            index: false,
            show: false
        }),

        targets: App.createEntityRoutes({
            roles: ['ROLE_ADMIN'],
            route: '/targets',
            entityName: 'Target',
            show: false,
            create: App.CreateRoute.create({
                entityName: 'Target',
                createEntity: function(router) {
                    var target = this._super(router);
                    target.set('tags', router.getPath('targetIndexController.content.selectedTags'));
                    return target;
                }
            })
        }),

        tags: App.createEntityRoutes({
            roles: ['ROLE_ADMIN'],
            route: '/tags',
            entityName: 'Tag',
            entitySupportsTagging: false,
            show: false
        }),

        sampleTypes: App.createEntityRoutes({
            roles: ['ROLE_ADMIN'],
            route: '/sample-types',
            entityName: 'SampleType',
            entitySupportsTagging: false,
            show: false
        }),

        targetTypes: App.createEntityRoutes({
            roles: ['ROLE_ADMIN'],
            route: '/target-types',
            entityName: 'TargetType',
            entitySupportsTagging: false,
            show: false
        }),

        users: App.createEntityRoutes({
           roles: ['ROLE_USER'],
           route: '/users',
           entityName: 'User',
           entitySupportsTagging: false,
           index: false,
           edit: false,
           create: false
        }),

        admin: Em.Route.create(App.Secured, {
            roles: ['ROLE_ADMIN'],
            route: '/admin',
            index: Em.Route.create({
                route: '/',
                connectOutlets: function(router) {
                    router.get('applicationController').connectOutlet({
                        viewClass: App.AdminIndexView,
                        controller: router.get('adminIndexController')
                    });
                }
            }),
            showTags: Em.Route.transitionTo('tags.index'),
            showTargets: Em.Route.transitionTo('targets.index'),
            showSampleTypes: Em.Route.transitionTo('sampleTypes.index'),
            showTargetTypes: Em.Route.transitionTo('targetTypes.index')
        }),

        profile: Em.Route.create(App.Secured, {
            roles: ['ROLE_USER'],
            route: '/profile',
            connectOutlets: function(router) {
                router.get('applicationController').connectOutlet({
                    viewClass: App.ProfileView,
                    context: App.User.getCurrent(),
                    controller: router.get('profileController')
                });
            }
        }),

        profile: Em.Route.create(App.Secured, {
            roles: ['ROLE_USER'],
            route: '/profile',
            connectOutlets: function(router) {
                router.get('applicationController').connectOutlet({
                    viewClass: App.ProfileView,
                    context: App.User.getCurrent(),
                    controller: router.get('profileController')
                });
            }
        }),
        
        // Generic entity transitions:
        showIndex: Em.Route.transitionTo('index'),
        showEntity: Em.Route.transitionTo('show'),
        editEntity: Em.Route.transitionTo('edit'),
        createEntity: Em.Route.transitionTo('create'),
        
        // Other navigation
        editRequest: Em.Route.transitionTo('requests.edit'),

        // Global navigation:
        showDashboard: Em.Route.transitionTo('dashboard.index'),
        showTasks: Em.Route.transitionTo('tasks.index'),
        showAnalyses: Em.Route.transitionTo('analyses.index'),
        showAdmin: Em.Route.transitionTo('admin.index'),
        showProfile: Em.Route.transitionTo('profile'),
        showUser: Em.Route.transitionTo('users.show')

    })
    // root
});