package pl.achrzanowski.moneymanagementservletclient.usermanagement.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Component
public class RegistrationService {

    @Autowired
    private WebClient webClient;

    @Value("${registrationService.url}")
    private String registrationServiceUrl;

    @Value("${registrationServiceClientRegistrationId}")
    private String registrationServiceClientRegistrationId;

    public byte[] getCaptcha(){
        return webClient.get().uri(registrationServiceUrl + "/captcha")
                .attributes(clientRegistrationId(registrationServiceClientRegistrationId))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    public void requestRegistration(RegistrationDTO registrationDTO){
        webClient.post().uri(registrationServiceUrl + "/registration")
            .attributes(clientRegistrationId(registrationServiceClientRegistrationId))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(registrationDTO)
            .retrieve()
            .onStatus(HttpStatus.CONFLICT::equals, clientResponse -> Mono.error(new UsernameTakenException()))
            .onStatus(HttpStatus.FORBIDDEN::equals, clientResponse -> Mono.error(new InvalidVerificationCodeException()))
            .bodyToMono(byte[].class)
            .block();
    }

    public void validateVerificationCode(String verificationCode, String requestId){
        webClient.post().uri(registrationServiceUrl + "/verify")
            .attributes(clientRegistrationId(registrationServiceClientRegistrationId))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                    Map.of("verificationCode", verificationCode,
                            "requestId", requestId))
            .retrieve()
            .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new InvalidVerificationCodeException()))
            .toBodilessEntity()
            .block();
    }
}
