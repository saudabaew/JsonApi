package rest.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated();
        http
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                .username("user")
                .password("1234")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .httpBasic().disable()
//                .formLogin().disable()
//                .csrf().disable()
//                .addFilterBefore(tokenAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("adminPassword").roles("USER", "ADMIN");
//        auth.authenticationProvider(tokenAuthenticationProvider(auth.getDefaultUserDetailsService()));
//    }
//
//    @Bean
//    public AuthenticationEntryPoint tokenAuthenticationEntryPoint() {
//        return new TokenAuthenticationEntryPoint();
//    }
//
//    @Bean
//    public AuthenticationProvider tokenAuthenticationProvider(UserDetailsService userDetailsService) {
//        return new TokenAuthenticationProvider(userDetailsService);
//    }
//
//    @Bean
//    public Filter tokenAuthenticationFilter(AuthenticationManager authenticationManager) {
//        return new TokenAuthenticationFilter(authenticationManager);
//    }

}
