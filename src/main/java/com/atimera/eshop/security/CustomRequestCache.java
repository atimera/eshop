package com.atimera.eshop.security;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomRequestCache extends HttpSessionRequestCache {

    // Permet de garder en mémoire l'url que l'utilisateur voulait accédé.
    // Une fois l'authentification terminée, on le redirige vers cet url
    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
        if (!SecurityUtils.isFrameworkInternalRequest(request)) {
            super.saveRequest(request, response);
        }
    }

}
