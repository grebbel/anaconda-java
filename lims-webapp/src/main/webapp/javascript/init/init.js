App.reopen({
    ready: function() {
        var currentUser = App.User.getCurrent();
        currentUser.whenLoaded(function() {
            App.user = currentUser.get('content');
            App.initialize();            
        });
    }
});

$.ajaxSetup({
    contentType: 'application/json'/*,
    error: function(xhr) {
        alert('You are logged out. Please log in again.');
        if (xhr.status == 401) {
            window.location = 'login.jsp';
        }
        
    }*/
});
