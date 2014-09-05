package com.teamtreehouse.meetup.integration;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Twitter {
	twitter4j.Twitter mTwitter;
	
	public Twitter() {
		mTwitter = TwitterFactory.getSingleton();
	}
	
	public List<Status> search(String query) {
		List<Status> tweets;
		try {
			Query q = new Query(query);
			QueryResult result = mTwitter.search(q);
			tweets = result.getTweets();	
		} catch(TwitterException e) {
			e.printStackTrace();
			tweets = new ArrayList<Status>();
		}
		return tweets;	
	}
}
