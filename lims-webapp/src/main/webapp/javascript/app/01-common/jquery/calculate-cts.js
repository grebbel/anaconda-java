$(function() {

	$('form#calculate-cts').bind('submit', function() {
		$(this).ajaxSubmit({
			dataType: 'json',
			success: function(data) {
			}
		});
		return false;
	})
});