package web.framework.sec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import web.framework.sec.buz.SecurityService;
import web.framework.sec.dvo.Role;
import web.framework.sec.dvo.User;

public class CustomUserDetailService implements UserDetailsService {

	@Resource(name = "securityService")
	private SecurityService securityService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = securityService.getUserByKey(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<Role> roles = securityService.getRoleByUser(user);
		for (Role role : roles) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
			authorities.add(grantedAuthority);
		}
		return new CustomUserDetails(user.getUsername(), user.getPassword(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.isEnabled(), authorities);
	}

}