App.SelectField = Em.View.extend(App.Validatable, {

    classNames: ['select-field'],

    templateName: 'select-field',

    items: null,

    source: null,

    selectLabel: '<Select>',

    isRequired: false,

    didInsertElement: function() {
        this.sourceDidChange();
    },

    change: function() {
        this.set('content', this.$('select').val() || null);
    },

    validate: function() {
        var valid = this.get('isRequired') == false || Em.empty(this.get('content')) == false;
        var result = $.Deferred();
        if (valid) {
            this.set('validationMessage', null);
            result.resolve();
        } else {
            this.set('validationMessage', 'Select an item.');
            result.reject();
        }
        return result;
    },

    sourceDidChange: function() {
        if (Em.typeOf(this.get('source') == 'string')) {
            this._getItemsFromSource();
        }
    }.observes('source'),

    contentDidChange: function() {
        this._setSelectedItem();
    }.observes('content'),
    
    _setSelectedItem: function() {
        var content = this.get('content');
        if (Em.empty(content) == false) {
            this.$('select').val(String(content));                
        }        
    },

    _getItemsFromSource: function() {
        var self = this;
        $.get(this.get('source'),
        function(data) {
            self.set('items', data);
            Em.run.next(self, function() {
                this._setSelectedItem();
            });
        });
    },
    
    disabled: function() {
        return Em.empty(this.get('items'));
    }.property('items')

})