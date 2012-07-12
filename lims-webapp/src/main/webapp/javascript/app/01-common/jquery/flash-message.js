$(function() {

	$('.flash-message .close').bind('click', function() {
		$(this).parent('.flash-message').remove();
		return false;
	});

});
