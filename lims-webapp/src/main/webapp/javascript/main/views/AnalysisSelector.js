App.AnalysisSelector = Em.View.extend({

    templateName: 'analysis-selector',

    classNames: ['analysis-selector'],

    analysis: null,

    tabindex: -1,

    selectedBinding: 'analysis.isSelected',

    didInsertElement: function() {
        this.$('input').prop('checked', this.get('isSelected'));
    },

    selectedDidChange: function() {
        this.$('input').prop('checked', this.get('isSelected'));
    }.observes('selected'),

    change: function() {
        this.set('isSelected', this.$('input').prop('checked'));
    },

    id: function() {
        return 'analysis-selector-' + this.get('elementId');
    }.property('analysis'),

    style: function() {
        var hex = this.getPath('analysis.assayTarget.target.color.hex');
        if (hex) {
            return 'color: #' + hex + ';';
        } else {
            return '';
        }
    }.property()
});