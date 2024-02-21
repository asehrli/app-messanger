package org.example.appsimplemassenger.util;

import lombok.experimental.UtilityClass;
import org.example.appsimplemassenger.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class PrincipalUtil {
    public User currentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
