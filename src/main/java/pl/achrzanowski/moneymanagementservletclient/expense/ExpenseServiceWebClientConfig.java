package pl.achrzanowski.moneymanagementservletclient.expense;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ExpenseServiceWebClientConfig {

    @Value("${expenseService.url}")
    private String expenseServiceUrl;

    @Bean(name = "expenseServiceWebClient")
    public WebClient webClient(){
        return WebClient.builder().baseUrl(expenseServiceUrl).build();
    }

}
