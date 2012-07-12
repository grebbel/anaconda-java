App.Number = Em.View.extend({
    
    tagName: 'span',

    template: Em.Handlebars.compile('<span {{bindAttr class="view.statusClass"}} >{{view._formattedValue}}'),

    format: '0.00',

    value: null,

    status: null,

    statusClass: function() {
        return (this.get('status') || '').toLowerCase();
    }.property('status'),

    _formattedValue: function() {
        var value = this.get('value');
        if (value) {
            value = $.format.number(value, this.get('format'));
        }
        return value;
    }.property('value')

});

App.CtNumber = App.Number.extend({

    ct: null,

    generating: false,

    template: Em.Handlebars.compile(
    '<div class="ct value">{{view._formattedValue}}</div>' +
    '<div class="small active generator icon"></div>'
    ),

    value: function() {
        var ct = this.get('ct');
        if (ct) {
            return ct.value;
        } else {
            return null;
        }
    }.property('ct'),

    generatingDidChange: function() {
        this._setVisibility();
    }.observes('generating'),

    didInsertElement: function() {
        this._setVisibility();
    },

    _setVisibility: function() {
        if (this.get('generating')) {
            this.$('.generator').show();
            this.$('.value').hide();
        } else {
            this.$('.value').show();
            this.$('.generator').hide();
        }
    }

});