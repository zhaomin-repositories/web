package web.user.ws.impl;

import web.user.ws.UserService;

import javax.jws.WebService;

@WebService(endpointInterface = "web.user.ws.UserService")
public class UserServiceImpl implements UserService {
    @Override
    public boolean authenticateUsernamePassword(String username, String password) {
        return false;
    }
}