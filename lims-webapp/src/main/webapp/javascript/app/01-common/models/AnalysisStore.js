App.AnalysisStore = Em.Mixin.create({

    analysisSettingModified: false,
    
    committing: false,

    _originalAnalyses: [],
    
    revertChanges: function() {
        var originals = this.get('_originalAnalyses');
        var analyses = this.get('analyses');
        // Synchronize current 'selected' state.        
        originals.forEach(function(a) {
            var b = analyses.find(function(b) {
                return a.get('id') == b.get('id');
            });
            if (b) {
                a.set('isSelected', b.get('isSelected'));
            }
        });
        this.set('analyses', originals);
    },
    
    commitChanges: function() {
        var analyses = this.get('analyses').filter(function(a) {
            return a.get('isModified');
        });
        this.set('committing', true);
        var self = this;
        return App.api.updateAnalysisStatus(analyses).success(function() {
            self._copyAnalyses(self.get('analyses'));
            self.set('analysisSettingModified', false);
        }).done(function() {
            self.set('committing', false);
        });
    },
    
    analysesDidChange: function() {
        var analyses = this.get('analyses');
        if (analyses) {
            this._copyAnalyses(analyses);
        }
        this.set('analysisSettingModified', false);
    }.observes('analyses'),
    
    /*analysisSelectedDidChange: function() {
        this.get('_originalAnalyses').find(function(a) {
                        
        })
        console.log(arguments);
    }.observes('analyses.@each.isSelected'),*/
    
    _copyAnalyses: function(analyses) {
        this.set('_originalAnalyses', analyses.map(function(a) {
            return Em.copy(a);
        }));
    },

    analysisStatusDidChange: function() {
        this.set('analysisSettingModified', true);
    }.observes('analyses.@each.status', 'analyses.@each.treshold')

});
