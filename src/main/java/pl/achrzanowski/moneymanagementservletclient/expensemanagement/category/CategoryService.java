package pl.achrzanowski.moneymanagementservletclient.expensemanagement.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Service
public class CategoryService {

    @Autowired
    private WebClient webClient;

    @Value("${expenseService.url}")
    private String expenseServiceUrl;

    @Value("${expenseServiceClientRegistrationId}")
    private String expenseServiceClientRegistrationId;

    public List<CategoryDTO> getCategories(){
        return webClient.get().uri(expenseServiceUrl + "/category")
                .attributes(clientRegistrationId(expenseServiceClientRegistrationId))
                .retrieve()
                .bodyToFlux(CategoryDTO.class)
                .collectList()
                .onErrorReturn(new ArrayList<>())
                .block();
    }

}
