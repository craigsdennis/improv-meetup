$(function() { 
	var commentTemplate = _.template(
		'<li class="list-group-item"><div class="row">' +
		'<div class="col-md-2"><img src="<%= posterAvatar %>" class="img-circle" /></div>' +
		'<div><blockquote><p><%= message %></p><footer><%= poster %></footer></blockquote></div>' + 
		'</div></li>');
	var loadMoarComments = function() {
		var $comments = $('#comments');
		var data = $comments.data();
		var dfd = $.ajax('/api/comments', {
			data: data
		});
		dfd.then(function(data) {
			var newComments = _.map(data, function(comment) {
				return commentTemplate(comment);
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
			$link.text('Start comment simulation');
		} else {			
			areCommentsSimulating = true;
			simulateComments();
			$link.text('Simulating...click to stop');
		}
		
	});
});