package springsecurity.hibernate.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	@GetMapping("index")
	@ResponseBody
	public String index() {
		return "index";
	}

//加了这个注解，即使configure里配置了permitAll，也会生效
	// 要让这个注解生效需要在java配置类上加上@EnableGlobalMethodSecurity(securedEnabled = true)
	@Secured("ROLE_TELLER")
	@GetMapping("/allow/user")
	@ResponseBody
	public String allow() {
		return "allows";
	}
}
