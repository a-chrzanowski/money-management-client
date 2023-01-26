package pl.achrzanowski.moneymanagementservletclient.usermanagement.registration;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@SessionScope
@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private RegistrationForm registrationForm;

    @ModelAttribute
    public void addAttributes(Model model){
        model.addAttribute("registrationForm", registrationForm);
    }

    @GetMapping
    public String getRegistrationFormView(){
        return "registration/registration-view.html";
    }

    @PostMapping
    public String submitRegistrationForm(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
                                         BindingResult bindingResult){
        String viewName = "registration/registration-view.html";
        if(!bindingResult.hasErrors()){
            RegistrationDTO registrationDTO = new RegistrationDTO(registrationForm);
            registrationService.requestRegistration(registrationDTO);
            viewName = "redirect:registration/success";
        }
        return viewName;
    }

    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse httpServletResponse) throws IOException {
        byte[] captchaImage = registrationService.getCaptcha();
        InputStream inputStream = new ByteArrayInputStream(captchaImage);
        httpServletResponse.setContentType("image/png");
        IOUtils.copy(inputStream, httpServletResponse.getOutputStream());
    }

    @GetMapping("/success")
    public String getSuccessfulRegistrationView(){
        return "registration/successful-registration-view";
    }

    @ExceptionHandler(UsernameTakenException.class)
    public String usernameTakenExceptionHandler(Model model){
        BindingResult bindingResult = new BeanPropertyBindingResult(registrationForm, "registrationForm");
        bindingResult.addError(new FieldError("registrationForm", "username", "This username is already taken"));

        model.addAttribute(BindingResult.class.getName() + ".registrationForm", bindingResult);
        return "registration/registration-view";
    }

    @ExceptionHandler(InvalidVerificationCodeException.class)
    public String invalidVerificationCodeExceptionHandler(Model model){
        BindingResult bindingResult = new BeanPropertyBindingResult(registrationForm, "registrationForm");
        bindingResult.addError(new FieldError("registrationForm", "captchaVerificationCode", "Wrong verification code - try again."));

        model.addAttribute(BindingResult.class.getName() + ".registrationForm", bindingResult);
        return "registration/registration-view";
    }
}

