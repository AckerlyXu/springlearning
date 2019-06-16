package springsecurity.start.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import springsecurity.start.model.MyAuthentication;

public class QQAuthenticationManager implements AuthenticationManager {
	private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

	public static final String clientId = "101386962";
	/**
	 * 获取 QQ 登录信息的 API 地址
	 */
	private final static String userInfoUri = "https://graph.qq.com/user/get_user_info";

	/**
	 * 获取 QQ 用户信息的地址拼接
	 */
	private final static String USER_INFO_API = "%s?access_token=%s&oauth_consumer_key=%s&openid=%s";

	static {
		AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		if (authentication.getName() != null && authentication.getCredentials() != null) {
			MyAuthentication user = null;
			try {
				user = getUserInfo(authentication.getName(), (String) authentication.getCredentials());
			} catch (Exception e) {
				// TODO: handle exception
			}
			return new UsernamePasswordAuthenticationToken(user, null, AUTHORITIES);
		}
		throw new BadCredentialsException("Bad Credentials");
	}

	private MyAuthentication getUserInfo(String name, String credentials) {
		// TODO Auto-generated method stub
		String url = String.format(USER_INFO_API, userInfoUri, name, clientId, credentials);
		Document document;
		try {
			document = Jsoup.connect(url).get();
		} catch (Exception e) {
			throw new BadCredentialsException("Bad Credentials!");
		}
		String resultText = document.text();
		JSONObject jsonObject = JSON.parseObject(resultText);
		MyAuthentication user = new MyAuthentication();
		user.setGender(jsonObject.getString("gender"));
		user.setProvince(jsonObject.getString("province"));
		user.setYear(jsonObject.getString("year"));
		user.setAvatar(jsonObject.getString("figureurl_qq_2"));
		return user;
	}

}
