package pl.piotr.iotdbmanagement.configuration.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http
                .authorizeRequests()

                    .antMatchers("/api_auth/users").hasAuthority("ADMIN")
                    .antMatchers("/api_auth/users/**").hasAuthority("ADMIN")

                    .antMatchers("/api_auth").authenticated()
                    .antMatchers("/api_auth/**").authenticated()

                    .anyRequest().permitAll()
                    .and()
                .httpBasic();
    }
}
