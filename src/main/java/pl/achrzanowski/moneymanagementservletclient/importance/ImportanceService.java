package pl.achrzanowski.moneymanagementservletclient.importance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Service
public class ImportanceService {

    @Autowired
    @Qualifier("expenseServiceWebClient")
    private WebClient webClient;

    @Value("${expenseServiceClientRegistrationId}")
    private String expenseServiceClientRegistrationId;

    public List<ImportanceDTO> getImportanceList(){
        return webClient.get().uri("/importance")
                .attributes(clientRegistrationId(expenseServiceClientRegistrationId))
                .retrieve()
                .bodyToFlux(ImportanceDTO.class)
                .collectList()
                .onErrorReturn(new ArrayList<>())
                .block();
    }


}
