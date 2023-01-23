package pl.achrzanowski.moneymanagementservletclient.usermanagement.registration;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.achrzanowski.moneymanagementservletclient.validation.FieldsValueMatch;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Component
@Scope("prototype")
@FieldsValueMatch(field = "password", fieldMatch = "repeatedPassword", message = "- Passwords do not match")
public class RegistrationForm {

    @Size(min = 5, max = 40, message = "- Username must be between 5 and 40 characters long")
    @Pattern(regexp = "^[a-zA-Z0-9_\\-.]*$",
            message = "- Username should contain only lowercase and uppercase letters, numbers and (-), (_), (.) characters")
    private String username;
    @Size(min = 2, max = 40, message = "- First name must be between 2 and 40 characters long")
    @Pattern(regexp = "^[a-zA-Z]*$",
            message = "- First name should contain only lowercase and uppercase letters")
    private String firstName;
    @Size(min = 8, max = 25, message = "- Password should be between 8 and 25 characters long")
    @Pattern(regexp = "^(?=.*[0-9]).*$", message = "- Number is missing")
    @Pattern(regexp = "^(?=.*[A-Z]).*$", message = "- Uppercase letter is missing")
    @Pattern(regexp = "^(?=.*[a-z]).*$", message = "- Lowercase letter is missing")
    @Pattern(regexp = "^(?=.*[!?_\\-<>^&%$#*=+]).*$", message = "- Special character is missing")
    private String password;
    @NotEmpty(message = "- Repeat password")
    private String repeatedPassword;
    @AssertTrue(message = "- Confirm to continue")
    private Boolean isAwareOfDataInsecurity;
    @NotEmpty(message = "- Fill verification code")
    private String captchaVerificationCode;

    public void encodePassword(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.password = bCryptPasswordEncoder.encode(this.password);
        this.repeatedPassword = bCryptPasswordEncoder.encode(this.repeatedPassword);
    }
}

