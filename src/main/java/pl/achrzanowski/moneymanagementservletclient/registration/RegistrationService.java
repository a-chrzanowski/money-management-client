package pl.achrzanowski.moneymanagementservletclient.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Component
public class RegistrationService {

    @Autowired
    private WebClient webClient;

    @Value("${registrationService.url}")
    private String registrationServiceUrl;

    @Value("${registrationServiceClientRegistrationId}")
    private String registrationServiceClientRegistrationId;

    public void save(RegistrationDTO registrationDTO){
        webClient.post().uri(registrationServiceUrl + "/registration")
                .attributes(clientRegistrationId(registrationServiceClientRegistrationId))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registrationDTO)
                .retrieve()
                .onStatus(httpStatus -> httpStatus == HttpStatus.CONFLICT, clientResponse -> Mono.error(new UsernameTakenException()))
                .toBodilessEntity()
                .block();
    }

}
