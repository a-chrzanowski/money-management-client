package pl.achrzanowski.moneymanagementservletclient.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.Valid;

@Controller
@SessionScope
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private RegistrationForm registrationForm;

    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("registrationForm", registrationForm);
    }

    @GetMapping("/registration")
    public String getRegistrationForm(){
        return "registration/registration-view.html";
    }

    @PostMapping("/registration")
    public String submit(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
                        BindingResult bindingResult){
        String viewName = "registration/registration-view.html";
        if(!bindingResult.hasErrors()){
            RegistrationDTO registrationDTO = new RegistrationDTO(registrationForm);
            registrationService.save(registrationDTO);
            viewName = "redirect:/";
        }
        return viewName;
    }

    @ExceptionHandler(UsernameTakenException.class)
    public String usernameTakenExceptionHandler(Model model){
        BindingResult bindingResult = new BeanPropertyBindingResult(registrationForm, "registrationForm");
        bindingResult.addError(new FieldError("registrationForm", "username", "This username is already taken"));

        model.addAttribute(BindingResult.class.getName() + ".registrationForm", bindingResult);
        return "registration/registration-view";
    }
}
