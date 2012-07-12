App.AnalysisIndexController = App.IndexController.extend({
    entityName: 'Analysis',
    pageSizeBinding: 'App.config.pageSizes.analysisIndex'
});

App.AnalysisDetailController = Em.Controller.extend({

    content: null,

    chartTypeBinding: 'App.config.chartType',

    analyses: function() {
        return [this.get('content')];
    }.property('content')

});
