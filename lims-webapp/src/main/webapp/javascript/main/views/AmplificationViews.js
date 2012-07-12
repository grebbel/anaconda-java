App.AmplificationTable = Em.View.extend({

    templateName: 'amplification-table',

    classNames: 'amplification',

    sectionSize: 15,

    sectionCount: function() {
        var content = this.get('content')
        if (Em.empty(content)) {
            return 0;
        } else {
            return Math.ceil(content.length / this.get('sectionSize'));
        }
    }.property('content'),

    sections: function() {
        var content = this.get('content');
        var sections = [];
        if (Em.empty(content) == false) {
            for (var i = 0, n = this.get('sectionCount'); i < n; i++) {
                var offset = i * this.get('sectionSize');
                var count = Math.min(content.length - offset, this.get('sectionSize'));
                sections.push({
                    content: content.slice(offset, offset + count)
                });
            }
        }
        if (sections.length > 1) {
            sections[sections.length - 1].last = true;
        }
        return sections;
    }.property('content').cacheable()

});


App.AmplificationRow = Em.View.extend({

    analysis: null,

    tagName: 'tr',

    beforeTreshold: function() {
        var ct = this.getPath('analysis.ct.value') || 0;
        var delta = Math.min(ct - this.getPath('content.cycle'), 1.0);
        return (delta > 0) && (delta < 1.0);
    }.property('analysis.ct').cacheable(),

    afterTreshold: function() {
        var ct = this.getPath('analysis.ct.value') || 0;
        var delta = Math.min(ct - this.getPath('content.cycle'), 1.0);
        return (delta > -1.0) && (delta < 0);
    }.property('analysis.ct').cacheable(),

    status: function() {
        if (this.get('beforeTreshold') || this.get('afterTreshold')) {
            return (this.getPath('analysis.status') || '').toLowerCase();
        }
    }.property('beforeTreshold', 'afterTreshold', 'analysis.status')

});