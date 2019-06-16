package springframework.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import springframework.test.sessionandrequest.SimpleUserService;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:requestAndSession.xml")
@WebAppConfiguration
public class RequestAndSession {

	@Autowired
	private SimpleUserService simpleUserService;
	@Autowired
	MockHttpServletRequest request;
	@Autowired
	MockHttpSession session;

	@Test
	public void test() {

		request.setParameter("username", "ackerly");
		request.setParameter("password", "123");
		session.setAttribute("product", "chicken");
		assertTrue(simpleUserService.isLogin());
		assertTrue(simpleUserService.getProduct().equals("chicken"));
	}

}
