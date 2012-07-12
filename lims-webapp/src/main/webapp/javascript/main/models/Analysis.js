App.Analysis = App.Entity.extend(Em.Copyable, {

    generatingCt: false,
    
    isSelected: true,
    
    copy: function() {
        return App.Analysis.create(this);
    },

    tresholdDidChange: function() {
        this._calculateCt();
    }.observes('treshold'),

    ctOrTresholdDidChange: function() {
        this.set('status', 'INDETERMINATE');
    }.observes('ct', 'treshold'),

    confirmationRelatedSettingDidChange: function() {
        this.set('isModified', true);
    }.observes('ct', 'treshold', 'status'),

    _calculateCt: function() {
        this._cancelPreviousCalculation();
        var self = this;
        var generating = $.timeout(300);
        generating.done(function() {
            self.set('generatingCt', true);
        });
        var post = App.api.calculateCts([this], this.get('treshold')).success(function(data) {
            var ct = null;
            if (data.ctCalculations.length > 0) {
                ct = data.ctCalculations[0].ct;
            }
            self.set('ct', ct);
        }).always(function() {
            self.set('_calculation', null);
            generating.clear();
            self.set('generatingCt', false);
        });
        this.set('_calculation', {
            post: post,
            generating: generating
        });
    },

    _cancelPreviousCalculation: function() {
        var _calculation = this.get('_calculation');
        if (_calculation) {
            _calculation.post.abort();
            _calculation.generating.clear();
            this.set('_calculation', null);
        }
    },

});

App.Analysis.find = function(id) {
    return App.EntityStore.find('Analysis', id);
};

App.Analysis.store = App.EntityStore.create(App.EntityProcessing, {
    
    entityName: 'Analysis',
    
    basePath: '/analyses',
    
    defaultSort: {
        field: 'date',
        ascending: false
    },
    
    processEntity: function(analysis) {
        analysis.set('request', App.Request.create({
            id: analysis.get('requestId'),
            description: analysis.get('requestDescription'),
        }));
        return analysis;
    },

});
