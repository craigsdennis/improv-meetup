var App = {
	templates: {},
	state: {
		commentsAreSimulating: false,
	},
	init: function() {
		$('script[type="text/x-handlebars-template"]').each(function() {
			var $script = $(this);
			App.templates[$script.attr('id')] = Handlebars.compile($script.html());
		});
		this.bindEvents();
	},
	bindEvents: function() {
		this.bindCommentEvents();
		this.bindLearningEvents();
	},
	bindCommentEvents: function() {
		var app = this;
		$('#commentSimulation').on('click', function(evt) {
			evt.preventDefault();
			var $link = $(this);
			if (app.state.commentsAreSimulating) {
				app.state.commentsAreSimulating = false;
				$link.empty();
				$link.append($('<span />').addClass('glyphicon glyphicon-play'));
			} else {			
				app.state.commentsAreSimulating = true;
				app.simulateComments();
				$link.empty();
				$link.append($('<span />').addClass('glyphicon glyphicon-pause'));
			} 
		});
	},
	bindLearningEvents: function() {
		$('#learnIncrementer').click(function(evt) {
			evt.preventDefault();
			var currentCount = parseInt($('#learningCount').text(), 10);
			currentCount += 1;
			$('#learningCount').text(currentCount);
		});
		$('#brokenButton').click(function(evt) {
			console.warn('I warned you this button was broken.');
			alert(doucment.body.text);
		});
	},
	loadMoarComments: function() {
		var $comments = $('#comments');
		var data = $comments.data();
		var dfd = $.ajax('/api/comments', {
			data: data
		});
		dfd.then(function(data) {
			var newComments = _.map(data, function(comment) {
				return App.templates.commentRowTmpl(comment);
			});
			_.each(newComments, function(comment) {
				var $comment = $(comment);
				$comment.hide();
				$comments.prepend($comment);
				$comment.show('slow');
			});
		});
	},
	simulateComments: function() {
		var app = this;
		_.delay(function() {
			app.loadMoarComments();
			// Recurse randomly if we are supposed to.
			console.log('Current simulation state is', app.state.commentsAreSimulating);
			if (app.state.commentsAreSimulating) {				
				app.simulateComments();
			}
		}, _.random(1500, 4000));
	
	}
};


$(function() {
	App.init();
});
