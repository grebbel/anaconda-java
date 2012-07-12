App.UserMenu = Em.View.extend({

    templateName: 'user-menu',

    classNames: ['user-menu'],

    displayName: null,

    email: null,
    
    isActive: function() {
        return this.$('.open-menu').hasClass('active');
    }.property().volatile(),

    didInsertElement: function() {
        var self = this;
        this.$('.open-menu').on('click',
        function(event) {
            if (self.get('isActive')) {
                self._hidePopupMenu();
            } else {
                self._showPopupMenu();
            }
            event.stopPropagation();
        });
        $('a').on('click', function() {
            self._hidePopupMenu();
        })
        $('body').on('keydown',
        function(event) {
            if (event.which == $.ui.keyCode.ESCAPE) {
                self._hidePopupMenu();
            }
        }).on('click', function() {
            self._hidePopupMenu();
        });
    },

    _hidePopupMenu: function() {
        this.$('.open-menu').removeClass('active');
        this.$('.popup-menu').hide()
    },

    _showPopupMenu: function() {
        this.$('.open-menu').addClass('active');
        this.$('.popup-menu').show().position({
            of: this.$(),
            my: 'right top',
            at: 'right bottom'
        });
    }

});
