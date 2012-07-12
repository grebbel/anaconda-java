App.AnalysisToggleSelector = Em.View.extend({
    
    templateName: 'analysis-toggle-selector',
    
    classNames: [ 'analysis-toggle-selector' ],
    
    label: null,
    
    analyses: [ ],
    
    tabIndex: -1,
    
    selected: true,
    
    allSelected: function() {
        var allSelected = true;
        this.get('analyses').forEach(function(a) {
           if (a.get('isSelected') == false) {
               allSelected = false;
           }
        });
        return allSelected;
    }.property('analyses', 'analyses.@each.isSelected'),
    
    allSelectedDidChange: function() {
        this.set('isSelected', this.get('allSelected'));
    }.observes('allSelected'),
    
    didInsertElement: function() {
        this.$('input').prop('checked', this.get('allSelected'));
    },

    selectedDidChange: function() {
        this.$('input').prop('checked', this.get('isSelected'));
    }.observes('selected'),
    
    change: function() {
        this.set('isSelected', this.$('input').prop('checked'));
        this._toggleAnalysisSelection();
    },
    
    _toggleAnalysisSelection: function() {
        var select = this.get('isSelected') && (!this.get('allSelected'));
        this.get('analyses').forEach(function(a) {
           a.set('isSelected', select); 
        });
    },

    id: function() {
        return 'analysis-toggle-selector-' + this.get('elementId');
    }.property('_parentView.contentIndex')

});