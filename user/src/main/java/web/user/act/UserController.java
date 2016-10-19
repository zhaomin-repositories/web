package web.user.act;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import web.framework.act.AbstractController;
import web.framework.sec.dvo.Role;
import web.framework.sec.dvo.User;
import web.framework.sec.dvo.UserRole;


@Controller
public class UserController extends AbstractController<Long, User> {
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "register";
	}
	
	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	public String doRegister(
		@RequestParam(value = "username", required = true) String username, 
		@RequestParam(value = "password", required = true) String password,
		Model model) {
		User user = new User();
		user.setId(username);
		user.setPassword(username);
		user.setPassword(password);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		
		Role role = this.getService().getMongoTemplate().findById("ROLE_USER", Role.class);
		if(role == null){
			role = new Role();
			role.setId("ROLE_USER");
			role.setName("ROLE_USER");
		}
		
		UserRole userRole = new UserRole();
		userRole.setId(user.getUsername());
		userRole.setUser(user);
		userRole.setRole(role);
				
		this.getService().getMongoTemplate().save(userRole);
		
		
		
		this.getService().getMongoTemplate().save(user);
		return "login";
	}
}