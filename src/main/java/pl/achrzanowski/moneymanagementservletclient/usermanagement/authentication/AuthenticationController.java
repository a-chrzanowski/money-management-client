package pl.achrzanowski.moneymanagementservletclient.usermanagement.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.achrzanowski.moneymanagementservletclient.authorization.AuthorizationServerService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthenticationController {

    @Autowired
    private AuthorizationServerService authorizationServerService;

    @Autowired
    private AuthenticationService authenticationService;

    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
    private String authServerURI;

    @GetMapping("/login")
    public String login(){
        return "redirect:" + authServerURI + "/login";
    }

    @GetMapping("/logout")
    public String logout(
            @RegisteredOAuth2AuthorizedClient("expense-service-authorization-code") OAuth2AuthorizedClient authorizedClient,
            HttpServletRequest request,
            OAuth2AuthenticationToken oAuth2AuthenticationToken
    ){
        OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
        OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();
        authorizationServerService.requestAccessTokenRevoke(accessToken);
        if(refreshToken != null)
            authorizationServerService.requestRefreshTokenRevoke(refreshToken);

        authenticationService.invalidateOAuth2AuthenticationToken(oAuth2AuthenticationToken);
        authenticationService.handleClientLogout(request);
        return "redirect:" + authServerURI + "/logout";
    }

}
