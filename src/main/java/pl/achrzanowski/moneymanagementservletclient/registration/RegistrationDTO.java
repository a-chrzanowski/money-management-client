package pl.achrzanowski.moneymanagementservletclient.registration;

import lombok.Getter;

@Getter
public class RegistrationDTO {

    private final String username;
    private final String firstName;
    private final String password;
    private final Boolean isAwareOfDataInsecurity;

    public RegistrationDTO(RegistrationForm registrationForm){
        registrationForm.encodePassword();
        this.username = registrationForm.getUsername();
        this.firstName = registrationForm.getFirstName();
        this.password = registrationForm.getPassword();
        this.isAwareOfDataInsecurity = registrationForm.getIsAwareOfDataInsecurity();
    }

}
