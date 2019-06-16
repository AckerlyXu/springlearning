package springsecurity.hibernate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import springsecurity.hibernate.service.MyUserDetailsService;

@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MyUserDetailsService MyUserDetailsService;

//	@Bean
//	public UserDetailsService userDetailsService() {
//
//	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { // 7

		auth
//	            .inMemoryAuthentication() //8
//	            .withUser("user").password("password").roles("USER"); // 9
				.userDetailsService(MyUserDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.authorizeRequests().antMatchers("/allow/**").permitAll()

				.anyRequest().authenticated().and().formLogin().and().httpBasic();
	}

}
