package com.teamtreehouse.meetup;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ImprovController {
	
	@RequestMapping
	public String index(Model model) {
		return "index";
	}
}