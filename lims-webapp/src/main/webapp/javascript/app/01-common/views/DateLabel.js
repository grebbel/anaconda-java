App.DateLabel = Em.View.extend(App.AutoUpdatable, {

    tagName: 'span',

    templateName: 'date-label',

    classNames: 'date-label',

    updateInterval: 1000 * 10,

    isShowRelative: true,

    isShowAbsolute: false,

    // Surrogate property to trigger an update of "relative" during an auto-update cycle.
    _sequence: 0,

    // Saved "relative" value, used to avoid unnecessary updates.
    _relative: null,

    relative: function() {
        var relative = this._getRelative();
        this.set('_relative', relative);
        return relative;
    }.property('content', '_sequence'),

    _getRelative: function() {
        return moment(this.get('content')).from(moment());
    },

    absolute: function() {
        return moment(this.get('content')).format('LLLL');
    }.property('content'),

    didInsertElement: function() {
        this.set('isAutoUpdate', true);
        this.set('updateInterval', (60 - moment().seconds()) * 1000);
    },

    update: function() {
        if (this.get('_relative') !== this._getRelative()) {
            // Update the sequence so that we trigger an update of "relative".
            this.set('_sequence', this.get('_sequence') + 1);
        }
        this.set('updateInterval', (60 - moment().seconds()) * 1000);        
    },

    willDestroyElement: function() {
        // Call clearUpdateTimeout to avoid waiting for the next run loop.
        this.clearUpdateTimeout();
        this.set('isAutoUpdate', false);
    }

});