package pl.achrzanowski.moneymanagementservletclient.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Value("${expenseServiceClientRegistrationId}")
    private String expenseServiceClientRegistrationId;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .antMatchers("/").permitAll()
                                .antMatchers("/content/**").permitAll()
                                .anyRequest().authenticated())
                .oauth2Login(oauth2Login ->
                    oauth2Login.loginPage("/oauth2/authorization/" + expenseServiceClientRegistrationId))
                .oauth2Client(Customizer.withDefaults());
        return httpSecurity.build();
    }

}
