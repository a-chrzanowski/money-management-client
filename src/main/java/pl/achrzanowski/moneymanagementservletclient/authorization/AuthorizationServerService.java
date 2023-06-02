package pl.achrzanowski.moneymanagementservletclient.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthorizationServerService {

    @Autowired
    private WebClient webClient;

    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
    private String authServerURI;

    @Value("${spring.security.oauth2.client.registration.expense-service-authorization-code.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.expense-service-authorization-code.client-secret}")
    private String clientSecret;

    public void requestAccessTokenRevoke(OAuth2AccessToken oAuth2AccessToken){
        webClient.post()
                .uri(authServerURI + "/oauth2/revoke")
                .headers(httpHeaders -> httpHeaders.setBasicAuth(clientId, clientSecret))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("token", oAuth2AccessToken.getTokenValue())
                                            .with("token_type_hint", "access_token"))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void requestRefreshTokenRevoke(OAuth2RefreshToken oAuth2RefreshToken){
        webClient.post()
                .uri(authServerURI + "/oauth2/revoke")
                .headers(httpHeaders -> httpHeaders.setBasicAuth(clientId, clientSecret))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("token", oAuth2RefreshToken.getTokenValue())
                                            .with("token_type_hint", "refresh_token"))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
