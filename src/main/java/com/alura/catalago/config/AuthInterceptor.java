package com.alura.catalago.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // Rotas livres
        if (uri.equals("/") || uri.startsWith("/loja") || uri.equals("/usuario/login") || uri.equals("/usuario/autenticar") || uri.startsWith("/fotos/") || uri.startsWith("/css/") || uri.startsWith("/js/")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session != null && Boolean.TRUE.equals(session.getAttribute("usuario_logado"))) {
            return true;
        }

        response.sendRedirect("/usuario/login");
        return false;
    }
}
