App.Config = Em.Object.extend({

    pageSizes: Em.Object.create({
        analysisIndex: localStorage.getItem('analysisIndexPageSize') || 20,
        tagIndex: localStorage.getItem('tagIndexPageSize') || 20,
        targetIndex: localStorage.getItem('targetIndexPageSize') || 20,
        sampleTypeIndex: localStorage.getItem('sampleTypeIndexPageSize') || 20,
        targetTypeIndex: localStorage.getItem('targetTypeIndexPageSize') || 20
    }),

    chartType: localStorage.getItem('chartType') || 'linear',

    settingDidChange: function() {
        localStorage.setItem('analysisIndexPageSize', this.getPath('pageSizes.analysisIndex'));
        localStorage.setItem('tagIndexPageSize', this.getPath('pageSizes.tagIndex'));
        localStorage.setItem('targetIndexPageSize', this.getPath('pageSizes.targetIndex'));
        localStorage.setItem('sampleTypeIndexPageSize', this.getPath('pageSizes.sampleTypeIndex'));
        localStorage.setItem('targetTypeIndexPageSize', this.getPath('pageSizes.targetTypeIndex'));
        localStorage.setItem('chartType', this.get('chartType'));
    }.observes('pageSizes.analysisIndex', 'pageSizes.targetIndex', 'pageSizes.tagIndex', 'pageSizes.sampleTypeIndex', 'pageSizes.targetTypeIndex', 'chartType')

});

App.config = App.Config.create();
