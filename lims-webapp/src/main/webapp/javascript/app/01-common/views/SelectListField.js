App.SelectListField = Em.View.extend(App.Validatable, {

    classNames: ['select-list-field'],

    templateName: 'select-list-field',

    size: 10,

    multiple: false,

    items: null,

    selectedItems: null,

    source: null,

    isRequired: false,

    didInsertElement: function() {
        this.sourceDidChange();
    },

    change: function() {
        var values = this.$('select').val() || [];
        this.set('content', values.map(function(value) {
            return Number(value);
        }));
    },

    contentDidChange: function() {
        this._setSelectedItems();
    }.observes('content'),

    validate: function() {
        var valid = this.get('isRequired') == false || Em.empty(this.get('content')) == false;
        var result = $.Deferred();
        if (valid) {
            this.set('validationMessage', null);
            result.resolve();
        } else {
            this.set('validationMessage', 'Select at least 1 item.');
            result.reject();
        }
        return result;
    },

    sourceDidChange: function() {
        if (Em.typeOf(this.get('source') == 'string')) {
            this._getItemsFromSource();
        }
    }.observes('source'),

    _setSelection: function() {
        var content = this.get('content');
        if (Em.empty(content) == false) {
            var values = content.map(function(value) {
                return String(value)
            });
            this.$('select').val(values);
        }
    },

    _setSelectedItems: function() {
        var selectedItems = [];
        this.$('select option:selected').each(function() {
            selectedItems.push({
                value: Number($(this).val()),
                label: $(this).text()
            });
        });
        this.set('selectedItems', selectedItems);
    },

    _getItemsFromSource: function() {
        var self = this;
        $.get(this.get('source'),
        function(data) {
            self.set('items', data);
            Em.run.next(self,
            function() {
                this._setSelection();
                this._setSelectedItems();
            });
        });
    }

})