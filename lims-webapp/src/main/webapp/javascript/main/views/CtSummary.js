App.CtSummary = Em.View.extend({

    templateName: 'ct-summary',

    group: null,

    positiveCount: function() {
        return this.getPath('group.analyses').filter(function(a) {
            return a.get('status') == 'POSITIVE';
        }).length;
    }.property('group.analyses', 'group.analyses.@each.status').cacheable(),

    negativeCount: function() {
        return this.getPath('group.analyses').filter(function(a) {
            return a.get('status') == 'NEGATIVE';
        }).length;
    }.property('group.analyses', 'group.analyses.@each.status').cacheable(),

    indeterminateCount: function() {
        return this.getPath('group.analyses').filter(function(a) {
            return a.get('status') == 'INDETERMINATE';
        }).length;
    }.property('group.analyses', 'group.analyses.@each.status').cacheable()    
    
});