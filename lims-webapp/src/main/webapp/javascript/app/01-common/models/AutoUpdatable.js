App.AutoUpdatable = Em.Mixin.create({

    updateInterval: 0,

    isAutoUpdate: false,

    update: Em.required(Function),

    updateIntervalDidChange: function() {
        this.setUpdateTimeout();
    }.observes('updateInterval'),

    isAutoUpdateDidChange: function() {
        if (this.get('isAutoUpdate')) {
            this.setUpdateTimeout();
        } else {
            this.clearUpdateTimeout();
        }
    }.observes('isAutoUpdate'),

    clearUpdateTimeout: function() {
        if (this.timeout) {
            this.timeout.clear();
            this.timeout = null;
        }
    },

    setUpdateTimeout: function() {
        this.clearUpdateTimeout();
        if (this.get('isAutoUpdate') == false) {
            return;
        }
        var interval = this.get('updateInterval');
        if (interval > 0) {
            var self = this;
            this.timeout = $.timeout(interval).then(function() {
                self.update();
            });
        }
    }

});