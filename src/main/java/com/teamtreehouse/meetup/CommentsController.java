package com.teamtreehouse.meetup;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.teamtreehouse.meetup.domain.Comment;

@Controller
@RequestMapping("/api/comments")
public class CommentsController {
    private Random mRandom = new Random();
    private String[][] mUsers = {
            { "craig@teamtreehouse.com", "Craig Dennis" },
            { "andrew@teamtreehouse.com", "Andrew Chalkers" },
            { "kenneth@teamtreehouse.com", "Kenneth Love" },
            { "dave@teamtreehouse.com", "Dave McFarland" }, 
            { "ben@teamtreehouse.com", "Ben Jakuben" },
            { "jason@teamtreehouse.com", "Jason Seifer" },
            { "amit@teamtreehouse.com", "Amit Bijlani" },
            { "guil@teamtreehouse.com", "Guil Hernandez" },
            { "hampton@teamtreehouse.com", "Hampton Paulk" },
            { "joy@teamtreehouse.com", "Joy Kesten" },
            { "nick@teamtreehouse.com", "Nick Petit" },
            { "pasan@teamtreehouse.com", "Pasan Premaratne"},
            { "zac@teamtreehouse.com", "Zac Gordon"},
    };
    private String[] mComments = {
            "I love {0}!  I seriously cannot get enough!",
            "{0}?  Of course I love it!",
            "I have to admit, I have seen better things than {0}.",
            "If I had my choice, I would never choose {0}.",
            "Sometimes {0} scares me.",
            "{0}?  Delight!",
            "I am not in touch with my feelings",
            "I am so tired of all this {0} hoopla",
            "What is {0}?",
            "Really? {0} ???",
    };

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Comment> getAllComments(
            @RequestParam(value = "regarding", required = true) String regarding,
            @RequestParam(value = "since", required = false) String since) {
        // Fake the since concept
        List<Comment> comments = new ArrayList<Comment>();
        int total = mRandom.nextInt(3) + 1;
        for (int i = 0; i < total; i++) {
            Comment com = new Comment();
            String[] fakeUser = mUsers[mRandom.nextInt(mUsers.length)];
            String fakePattern = mComments[mRandom.nextInt(mComments.length)];
            com.setPostedDate(new Date());
            com.setMessage(MessageFormat.format(fakePattern, regarding));
            com.setPosterEmail(fakeUser[0]);
            com.setPoster(fakeUser[1]);
            comments.add(com);
        }
        return comments;
    }
}
