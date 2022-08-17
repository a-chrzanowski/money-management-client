package pl.achrzanowski.moneymanagementservletclient.importance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImportanceService {

    @Autowired
    @Qualifier("expenseServiceWebClient")
    private WebClient webClient;

    public List<ImportanceDTO> getImportanceList(){
        return webClient.get().uri("/importance")
                .retrieve()
                .bodyToFlux(ImportanceDTO.class)
                .collectList()
                .onErrorReturn(new ArrayList<>())
                .block();
    }

}
