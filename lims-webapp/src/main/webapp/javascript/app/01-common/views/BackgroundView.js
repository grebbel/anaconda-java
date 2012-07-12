App.BackgroundView = Em.View.extend({
    
    classNames: ['background-view'],
    
    didInsertElement: function() {
        var content = $('<div class="content"></div>');
        content.append(this.$('*'));
        this.$().append(content);        
        var background = $('<div class="background"></div>')
        this.$().append(background);
        background.css('width', this.$().width()).css('height', this.$().height());
    }
})