package br.com.sqs.bridge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage() {
	return "homePage";
    }

    @GetMapping("/leaders")
    public String showLeaders() {
	return "leaders";
    }

    @GetMapping("/systems")
    public String showSystems() {
	return "systems";
    }
}
