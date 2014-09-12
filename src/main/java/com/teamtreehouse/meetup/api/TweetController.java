package com.teamtreehouse.meetup.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import twitter4j.Status;

import com.teamtreehouse.meetup.integration.Twitter;

@Controller
@RequestMapping("/api/tweets/search")
public class TweetController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Status> search(@RequestParam(value = "q", required = true) String q) {
        Twitter t = new Twitter();
        return t.search(q);
    }
            
    
}
