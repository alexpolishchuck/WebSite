package com.groupProject.backend.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/**
 * \brief Клас для опрацювання токенів при автентифікації.
 */
public class TokenAuthentication extends AbstractAuthenticationToken {
    private final DecodedJWT jwt;
    private boolean invalidated;

    /**
     * Конструктор класу.
     *
     * @param jwt Токен.
     */
    public TokenAuthentication(DecodedJWT jwt) {
        super(readAuthorities(jwt));
        this.jwt = jwt;
    }

    private boolean hasExpired() {
        return jwt.getExpiresAt().before(new Date());
    }

    private static Collection<? extends GrantedAuthority> readAuthorities(DecodedJWT jwt) {
        Claim rolesClaim = jwt.getClaim("https://access_control/roles");
        if (rolesClaim.isNull()) {
            return Collections.emptyList();
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        String[] scopes = rolesClaim.asArray(String.class);
        for (String s : scopes) {
            SimpleGrantedAuthority a = new SimpleGrantedAuthority(s);
            if (!authorities.contains(a)) {
                authorities.add(a);
            }
        }

        return authorities;
    }

    /**
     * @return Повертає збережений дешифрований токен.
     */
    @Override
    public String getCredentials() {
        return jwt.getToken();
    }

    /**
     * @return Повертає збережений дешифрований токен.
     */
    @Override
    public Object getPrincipal() {
        return jwt.getSubject();
    }

    /**
     * Встановлення значення автентифікації користувача.
     *
     * @throws IllegalArgumentException У випадку якщо здійснюється спроба встановити значення "true".
     */
    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException("Create a new Authentication object to authenticate");
        }
        invalidated = true;
    }

    /**
     * Встановлення значення автентифікації користувача.
     *
     * @return Повертає "true" якщо токен дійсний та автентифікація не була
     * раніше встановлена на "false" використовуючи @ref setAuthenticated(boolean) "setAuthenticated"
     */
    @Override
    public boolean isAuthenticated() {
        return !invalidated && !hasExpired();
    }

    @Override
    public String getName() {
        return jwt.getClaim("email").toString();
    }

    /**
     * @return Повертає додаткову інформацію отриману з токен (ролі, права, email тощо)
     */
    public Map<String, Claim> getClaims() {
        return jwt.getClaims();
    }
}