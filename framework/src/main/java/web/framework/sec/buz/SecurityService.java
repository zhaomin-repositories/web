package web.framework.sec.buz;

import java.util.List;

import web.framework.buz.Service;
import web.framework.sec.dvo.Menu;
import web.framework.sec.dvo.Path;
import web.framework.sec.dvo.Role;
import web.framework.sec.dvo.RoleMenu;
import web.framework.sec.dvo.RolePath;
import web.framework.sec.dvo.User;
import web.framework.sec.dvo.UserRole;

public interface SecurityService extends Service {
	
	public User getUserByKey(String key);
	
	public void savePath(Path path);
	
	public List<Path> getPath();
	
	public Path getPathById(String id);
	
	public List<Path> getPathByRole(Role role);
	
	public void saveMenu(Menu menu);
	
	public List<Menu> getMenu();
	
	public Menu getMenuById(String id);
	
	public List<Menu> getMenuByRole(Role role);
	
	public void saveRole(Role role);
	
	public List<Role> getRole();
	
	public Role getRoleById(String id);
	
	public List<Role> getRoleByUser(User user);
	
	public List<Role> getRoleByPath(Path path);
	
	public List<Role> getRoleByMenu(Menu menu);
	
	public void saveRolePath(RolePath rolePath);
	
	public List<RolePath> getRolePath();
	
	public void saveRoleMenu(RoleMenu roleMenu);
	
	public List<RoleMenu> getRoleMenu();
	
	public void saveUserRole(UserRole userRole);
	
}