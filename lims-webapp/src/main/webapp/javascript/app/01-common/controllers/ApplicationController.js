App.ApplicationController = Em.Controller.extend({
    
    logoutUrlBinding: 'App.config.logoutUrl',
    
    logout: function() {
        var dialog = this._createLogoutConfirmationDialog();
        dialog.open();
    },
    
    _createLogoutConfirmationDialog: function() {
        var self = this;
        var buttons = {};
        buttons['Log out'] = function() {
            $(this).dialog('destroy');
            self._doLogout();
        };
        buttons['Cancel'] = function() {
            $(this).dialog('destroy');
        }
        return App.Dialog.create({
            selector: '.logout-confirmation.dialog',
            buttons: buttons
        });
    },
    
    _doLogout: function() {
        window.location.href = 'logout';
    }
    
});
