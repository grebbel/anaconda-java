App.TagIndexController = App.IndexController.extend({
    entityName: 'Tag',
    pageSizeBinding: 'App.config.pageSizes.tagIndex'
});

App.TagFormController = App.EntityController.extend({
    entityName: 'Tag'
});
 