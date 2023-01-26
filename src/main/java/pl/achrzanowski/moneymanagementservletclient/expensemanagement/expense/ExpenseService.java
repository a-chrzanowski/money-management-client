package pl.achrzanowski.moneymanagementservletclient.expensemanagement.expense;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Service
public class ExpenseService {

    @Autowired
    private WebClient webClient;

    @Value("${expenseService.url}")
    private String expenseServiceUrl;

    @Value("${expenseServiceClientRegistrationId}")
    private String expenseServiceClientRegistrationId;

    public List<Expense> getExpenses(){
        return webClient.get().uri(expenseServiceUrl + "/expense")
                .attributes(clientRegistrationId(expenseServiceClientRegistrationId))
                .retrieve()
                .bodyToFlux(Expense.class)
                .collectList()
                .onErrorReturn(new ArrayList<>())
                .block();
    }

    public Expense getExpense(String id){
        return webClient.get().uri(expenseServiceUrl + "/expense/" + id)
                .attributes(clientRegistrationId(expenseServiceClientRegistrationId))
                .retrieve()
                .bodyToMono(Expense.class)
                .onErrorReturn(new Expense())
                .block();
    }

    public Expense save(Expense expense){
        return webClient.post().uri(expenseServiceUrl + "/expense")
                .attributes(clientRegistrationId(expenseServiceClientRegistrationId))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(expense)
                .retrieve()
                .bodyToMono(Expense.class)
                .block();
    }

    public void delete(String id){
        webClient.delete().uri(expenseServiceUrl + "/expense/" + id)
                .attributes(clientRegistrationId(expenseServiceClientRegistrationId))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
