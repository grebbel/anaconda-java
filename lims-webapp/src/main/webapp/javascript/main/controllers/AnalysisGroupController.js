App.AnalysisGroupController = Em.Object.extend(App.AnalysisStore, {

    title: null,

    group: null,

    analysesBinding: 'group.analyses',

    allAnalyses: [],

    chartType: 'log',

    active: false,

    /*activeChanged: function() {
        var active = this.get('active');
        var analyses = this.get('analyses');
        if (analyses) {
            analyses.forEach(function(a) {
                a.set('isSelected', active);
            });
        }
    }.observes('active'),*/

    saveChanges: function() {
        return this.commitChanges();
    },

    focusOn: function(analysis) {
        this.get('allAnalyses').forEach(function(a) {
            a.set('isSelected', a === analysis);
        });
    },

    selectAll: function() {
        this.get('analyses').forEach(function(a) {
            a.set('isSelected', true);
        });
    },

    activateAndSelectAll: function() {
        this._activate(true);
    },

    deactivateAndDeselectAll: function() {
        this._activate(false);
    },

    _activate: function(active) {
        var analyses = this.get('analyses');
        if (analyses) {
            analyses.forEach(function(a) {
                a.set('isSelected', active);
            });
        }
        this.set('active', active);
    },

    selectPositive: function(event) {
        event.stopPropagation();
        event.preventDefault();
        this._selectWithStatus('POSITIVE');
    },

    selectNegative: function(event) {
        event.stopPropagation();
        event.preventDefault();
        this._selectWithStatus('NEGATIVE');
    },

    selectIndeterminate: function(event) {
        event.stopPropagation();
        event.preventDefault();
        this._selectWithStatus('INDETERMINATE');
    },

    _selectWithStatus: function(status) {
        this.set('active', true);
        this.get('analyses').forEach(function(a) {
            a.set('isSelected', a.get('status') == status);
        });
    }

});