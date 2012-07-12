App.TextField = Em.View.extend(App.Validatable, {

    tagName: 'span',

    classNames: ['text-field'],

    templateName: 'text-field',

    size: 50,

    maxLength: 255,

    nullable: false,

    focus: false,

    message: null,

    readonly: false,

    widget: function() {
        return this.$('input');
    }.property().volatile(),

    autofocus: function() {
        return this.get('focus') ? 'autofocus': null;
    }.property('focus'),

    didInsertElement: function() {
        var self = this;
        if (this.get('validateOnBlur')) {
            this.get('widget').on('blur',
            function(event) {
                self.validate();
            });
        }
    },

    change: function() {
        var value = this.$('input').val();
        if (this.get('nullable') && Em.empty(value)) {
            value = null;
        }
        this.set('content', value);
    },

    validate: function() {
        var validation = this.get('validationFunction');
        if (!validation) {
            return true;
        }
        var args = [this.get('widget').val()];
        var validationArgs = this.get('validationArgs');
        if (validationArgs) {
            args.pushObjects((Em.typeOf(validationArgs) == 'array' ? validationArgs: [validationArgs]));
        }
        var errors = validation.apply(this, args);
        var valid = Em.empty(errors);
        Em.run.next(this,
        function() {
            if (valid == false) {
                this.set('validationMessage', errors[0].message);
            } else {
                this.set('validationMessage', null);
            }
        });
        return $.Deferred(function() {
            if (valid) {
                this.resolve(valid);
            } else {
                this.reject(valid);
            }
        }).promise();
    },

    focusOn: function() {
        this.get('widget').focus();
    }


});

App.TextAreaField = App.TextField.extend({

    classNames: ['text-area-field'],

    templateName: 'text-area-field',

    rows: 4,

    cols: 40,

    widget: function() {
        return this.$('textarea');
    }.property().volatile()

});


App.DateField = App.TextField.extend({

    templateName: 'date-field',

    readonly: true,

    size: 18,

    didInsertElement: function() {
        var self = this;
        var picker = this.$('input').datepicker({
            firstDay: 1,
            dateFormat: 'dd-mm-yy',
            // http://stackoverflow.com/a/10598178/1386535
            beforeShow: function(input, inst) {
                // Handle calendar position before showing it.
                // It's not supported by Datepicker itself (for now) so we need to use its internal variables.
                var calendar = inst.dpDiv;

                // Dirty hack, but we can't do anything without it (for now, in jQuery UI 1.8.20)
                setTimeout(function() {
                    calendar.position({
                        my: 'left top',
                        at: 'left bottom',
                        collision: 'none',
                        of: input
                    });
                },
                1);
            },

            onSelect: function() {
                self.set('content', $(this).datepicker('getDate').getTime());
            }
        });
        if (this.get('content')) {
            picker.datepicker('setDate', new Date(this.get('content')));
        }
    }

});