package springsecurity.xml.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import springsecurity.xml.service.MyService;

@Controller
public class HomeController {
	@Autowired(required = false)
	private MyService myService;

	@GetMapping("/index")
	@ResponseBody
	public String index() {
		myService.getUserInfo();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return encoder.encode("123");

	}

	@GetMapping("/user/info")
	@ResponseBody
	public String user() {
		myService.getAdminInfo();
		return "userinfo";
	}

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping("/user/failure")
	@ResponseBody
	public String authenticationFailure() {
		myService.getUserInfo();
		return "/user/failure";
	}
}
