var App = {
	templates: {},
	state: {
		commentsAreSimulating: false,
		tweetRefreshRate: 10000,
	},
	init: function() {
		$('script[type="text/x-handlebars-template"]').each(function() {
			var $script = $(this);
			App.templates[$script.attr('id')] = Handlebars.compile($script.html());
		});
		this.bindEvents();
		this.refreshTweets();
	},
	bindEvents: function() {
		this.bindCommentEvents();
		this.bindLearningEvents();
	},
	bindCommentEvents: function() {
		var app = this;
		$('#comment-simulation').on('click', function(evt) {
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
		$('#learn-incrementer').click(function(evt) {
			evt.preventDefault();
			var currentCount = parseInt($('#learning-count').text(), 10);
			currentCount += 1;
			$('#learning-count').text(currentCount);
		});
		$('#broken-button').click(function(evt) {
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
		dfd.then(function(results) {
			var newComments = _.map(results, function(comment) {
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
	},
	loadNewTweets: function() {
		var $tweets = $('#tweets');
		var data = $tweets.data();
		var dfd = $.ajax('/api/tweets/search', {
			data: data
		});
		dfd.then(function(results) {
			// For some reason, Twitter is returning the sinceId
			if (results.length && data.sinceId) {
				results = _.reject(results, function(result) {
					return result.id === data.sinceId;
				});				
			}
			if (results.length) {
				// Store this for refresh
				$tweets.data('sinceId', results[0].id);
			} else {
				console.log('No new tweets');
				return;
			}
			var newTweets = _.map(results, function(result) {
				return App.templates.tweetRowTmpl(result);
			});
			_.each(newTweets, function(tweet) {
				var $tweet = $(tweet);
				$tweet.hide();
				$tweets.prepend($tweet);
				$tweet.show('slow');
				$tweet.find('button.close').click(function(evt) {
					evt.preventDefault();
					$(this).parents('li.list-group-item').remove();
				});
				$tweet.on('mouseover', function(evt) {
					$tweet.find('.hide').removeClass('hide');
				});
				$tweet.on('mouseout', function(evt) {
					$tweet.find('div:not(.row):first, footer, button.close').addClass('hide');
				});
				// Only recent ones
				$tweets.find('li').slice(10).remove();
			});
		});
	},
	refreshTweets: function() {
		var app = this;
		this.loadNewTweets();
		_.delay(function() {
			app.refreshTweets();
		}, this.state.tweetRefreshRate);
	}
};


$(function() {
	App.init();
});
