App.AnalysisStatus = Em.View.extend({

    tagName: 'span',
    
    templateName: 'analysis-status',
    
    classNames: ['analysis-status'],
    
    statusBinding: 'analysis.status',
    
    positive: function() {
      return (this.get('status') == 'POSITIVE');  
    }.property('status'),
    
    negative: function() {
      return (this.get('status') == 'NEGATIVE');
    }.property('status'),

    indeterminate: function() {
      return (this.get('status') == 'INDETERMINATE');
    }.property('status'),

    change: function() {
        this.set('status', this.$('input:checked').val());
        this.setPath('analysis.isSelected', true);
    },

    name: function() {
        return 'analysis-confirmation-' +  this.get('elementId');
    }.property('analysis'),

    positiveId: function() {
        return 'analysis-confirmation-positive-' + this.get('elementId');
    }.property('analysis'),
    
    negativeId: function() {
        return 'analysis-confirmation-negative-' + this.get('elementId');
    }.property('analysis')
    
    
});