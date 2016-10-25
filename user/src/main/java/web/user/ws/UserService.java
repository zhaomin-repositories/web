package web.user.ws;

import javax.jws.WebService;

@WebService
public interface UserService {
	public boolean authenticateUsernamePassword(String username, String password);
}