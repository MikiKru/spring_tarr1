package pl.tarr1.spring_app.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity      // zabezpieczenia dla żądań protokołu http
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    // metoda zabezpieczająca wykonywanie żądań protokołu http
    protected void configure(HttpSecurity httpSecurity){
        

    }

    // metoda wykonująca zapytanie do bazy danych i uwierzytelniająca zalogowanego usera

}
