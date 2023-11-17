package com.groupProject.backend.controller;

import com.auth0.AuthenticationController;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;
import com.groupProject.backend.model.DTO.UserDTO;
import com.groupProject.backend.security.Auth0Management;
import com.groupProject.backend.security.AuthService;
import com.groupProject.backend.security.TokenAuthentication;
import com.groupProject.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * \brief Клас-контролер для авторизації та реєстрації Користувача.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
    private final AuthenticationController authenticationController;
    private final UserService userService;
    private final Auth0Management auth0Management;

    @Value("${auth0.audience}")
    private String audience;
    @Value(value = "${auth0.domain}")
    private String domain;
    @Value(value = "${auth0.clientId}")
    private String clientId;
    @Value(value = "${auth0.clientSecret}")
    private String clientSecret;

    /**
     * Конструктор контролера.
     *
     * @param authService     Сервіс для автентифікації Користувача.
     * @param userService     Сервіс для взаємодії з базою даних Користувачів.
     * @param auth0Management
     */
    @Autowired
    public LoginController(AuthService authService, UserService userService, Auth0Management auth0Management) {
        authenticationController = authService.createAuthenticationController();
        this.userService = userService;
        this.auth0Management = auth0Management;
    }

    /**
     * Авторизація Користувача. Переадресовує на сторінку для авторизації Auth0.
     *
     * @param request HTTP запит.
     * @param response HTTP відповідь.
     */
    @GetMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String redirectUri = createRedirectUri(request);

        String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
                .withParameter("scope", "openid profile email")
                .build();

        response.sendRedirect(authorizeUrl);
    }

    /**
     * Реєстрація Користувача. Переадресовує на сторінку для реєстрації Auth0.
     *
     * @param request HTTP запит.
     * @param response HTTP відповідь.
     */
    @GetMapping("/register")
    public void GetHome(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String redirectUri = createRedirectUri(request);

        String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri)
                .withParameter("scope", "openid profile email")
                .withParameter("screen_hint", "signup")
                .build();

        response.sendRedirect(authorizeUrl);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void test()
    {

    }
    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public void test1()
    {

    }
    /**
     * Користувач переадресовується на цей URL у випадку успішної авторизації.
     *
     * @param request HTTP запит.
     * @param response HTTP відповідь.
     */
    @GetMapping("/login_callback")
    public void authenticationCallback(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        try {
            // Parse the request
            Tokens tokens = authenticationController.handle(request, response);
            TokenAuthentication tokenAuth = new TokenAuthentication(JWT.decode(tokens.getIdToken()));

            if(tokenAuth.getAuthorities().size() == 0)
            {
                response.sendRedirect("/logout");
                return;
            }

            String auth0Id = tokenAuth.getPrincipal().toString();
            if(userService.getUserEnityByAuth0Id(auth0Id).isPresent())
            {
                SecurityContextHolder.getContext().setAuthentication(tokenAuth);
                response.sendRedirect("/");
                return;
            }

            GrantedAuthority[] authorities = new GrantedAuthority[0];
            authorities = tokenAuth.getAuthorities().toArray(authorities);

            UserDTO newUser = new UserDTO();
            newUser.setAuth0Id(auth0Id);
            newUser.setLogin(tokenAuth.getName());
            newUser.setRole(authorities[0].toString());

            Optional<UserDTO> res = userService.registerUser(newUser);

            if(!res.isPresent())
            {
                auth0Management.deleteUser(auth0Id);
                logger.error("Failed request to register User");
                response.sendRedirect("/");
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(tokenAuth);
            response.sendRedirect("/");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/login");
        }
    }

    private String createRedirectUri(HttpServletRequest request){
        String redirectUri = request.getScheme() + "://" + request.getServerName();
        if ((request.getScheme().equals("http") && request.getServerPort() != 80)
                || (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            redirectUri += ":" + request.getServerPort();
        }
        redirectUri += "/login_callback";

        return redirectUri;
    }
}
