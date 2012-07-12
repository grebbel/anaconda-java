App.AnalysisConfirmationController = Em.Object.extend({

    content: Em.ArrayProxy.create(),

    chartTypeBinding: 'App.config.chartType',

    analysisGroupControllers: function() {
        return this.get('content').map(function(group, index) {
            group.get('analyses').forEach(function(a) {
                a.set('isSelected', index == 0);
            });
            return App.AnalysisGroupController.create({
                parentController: this,
                active: index == 0,
                group: group,
                titleBinding: 'group.title',
                analysesBinding: 'group.analyses',
                allAnalysesBinding: 'parentController.analyses'
            })
        }, this);
    }.property('content.@each').cacheable(),

    analyses: function() {
        var analyses = [];
        this.get('content').forEach(function(group) {
            analyses.pushObjects(group.get('analyses'));
        });
        return analyses;
    }.property('content.@each.analyses').cacheable()
    
});
