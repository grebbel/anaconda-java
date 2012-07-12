App.Readyable = Em.Mixin.create({

    whenReady: $.Deferred(),

    whenReadyThen: function(callback) {
        this.setup();
        this.get('whenReady').then(callback);
    },
    
    setup: function() {        
    },
    
    reject: function() {
        this.get('whenReady').reject.apply(this, arguments);
    },

    resolve: function() {
        this.get('whenReady').resolve.apply(this, arguments);
    },

    reset: function() {
        this.set('whenReady', $.Deferred());
    },
    
    deferred: function(def) {
        var self = this;
        def.success(self.resolve()).fail(self.reject);
        return def;
    }

});