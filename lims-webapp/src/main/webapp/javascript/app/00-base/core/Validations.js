App.Validations = Em.Namespace.create();

App.Validations.notEmpty = function(value) {
    if (Em.empty(value)) {
        return [{
            message: 'Field cannot be empty.'
        }];
    } else {
        return null;
    }
};

App.Validations.match = function(value, regexp) {
    if (regexp === undefined) {
        return;
    }
    regexp = Em.typeOf(regexp) == 'string' ? new RegExp(regexp) : regexp;
    if (!value.match(regexp)) {
        return [{
            message: 'Invalid value.'
        }];
    } else {
        return null;
    }
};


App.Validations.deferred = function(entity, validation, validationContext, failureMessage) {
    validationContext = validationContext || App.api;
    var deferred = $.Deferred();
    validation.call(validationContext, entity).done(function(valid) {
        if (valid) {
            deferred.resolve(valid);
        } else {
            deferred.reject(failureMessage);
        }
    }).fail(function() {
        deferred.reject('Could not validate.');
    });
    return deferred.promise();
}

App.Validations.isNameUnique = function(entity, validation, validationContext) {
    return App.Validations.deferred(entity, validation, validationContext,
    'Name "' + entity.get('name') + '" is already in use.');
};

App.Validations.areCodesUnique = function(entity, validation, validationContext) {
    return App.Validations.deferred(entity, validation, validationContext,
    'One of the codes is already in use.');
};

App.Validatable = Em.Mixin.create({

    validateOnBlur: false,

    validation: null,

    validationArgs: [],

    validationMessage: null,

    validationFunction: function() {
        var validation = this.get('validation');
        if (Em.typeOf(validation) == 'string') {
            validation = App.Validations[validation];
        }
        Em.assert('Validate should refer to a function.', !validation || Em.typeOf(validation) == 'function');
        return validation;
    }.property('validation'),

    focusOn: function() {},

    validate: Em.required(Function)

});