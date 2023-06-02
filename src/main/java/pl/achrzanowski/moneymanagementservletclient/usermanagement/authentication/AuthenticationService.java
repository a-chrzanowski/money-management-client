package pl.achrzanowski.moneymanagementservletclient.usermanagement.authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AuthenticationService {

    public void handleClientLogout(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session != null)
            session.invalidate();
        for(Cookie cookie : request.getCookies())
            cookie.setMaxAge(0);
        SecurityContextHolder.clearContext();
    }

    public void invalidateOAuth2AuthenticationToken(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        oAuth2AuthenticationToken.eraseCredentials();
        oAuth2AuthenticationToken.setAuthenticated(false);
    }

}
