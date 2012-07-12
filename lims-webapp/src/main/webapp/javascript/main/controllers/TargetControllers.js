App.TargetIndexController = App.IndexController.extend({
    entityName: 'Target',
    pageSizeBinding: 'App.config.pageSizes.targetIndex'
});

App.TargetFormController = App.EntityController.extend({
    entityName: 'Target'
});
