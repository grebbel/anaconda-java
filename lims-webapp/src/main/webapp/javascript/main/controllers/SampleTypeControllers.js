App.SampleTypeIndexController = App.IndexController.extend({
    entityName: 'SampleType',
    pageSizeBinding: 'App.config.pageSizes.sampleTypeIndex'
});

App.SampleTypeFormController = App.EntityController.extend({
    entityName: 'SampleType'
});
 