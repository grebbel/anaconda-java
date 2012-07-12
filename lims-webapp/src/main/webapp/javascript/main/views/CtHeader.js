App.CtHeader = Em.View.extend({

    templateName: 'ct-header',

    group: null,

    selected: false,

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
    }.property('group.analyses', 'group.analyses.@each.status').cacheable(),

    click: function() {
        var selected = !this.get('isSelected');
        var controller = this.get('controller');
        this.set('isSelected', selected);
        if (selected) {
            controller.activateAndSelectAll();
        } else {
            controller.deactivateAndDeselectAll();            
        }
    },
    
    mouseOver: false,

    mouseEnter: function() {
        this.set('mouseOver', true);
    },

    mouseLeave: function() {
        this.set('mouseOver', false);
    },

    uiStateClass: function() {
        return this.get('mouseOver') ? 'ui-state-hover': 'ui-state-default';
    }.property('mouseOver', 'selected')

});