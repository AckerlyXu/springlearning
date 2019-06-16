package springsecurity.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	@GetMapping(path = { "/", "/index" })
	public String index() {
		return "index";
	}

	@GetMapping(path = { "about" })
	@ResponseBody
	public String about() {
		return "about";
	}

	@GetMapping(path = { "/login" })
	public String login() {
		return "qqlogin";
	}

	// @PostMapping(path = { "/login" })
	// @ResponseBody
	public String login(String username, String password) {
		return username + password;
	}

	@GetMapping("/logout")
	public String logout() {
		return "logout";
	}

}
