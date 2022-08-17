package pl.achrzanowski.moneymanagementservletclient.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    @Qualifier("expenseServiceWebClient")
    private WebClient webClient;

    public List<CategoryDTO> getCategories(){
        return webClient.get().uri("/category")
                .retrieve()
                .bodyToFlux(CategoryDTO.class)
                .collectList()
                .onErrorReturn(new ArrayList<>())
                .block();
    }

}
