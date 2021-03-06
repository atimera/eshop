package com.atimera.eshop.security;

import com.vaadin.flow.server.HandlerHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public class SecurityUtils {

    private SecurityUtils() {
        // Util methods only
    }

    // Identifie si la requête http est interne à Vaadin ou pas
    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(HandlerHelper.RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    // Identifie si l'utilisateur est connécté ou pas
    static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }

}
