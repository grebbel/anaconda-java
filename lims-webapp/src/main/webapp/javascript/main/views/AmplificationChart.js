App.AmplificationChart = Em.View.extend({

    templateName: 'amplification-chart',
    
    analyses: [],
    
    type: 'linear',

    width: 926,

    height: 675 - 30,

    legend: true,

    message: 'Loading amplification chart.',

    isEmpty: false,

    isShowAlways: true,
    
    isAnimated: false,

    _imageLoaded: false,
    
    isShow: function() {
        return this.get('isShowAlways') || (this.get('isEmpty') == false);
    }.property('isShowAlways', 'isEmpty'),

    src: function() {
        var uri = 'amplification-chart';
        if (uri) {
            var params = this.getParameters(this.get('selectedAnalyses'));
            uri += '?' +  $.param(params);
        }
        return uri;
    }.property('analyses.@each.isSelected', 'analyses.@each.treshold', 'analyses.@each.status', 'type'),

    getParameters: function(analyses) {
        var params = {
            type: this.get('type'),
            width: this.get('width'),
            height: this.get('height'),
            legend: this.get('legend'),
            analyses: analyses.map(function(a) {
                return a.get('id')
            }),
            tresholds: analyses.map(function(a) {
                return a.get('treshold')
            }),
            status: analyses.map(function(a) {
                return a.get('status')
            })
        };
        if (params.type == 'linear') {
            params.rangeMin = -1.0;
            params.rangeMax = 1.0;
        }
        return params;
    },

    selectedAnalyses: function() {
        return this.get('analyses').filterProperty('isSelected');
    }.property('analyses', 'analyses.@each.isSelected'),

    analysisSelectionDidChange: function() {
        this.set('isEmpty', Em.empty(this.get('selectedAnalyses')));
    }.observes('analyses', 'analyses.@each.isSelected'),

    didInsertElement: function() {
        var self = this;
        this.$('img').hide().imagesLoaded(function() {
            self.set('_imageLoaded', true);
        });
        this._configureDisplay();
    },

    displayPropertyHasChanged: function() {
        this._configureDisplay();
    }.observes('width', 'height', '_imageLoaded'),

    _configureDisplay: function() {
        var placeholder = this.$('.placeholder')
        placeholder.css('width', this.get('width')).css('height', this.get('height') + 2);
        if (this.get('_imageLoaded')) {
            placeholder.hide();
            if (this.get('isAnimated')) {
                this.$('img').show('slide', this.get('_slideOptions'));
            } else {
                this.$('img').show();
            }
        } else {
            placeholder.show();
            this.$('img').hide();
        }
    },

    typeDidChange: function() {
        var self = this;
        if (this.get('isAnimated')) {
            this.$('img').hide('slide', this.get('_slideOptions'),
            function() {
                self.set('_imageLoaded', false);
                $.timeout(500).then(function() {
                    self.$('img').imagesLoaded(function() {
                        self.set('_imageLoaded', true);
                    });
                })
            });
        } else {
            this.set('_imageLoaded', false);
            $.timeout(500).then(function() {
                self.$('img').imagesLoaded(function() {
                    self.set('_imageLoaded', true);
                });
            });
        }
    }.observes('type'),

    _slideOptions: function() {
        return {
            direction: this.get('type') == 'linear' ? 'left': 'right',
            easing: 'easeInOutQuart'
        };
    }.property('type')

});