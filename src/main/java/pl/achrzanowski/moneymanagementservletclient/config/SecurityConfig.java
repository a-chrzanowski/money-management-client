package pl.achrzanowski.moneymanagementservletclient.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests.anyRequest().authenticated())
                .oauth2Client(Customizer.withDefaults());
        return httpSecurity.build();
    }

}
