package web.framework.sec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import web.framework.sec.buz.SecurityService;
import web.framework.sec.dvo.Path;
import web.framework.sec.dvo.Role;
import web.framework.sec.dvo.RolePath;
import web.framework.sec.dvo.User;
import web.framework.sec.dvo.UserRole;

public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Resource(name = "securityService")
	private SecurityService securityService;

	private Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

	public Map<String, Collection<ConfigAttribute>> getResourceMap() {
		return resourceMap;
	}

	public void setResourceMap(Map<String, Collection<ConfigAttribute>> resourceMap) {
		this.resourceMap = resourceMap;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		List<RolePath> rolePaths = securityService.getRolePath();
		if (rolePaths == null) {
			rolePaths = new ArrayList<RolePath>();
		}
		if (rolePaths.isEmpty()) {
			Role role_admin = securityService.getRoleById("ROLE_ADMIN");
			if (role_admin == null) {
				role_admin = new Role();
				role_admin.setId("ROLE_ADMIN");
				role_admin.setName("ROLE_ADMIN");
				securityService.saveRole(role_admin);
			}
			Path path_admin = securityService.getPathById("PATH_ADMIN");
			if (path_admin == null) {
				path_admin = new Path();
				path_admin.setId("PATH_ADMIN");
				path_admin.setUri("/admin/**");
				path_admin.setName("PATH_ADMIN");
				securityService.savePath(path_admin);
			}
			RolePath rolePath_admin = new RolePath();
			rolePath_admin.setId("ROLE_PATH_ADMIN");
			rolePath_admin.setRole(role_admin);
			rolePath_admin.setPath(path_admin);
			securityService.saveRolePath(rolePath_admin);

			Role role_manager = securityService.getRoleById("ROLE_MANAGER");
			if (role_manager == null) {
				role_manager = new Role();
				role_manager.setId("ROLE_MANAGER");
				role_manager.setName("ROLE_MANAGER");
				securityService.saveRole(role_manager);
			}
			Path path_manager = securityService.getPathById("PATH_MANAGER");
			if (path_manager == null) {
				path_manager = new Path();
				path_manager.setId("PATH_MANAGER");
				path_manager.setUri("/manager/**");
				path_manager.setName("PATH_MANAGER");
				securityService.savePath(path_manager);
			}
			RolePath rolePath_manager = new RolePath();
			rolePath_manager.setId("ROLE_PATH_MANAGER");
			rolePath_manager.setRole(role_manager);
			rolePath_manager.setPath(path_manager);
			securityService.saveRolePath(rolePath_manager);

			Role role_user = securityService.getRoleById("ROLE_USER");
			if (role_user == null) {
				role_user = new Role();
				role_user.setId("ROLE_USER");
				role_user.setName("ROLE_USER");
				securityService.saveRole(role_user);
			}
			Path path_user = securityService.getPathById("PATH_USER");
			if (path_user == null) {
				path_user = new Path();
				path_user.setId("PATH_USER");
				path_user.setUri("/user/**");
				path_user.setName("PATH_USER");
				securityService.savePath(path_user);
			}
			RolePath rolePath_user = new RolePath();
			rolePath_user.setId("ROLE_PATH_USER");
			rolePath_user.setRole(role_user);
			rolePath_user.setPath(path_user);
			securityService.saveRolePath(rolePath_user);

			User user_admin = new User();
			user_admin.setId("admin");
			user_admin.setUsername("admin");
			user_admin.setPassword("123456");
			user_admin.setAccountNonExpired(true);
			user_admin.setAccountNonLocked(true);
			user_admin.setCredentialsNonExpired(true);
			user_admin.setEnabled(true);
			securityService.getMongoTemplate().save(user_admin);

			UserRole userRole_admin = new UserRole();
			userRole_admin.setId("USER_ROLE_ADMIN");
			userRole_admin.setRole(role_admin);
			userRole_admin.setUser(user_admin);
			securityService.saveUserRole(userRole_admin);
		}
		for (RolePath rolePath : rolePaths) {
			Path path = rolePath.getPath();
			Collection<ConfigAttribute> configAttributes = resourceMap.get(path.getUri());
			if (configAttributes == null) {
				configAttributes = new HashSet<ConfigAttribute>();
				resourceMap.put(path.getUri(), configAttributes);
			}
			configAttributes.add(new SecurityConfig(rolePath.getRole().getName()));
		}

		List<Role> roles = securityService.getRole();
		List<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
		for (Role role : roles) {
			configAttributes.add(new SecurityConfig(role.getName()));
		}
		return configAttributes;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		FilterInvocation filterInvocation = (FilterInvocation) object;
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String requestURL = ite.next();
			RequestMatcher requestMatcher = new AntPathRequestMatcher(requestURL);
			if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
				return resourceMap.get(requestURL);
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}