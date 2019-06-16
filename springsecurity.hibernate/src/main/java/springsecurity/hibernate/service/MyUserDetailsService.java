package springsecurity.hibernate.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import springsecurity.hibernate.dao.UserDao;
import springsecurity.hibernate.model.MyUser;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserDao dao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		MyUser user = dao.findUserByUsername(username);
		if (user != null) {
			return new UserDetails() {

				@Override
				public boolean isEnabled() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean isCredentialsNonExpired() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean isAccountNonLocked() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean isAccountNonExpired() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public String getUsername() {
					// TODO Auto-generated method stub
					return user.getUsername();
				}

				@Override
				public String getPassword() {
					// TODO Auto-generated method stub
					// 关于为什么要加bycrypt
					// https://blog.csdn.net/canon_in_d_major/article/details/79675033
					return "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());
				}

				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}
		throw new UsernameNotFoundException("用户没有找到");
	}

}
