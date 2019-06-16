package springsecurity.start.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import springsecurity.start.service.QQAuthenticationFilter;
import springsecurity.start.service.QQAuthenticationManager;

@EnableWebSecurity
@ComponentScan(basePackages = { "springsecurity.start.service" })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailService;
//	@Bean
//	public UserDetailsService userDetailsService() {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(
//				User.withDefaultPasswordEncoder().username("ackerly").password("123").roles("ADMIN").build());
//		return manager;
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(http);

//		http
//				// 这一行相当与这段<intercept-url pattern="/**" access="authenticated"/>xml
//				.authorizeRequests().anyRequest().authenticated().and()
//				// 设置login的地址,并将login设置为允许所有用户访问
//				.formLogin().loginPage("/login").permitAll();

		// 匹配的策略是先匹配到的为准
//		http.authorizeRequests().antMatchers("/images/**", "/css/**", "/js/**")
//
//				.permitAll().antMatchers("/admin/**").hasRole("ADMIN").antMatchers("/db/**")
//				.access("hasRole('ADMIN') and hasRole('DBA')").anyRequest().authenticated().and().formLogin().and()
//				.httpBasic().and().csrf();

		// 默认继承了WebSecurityConfigurerAdapter是开启logout的,路径是/logout，但是如果开启了csrf防护，那么需要post请求并且携带csrf
		// token

		http.authorizeRequests().antMatchers("/user/**", "/news/**").authenticated().anyRequest().permitAll().and()
				.formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/user/info").and().logout().and().csrf()
				.disable();
		http.addFilterAt(qqAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	private QQAuthenticationFilter qqAuthenticationFilter() {
		QQAuthenticationFilter filter = new QQAuthenticationFilter("/login/qq");
		filter.setAuthenticationManager(new QQAuthenticationManager());
		return filter;
	}

	// 注册自定义的UserDetailService
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		// super.configure(auth);
		auth.userDetailsService(userDetailService);
	}

}
