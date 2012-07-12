App.TargetTypeIndexController = App.IndexController.extend({
    entityName: 'TargetType',
    pageSizeBinding: 'App.config.pageSizes.targetTypeIndex'
});

App.TargetTypeFormController = App.EntityController.extend({
    entityName: 'TargetType'
});
 