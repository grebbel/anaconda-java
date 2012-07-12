App.ActivityController = Em.Controller.extend(App.RestApiCapable, App.AutoUpdatable, {

    isShowMinor: false,

    updateInterval: 1000 * 20,

    eTag: null,

    init: function() {
        this._super();
        this.set('content', Em.ArrayProxy.create());
    },

    update: function() {
        options = {};
        if (moment !== undefined) {
            // These default values use the momentjs library.
            options = $.extend(true, {
                /*from: moment().sod(),
                to: moment().eod(),*/
            },
            options);
        }
        options = $.extend(true, {
            importance: this.get('isShowMinor') ? -1: 0
        },
        options);
        var params = options.from && options.to ? this._dateRange(options.from, options.to) : {};
        params.importance = options.importance;
        params['page.page'] = 1;
        params['page.size'] = 15;
        var self = this;
        return $.get(this._uri('activities'), params).done(function(data, message, xhr) {
            if (xhr.status == 200) {
                var eTag = xhr.getResponseHeader('ETag');
                if (self.get('eTag') != eTag) {
                    self.setPath('content.content', data.items.map(function(item) {
                        item.templateName = 'activity.' + item.message;
                        return Em.Object.create(item);
                    }));
                }
                self.set('eTag', eTag);
            }
        }).always(function() {
            self.setUpdateTimeout();
        });
    },

    startUpdate: function() {
        this.set('eTag', null);
        this.set('isAutoUpdate', true);
        this.setPath('content.content', null);
        return this.update();
    },

    stopUpdate: function() {
        this.set('isAutoUpdate', false);
    },

    toggleShowMinor: function() {
        this.set('isShowMinor', !this.get('isShowMinor'));
        this.update();
    },

    _dateRange: function(from, to) {
        if (Em.typeOf(from.toDate) == 'function') {
            from = from.toDate();
        }
        if (Em.typeOf(to.toDate) == 'function') {
            to = to.toDate();
        }
        var range = {};
        if (Em.typeOf(from.getTime) == 'function') {
            range.from = from.getTime();
        }
        if (Em.typeOf(to.getTime) == 'function') {
            range.to = to.getTime();
        }
        return range;
    },

});