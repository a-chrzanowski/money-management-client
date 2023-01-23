package pl.achrzanowski.moneymanagementservletclient.usermanagement.registration;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RegistrationDTO {

    private final String requestId;
    private final String username;
    private final String firstName;
    private final String password;
    private final Boolean isAwareOfDataInsecurity;
    private final String captchaVerificationCode;

    public RegistrationDTO(RegistrationForm registrationForm){
        registrationForm.encodePassword();
        this.requestId = UUID.randomUUID().toString();
        this.username = registrationForm.getUsername();
        this.firstName = registrationForm.getFirstName();
        this.password = registrationForm.getPassword();
        this.isAwareOfDataInsecurity = registrationForm.getIsAwareOfDataInsecurity();
        this.captchaVerificationCode = registrationForm.getCaptchaVerificationCode();
    }

}
