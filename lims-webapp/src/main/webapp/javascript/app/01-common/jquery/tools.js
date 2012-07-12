$(function() {

	$('#tools-popup').wijpopup({
		autoHide: true,
		position: {
			of: '#tools',
			offset: '-6 8'
		},
		showEffect: 'fade',
		hideEffect: 'fade'
	});
	$('#tools').bind('click', function() {
		$('#tools-popup').wijpopup('show');
		return false;
	});
	/* Binds a handler for hiding the tools popup when pressing the ESC key. */
	$('body').bind('keyup', function(event) {
		if (event.which == 27) {
			$('#tools-popup').wijpopup('hide');
		}
	});
	
});