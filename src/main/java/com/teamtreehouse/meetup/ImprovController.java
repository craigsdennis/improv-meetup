package com.teamtreehouse.meetup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.teamtreehouse.meetup.integration.Google;
import com.teamtreehouse.meetup.integration.Twitter;


@Controller
public class ImprovController {

	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping("improv")
	public String improv(@RequestParam(value="name", required=true) String name, Model model) {
		Twitter twitter = new Twitter();
		Google google = new Google();
		model.addAttribute("name", name);
		model.addAttribute("googleResults", google.search(name));
		model.addAttribute("googleImages", google.search(name, true));
		model.addAttribute("tweets", twitter.search(name));
		return "improv";
	}
}