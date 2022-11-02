package pl.achrzanowski.moneymanagementservletclient.registration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.Valid;

@Controller
@SessionScope
public class RegistrationController {

    @ModelAttribute
    public void addAttributes(Model model, RegistrationForm registrationForm){
        model.addAttribute("registrationForm", registrationForm);
    }

    @GetMapping("/registration")
    public String getRegistrationForm(){
        return "registration/registration-view.html";
    }

    @PostMapping("/registration")
    public String submit(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
                         BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "registration/registration-view.html";
         else
            return "redirect:/foobar";
    }

}
