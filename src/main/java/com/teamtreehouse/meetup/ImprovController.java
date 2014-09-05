package com.teamtreehouse.meetup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ImprovController {
	
	@RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

	@RequestMapping("improv")
	public String improv(@RequestParam(value="name", required=true) String name, Model model) {
		model.addAttribute("name", name);
		return "improv";
	}
}