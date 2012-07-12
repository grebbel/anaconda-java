App.Waitable = Em.Mixin.create({

    _waitTimeout: null,

    waitFor: function(delay) {
        this.cancelWait();
        var timeout = $.timeout(delay);
        this.set('_waitTimeout', timeout);
        return timeout;
    },

    cancelWait: function() {
        var timeout = this.get('_waitTimeout');
        if (timeout) {
            timeout.clear();
            this.set('_waitTimeout', null);
        }
    }

});