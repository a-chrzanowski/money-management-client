package pl.achrzanowski.moneymanagementservletclient.usermanagement.landing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LandingController {

    @GetMapping("/")
    public String getLanding(Principal principal){
        if(principal == null)
            return "landing/landing-view.html";
        else
            return "redirect:/expense/all";
    }
    
}
