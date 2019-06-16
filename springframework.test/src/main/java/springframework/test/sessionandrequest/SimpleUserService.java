package springframework.test.sessionandrequest;

public class SimpleUserService {
	private LoginAction loginAction;
	private UserPreference userPreference;

	public LoginAction getLoginAction() {
		return loginAction;
	}

	public void setLoginAction(LoginAction loginAction) {
		this.loginAction = loginAction;
	}

	public boolean isLogin() {
		return this.loginAction.getPassword().equals("123") && this.loginAction.getUsername().equals("ackerly");
	}

	public UserPreference getUserPreference() {
		return userPreference;
	}

	public void setUserPreference(UserPreference userPreference) {
		this.userPreference = userPreference;
	}

	public String getProduct() {
		return userPreference.getProduct();
	}
}
