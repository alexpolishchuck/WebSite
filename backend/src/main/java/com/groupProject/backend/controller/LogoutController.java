package com.groupProject.backend.controller;

import com.groupProject.backend.security.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * \brief Клас-контролер для розлогування Користувача.
 */
@Controller
public class LogoutController extends SecurityContextLogoutHandler {
    private final SecurityConfig securityConfig;

    /**
     * Конструктор контролера.
     *
     * @param securityConfig Клас який містить дані AUTH0.
     */
    public LogoutController(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    /**
     * Метод для розлогування клієнта.
     *
     * @param httpServletRequest HTTP запит.
     * @param httpServletResponse HTTP відповідь.
     * @param authentication Дані про автентифікацію Користувача, збережені в системі.
     */
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       Authentication authentication)
    {
        super.logout(httpServletRequest, httpServletResponse, authentication);

        String returnToUri =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toString()
                + "/login";

        String logoutUrl = UriComponentsBuilder
                .fromHttpUrl(securityConfig.getIssuer()
                        + "v2/logout?client_id={clientId}&returnTo={returnTo}")
                .encode()
                .buildAndExpand(securityConfig.getClientId(), returnToUri)
                .toUriString();

        try {
            httpServletResponse.sendRedirect(logoutUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
