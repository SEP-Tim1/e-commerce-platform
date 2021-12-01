package sep.webshopback.util;

import org.springframework.security.core.context.SecurityContextHolder;
import sep.webshopback.model.User;

public class UserToken {

    public static User getUserFromToken(){
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticated;
    }

    public static long getUserIdFromToken(){
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticated.getId();
    }
}
