package pl.tarr1.spring_app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity      // zabezpieczenia dla żądań protokołu http
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    // metoda zabezpieczająca wykonywanie żądań protokołu http
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()       // autoryzuj poniższe żądania -> wskazuje które żądania wymagają określonych upranień
                    .antMatchers("/addPost").hasAnyAuthority("ROLE_USER")
                    .antMatchers("/posts&**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                    .antMatchers("/logout").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .anyRequest()              // każde żądanie nie wymienione pod autorizeRequests()
                    .permitAll()           // nie wymaga zalogowania
                .and()
                    .csrf().disable()      // wyłączenie mechanizmu csrf
                .formLogin()               // konfiguracja dedykowanego w aplikacji formularza logowania
                    .loginPage("/login")   // konfiguracja mapingu formularza logowania
                    .usernameParameter("email")     // konfiguracja nazwy pola name w formularzu login.html
                    .passwordParameter("password")  // konfiguracja nazwy pola name w formularzu login.html
                    .loginProcessingUrl("/login_process")   // adres gdzie przekazujemy dane logowania -> nie ma go w mappingu w kontrolerze
                    .defaultSuccessUrl("/")                 // przekierowanie po poprawnym zalogowaniu metodą GET
                    .failureUrl("/login_error")             // przekierowanie po błędnym logowaniu metodą GET
                .and()
                    .logout()               // metoda wylogowująca usera -> czyszcząca dane logowania (obiekt Authentication)
                        .logoutUrl("/logout")               // adres do wylogowania (GET mapping) -> nie występuje w controllerze
                        .logoutSuccessUrl("/");             // przekierowanie po wylogowaniu
    }
    @Autowired
    private DataSource dataSource;

    // metoda wykonująca zapytanie do bazy danych i uwierzytelniająca zalogowanego usera
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.jdbcAuthentication()   // do autoryzacji wykorzystujemy JDBC
             .usersByUsernameQuery("SELECT u.email, u.password, u.status FROM user u WHERE u.email = ?")
             .authoritiesByUsernameQuery(
                     "SELECT u.email, r.role_name FROM user u JOIN user_role ur ON (u.user_id = ur.user_id) JOIN role r ON (r.role_id = ur.role_id) WHERE u.email = ?"
             )
             .dataSource(dataSource)  // obiekt do którego zapisywany jest wynik logowania
             .passwordEncoder(new BCryptPasswordEncoder());     // szyfrowanie hasła na podstawie algorytmu BCrypt
    }

}
