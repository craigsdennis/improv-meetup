package com.teamtreehouse.meetup.integration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;

public class Google {
	private Customsearch mSearch;
	private Properties mProps;
	
	public Google() {
		mSearch = new Customsearch(new NetHttpTransport(), new JacksonFactory(), null);
		try {
			InputStream inputStream  = this.getClass().getClassLoader().getResourceAsStream("google.properties");
			mProps = new Properties();
			mProps.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Result> search(String query) {
		return search(query, false);
	}
	
	public List<Result> search(String query, boolean isImageSearch) {
		List<Result> results;
		try {
			Customsearch.Cse.List list = mSearch.cse().list(query);
			list.setKey(mProps.getProperty("apiKey"));
			list.setCx(mProps.getProperty("cxId"));
			if (isImageSearch) {
				list.setSearchType("image");
			}
			results = list.execute().getItems();
		} catch (IOException e) {
			e.printStackTrace();
			results = new ArrayList<Result>();
		}
		return results;
	}
}
