$(function() { 
	var commentRowTemplate = Handlebars.compile($('#commentRowTmpl').html());
	var loadMoarComments = function() {
		var $comments = $('#comments');
		var data = $comments.data();
		var dfd = $.ajax('/api/comments', {
			data: data
		});
		dfd.then(function(data) {
			var newComments = _.map(data, function(comment) {
				return commentRowTemplate(comment);
			});
			_.each(newComments, function(comment) {
				var $comment = $(comment);
				$comment.hide();
				$comments.prepend($comment);
				$comment.show('slow');
			});
		});
	}
	var areCommentsSimulating = false;
	var simulateComments = function() {
		_.delay(function() {
			loadMoarComments();
			// Recurse randomly
			if (areCommentsSimulating) {				
				simulateComments();
			}
		}, _.random(1500, 4000));
	
	}
	$('#commentSimulation').on('click', function() {
		$link = $(this); 
		if (areCommentsSimulating) {
			areCommentsSimulating = false;
			$link.empty();
			$link.append($('<span />').addClass('glyphicon glyphicon-play'));
		} else {			
			areCommentsSimulating = true;
			simulateComments();
			$link.empty();
			$link.append($('<span />').addClass('glyphicon glyphicon-pause'));
		} 
	});
});