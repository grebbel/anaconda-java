App.CtTable = Em.View.extend({

    templateName: 'ct-table',

    analyses: [ ],

    saveChangesVisible: false,

    committing: false,
    
    didInsertElement: function() {
        this.modifiedDidChange();
        this.$('.actions input').button();
    },

    modifiedDidChange: function() {
        if (this.get('saveChangesVisible')) {
            this.$('.save-changes').show();
        } else {
            this.$('.save-changes').hide();
        }
    }.observes('saveChangesVisible'),

});