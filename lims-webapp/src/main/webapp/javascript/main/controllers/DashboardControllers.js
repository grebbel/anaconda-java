App.DashboardController = Em.Controller.extend({

    createRequest: function() {
        App.router.send('createRequest');
    }
    
});