App.TaskController = Em.Controller.extend(App.RestApiCapable, {

    content: null,

    init: function() {
        this.set('content', Em.Object.create({
            unassigned: Em.ArrayProxy.create()
        }));
    },

    update: function() {
        var self = this;
        return $.get(this._uri('/tasks/unassigned')).done(function(data) {
            self.setPath('content.unassigned.content', data.map(function(item) {
                item.templateName = 'task.' + item.description;
                return Em.Object.create(item);
            }));
        });
    },


});