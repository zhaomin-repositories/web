package web.framework.sec.buz.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import web.framework.buz.impl.ServiceImpl;
import web.framework.sec.buz.SecurityService;
import web.framework.sec.dvo.Menu;
import web.framework.sec.dvo.Path;
import web.framework.sec.dvo.Role;
import web.framework.sec.dvo.RoleMenu;
import web.framework.sec.dvo.RolePath;
import web.framework.sec.dvo.User;
import web.framework.sec.dvo.UserRole;

@Service("securityService")
public class SecurityMongoServiceImpl extends ServiceImpl implements SecurityService {

	@Override
	public User getUserByKey(String uniqueid) {
		User user = null;
		try{
			Criteria criatira = new Criteria();
			criatira.orOperator(Criteria.where("_id").is(uniqueid), Criteria.where("username").is(uniqueid), Criteria.where("phone").is(uniqueid),Criteria.where("email").is(uniqueid));
			user = this.getMongoTemplate().findOne(new Query(criatira), User.class);
		} catch (Exception e) {
	        e.printStackTrace();
	    }
		return user;
	}

	@Override
	public List<Role> getRoleByUser(User user) {
		List<Role> roles = new ArrayList<Role>();
		try{
			Criteria criatira = new Criteria();
			criatira.orOperator(Criteria.where("user.$id").is(user.getId()));
			Query query = new Query();
			query.fields().include("role");
			List<UserRole> userRoles = this.getMongoTemplate().find(new Query(criatira), UserRole.class);
			if (userRoles != null && !userRoles.isEmpty()) {
				for(UserRole userRole : userRoles){
					roles.add(userRole.getRole());
				}
			}
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		return roles;
	}

	@Override
	public List<Path> getPathByRole(Role role) {
		List<Path> paths = new ArrayList<Path>();
		try{
			Criteria criatira = new Criteria();
			criatira.orOperator(Criteria.where("role.$id").is(role.getId()));
			Query query = new Query();
			query.fields().include("path");
			List<RolePath> rolePaths = this.getMongoTemplate().find(new Query(criatira), RolePath.class);
			if (rolePaths != null && !rolePaths.isEmpty()) {
				for(RolePath rolePath : rolePaths){
					paths.add(rolePath.getPath());
				}
			}
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		return paths;
	}

	@Override
	public List<Menu> getMenuByRole(Role role) {
		return null;
	}

	@Override
	public List<Role> getRole() {
		return this.getMongoTemplate().findAll(Role.class);
	}

	@Override
	public List<Role> getRoleByPath(Path path) {
		List<Role> roles = new ArrayList<Role>();
		try{
			Criteria criatira = new Criteria();
			criatira.orOperator(Criteria.where("path.$id").is(path.getId()));
			Query query = new Query();
			query.fields().include("role");
			List<RolePath> rolePaths = this.getMongoTemplate().find(new Query(criatira), RolePath.class);
			if (rolePaths != null && !rolePaths.isEmpty()) {
				for(RolePath rolePath : rolePaths){
					roles.add(rolePath.getRole());
				}
			}
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		return roles;
	}

	@Override
	public List<Role> getRoleByMenu(Menu menu) {
		return null;
	}

	@Override
	public void saveRole(Role role) {
		this.getMongoTemplate().save(role);
	}

	@Override
	public List<Path> getPath() {
		return this.getMongoTemplate().findAll(Path.class);
	}

	@Override
	public List<Menu> getMenu() {
		return this.getMongoTemplate().findAll(Menu.class);
	}

	@Override
	public void savePath(Path path) {
		this.getMongoTemplate().save(path);
		
	}

	@Override
	public void saveMenu(Menu menu) {
		this.getMongoTemplate().save(menu);
	}

	@Override
	public List<RolePath> getRolePath() {
		return this.getMongoTemplate().findAll(RolePath.class);
	}

	@Override
	public List<RoleMenu> getRoleMenu() {
		return this.getMongoTemplate().findAll(RoleMenu.class);
	}

	@Override
	public void saveRolePath(RolePath rolePath) {
		this.getMongoTemplate().save(rolePath);
	}

	@Override
	public void saveRoleMenu(RoleMenu roleMenu) {
		this.getMongoTemplate().save(roleMenu);
	}

	@Override
	public Path getPathById(String id) {
		return this.getMongoTemplate().findById(id, Path.class);
	}

	@Override
	public Menu getMenuById(String id) {
		return this.getMongoTemplate().findById(id, Menu.class);
	}

	@Override
	public Role getRoleById(String id) {
		return this.getMongoTemplate().findById(id, Role.class);
	}

	@Override
	public void saveUserRole(UserRole userRole) {
		this.getMongoTemplate().save(userRole);
	}

}