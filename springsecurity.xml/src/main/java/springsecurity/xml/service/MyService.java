package springsecurity.xml.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class MyService {
	// @Secured(value = "ROLE_USER")
	public String getUserInfo() {
		return "userinfo";
	}

	@PreAuthorize("isAnonymous()")
	public String getCommonInfo() {
		return "commonInfo";
	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public String getAdminInfo() {
		return "AdminInfo";
	}
}
