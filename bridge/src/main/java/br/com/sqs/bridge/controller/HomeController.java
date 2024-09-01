package br.com.sqs.bridge.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${application.version}")
    private String version;

    @GetMapping("/")
    public String showHomePage(Model model) {
	model.addAttribute("version", version);
	return "homePage";
    }

}
