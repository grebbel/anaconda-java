App.RestApiCapable = Em.Mixin.create({
    
    // Note that contentType must be set to 'application/json'.
    // This is currently set as a default in init.js.
    baseUri: $('base').attr('href') + 'api/v1',

    _uri: function() {
        var uri = this.get('baseUri');
        for (var i = 0; i < arguments.length; i++) {
            var part = arguments[i];
            if (part.toString().indexOf('/') == -1) {
                uri += '/';
            }
            uri += part;
        }
        return uri;
    }

});

App.RestApi = Em.Object.extend(App.RestApiCapable, {

    /* Analysis operations */

    calculateCts: function(analyses, treshold) {
        var data = {
            asynchronous: false,
            analysisIds: analyses.mapProperty('id'),
            treshold: treshold
        };
        return $.post(this.get('baseUri') + '/calculate-cts', JSON.stringify(data));
    },

    updateAnalysisStatus: function(analyses) {
        return $.post(this.get('baseUri') + '/analyses/status', JSON.stringify({
            updates: analyses.map(function(a) {
                return a.getProperties('id', 'status', 'treshold');
            })
        }));
    },

    getAnalysesByRequest: function(requests, groups) {
        if (Em.typeOf(requests) != 'array') {
            requests = [requests];
        }
        var query = {
            requestIds: requests.mapProperty('id')
        };
        return $.get(this.get('baseUri') + '/analyses/query/by-request', query).success(function(data) {
            groups.set('content', data.map(function(group, index) {
                var group = App.Group.create({
                    data: group,
                    itemClass: App.Analysis,
                    itemProperty: 'analyses'
                });
                return group;
            }));
        });
    },

    /* Target operations */

    isTargetNameUnique: function(target) {
        var params = target.getProperties('id', 'name');
        return $.get(this._uri('/targets/validations/name-unique'), params);
    },

    /* Tag operations */

    isTagNameUnique: function(target) {
        var params = target.getProperties('id', 'name');
        return $.get(this._uri('/tags/validations/name-unique'), params);
    },

    getNonExistingTagNames: function(tagNames) {
        var params = {
            names: tagNames
        };
        return $.get(this._uri('/tags/validations/non-existing-names'), params);
    },

    /* SampleType operations */

    isSampleTypeNameUnique: function(sampleType) {
        var params = sampleType.getProperties('id', 'name');
        return $.get(this._uri('/sample-types/validations/name-unique'), params);
    },
    
    areSampleTypeCodesUnique: function(sampleType) {
        var params = sampleType.getProperties('id', 'codes');
        return $.get(this._uri('/sample-types/validations/codes-unique'), params);
    },

    /* SampleType operations */

    isTargetTypeNameUnique: function(targetType) {
        var params = targetType.getProperties('id', 'name');
        return $.get(this._uri('/target-types/validations/name-unique'), params);
    },

    areTargetTypeCodesUnique: function(targetType) {
        var params = targetType.getProperties('id', 'codes');
        return $.get(this._uri('/target-types/validations/codes-unique'), params);
    },
    
    /* User and profile operations */

    getCurrentUser: function(receiver) {
        return $.get(this._uri('/users/current')).success(function(data) {
            receiver.set('content', App.User.create(data));
        });
    },

});

App.api = App.RestApi.create();
