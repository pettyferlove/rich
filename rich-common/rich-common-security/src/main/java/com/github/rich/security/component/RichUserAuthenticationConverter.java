package com.github.rich.security.component;

import com.github.rich.security.service.RichUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Petty
 */
public class RichUserAuthenticationConverter implements UserAuthenticationConverter {

    private Collection<? extends GrantedAuthority> defaultAuthorities;

    /**
     * Extract information about the user to be used in an access token (i.e. for resource servers).
     *
     * @param userAuthentication an authentication representing a user
     * @return a map of key values representing the unique information about the user
     */
    @Override
    public Map<String, ?> convertUserAuthentication(Authentication userAuthentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(USERNAME, userAuthentication.getName());
        if (userAuthentication.getAuthorities() != null && !userAuthentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(userAuthentication.getAuthorities()));
        }
        return response;
    }

    /**
     * Inverse of {@link #convertUserAuthentication(Authentication)}. Extracts an Authentication from a map.
     *
     * @param map a map of user information
     * @return an Authentication representing the user or null if there is none
     */
    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Object principal = map.get(USERNAME);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return defaultAuthorities;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
