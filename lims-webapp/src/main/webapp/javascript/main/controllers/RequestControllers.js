App.RequestFormController = App.EntityController.extend({

    entityName: 'Request',
    
    routerMessage: 'showDashboard',

    generateExternalId: function() {
        this.setPath('content.externalId', App.UUID.generate());
    }
    
});
