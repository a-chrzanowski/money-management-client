package pl.achrzanowski.moneymanagementservletclient.usermanagement.landing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@Controller
public class LandingController {

    @Autowired
    private WebClient webClient;

    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
    private String authServerURI;


    @GetMapping("/")
    public String getLanding(Principal principal, Model model){
        model.addAttribute("authServerURI", authServerURI + "/login");
        if(principal == null)
            return "landing/landing-view.html";
        else
            return "redirect:/expense/all";
    }
    
}
