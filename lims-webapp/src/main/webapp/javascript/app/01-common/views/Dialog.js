App.Dialog = Em.Object.extend({

    selector: '.dialog',

    dialog: null,
    
    title: null,

    modal: true,

    resizable: false,

    buttons: {},

    open: function() {
        if (!this.get('dialog')) {
            this.set('dialog', this._createDialog());
        }
        this.get('dialog').dialog('open');
    },

    close: function() {
        this.get('dialog').dialog('close');
    },

    _createDialog: function() {
        var elem = $(this.get('selector'));
        Em.assert('Selection must consist of one element.', elem.length == 1);
        return elem.dialog({
            resizable: this.get('resizable'),
            title: this.get('title'),
            modal: this.get('modal'),
            buttons: this.get('buttons')
        });
    }

});
