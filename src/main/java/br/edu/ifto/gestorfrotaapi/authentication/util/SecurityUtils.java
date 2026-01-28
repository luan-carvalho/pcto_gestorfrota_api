package br.edu.ifto.gestorfrotaapi.authentication.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.edu.ifto.gestorfrotaapi.authentication.model.User;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Authentication authentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAuthenticated() {
        Authentication auth = authentication();
        return auth != null && auth.isAuthenticated();
    }

    public static User currentUser() {

        Authentication auth = authentication();

        if (auth == null || !(auth.getPrincipal() instanceof User user)) {

            throw new IllegalStateException("No authenticated user");

        }

        return user;

    }
}
