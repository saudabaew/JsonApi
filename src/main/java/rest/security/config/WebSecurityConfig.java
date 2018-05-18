package rest.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import rest.util.DataSourceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSourceUtil dataSourceUtil;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{

        MessageDigestPasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("MD5");

        JdbcTemplate jdbcTemplateSakura = new JdbcTemplate(dataSourceUtil.getDataSourceSakura());
        String passwordOfDB = jdbcTemplateSakura.queryForObject("SELECT password FROM public.users where login = 'tc_rsm8'", String.class);
        System.out.println("Password of DB Sakura = " + passwordOfDB);
        System.out.println("Password encode = " + passwordEncoder.encode("tc_rsm8"));
        if (passwordEncoder.matches("tc_rsm8", passwordOfDB)) System.out.println("Пароли совпадают");
        else System.out.println("Пароли не совпадают!!!");

        List<Map<String, Object>> roles = jdbcTemplateSakura.queryForList
                ("SELECT t3.name FROM public.users t1, public.user2role t2, public.roles t3 where t1.id = t2.id_user and t2.id_role = t3.id and t1.login = 'tc_rsm8'");
        System.out.println("Роли пользователя:" + roles);

        auth.jdbcAuthentication()
                .dataSource(dataSourceUtil.getDataSourceSakura())
                .usersByUsernameQuery
                        ("SELECT login, password, enabled FROM public.users where login = ?")
                .authoritiesByUsernameQuery
                        ("SELECT t1.login, t3.name FROM public.users t1, public.user2role t2, " +
                                "public.roles t3 where t1.id = t2.id_user and t2.id_role = t3.id and t1.login = ?")
                .passwordEncoder(passwordEncoder).rolePrefix("ROLE_");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/idunit/**").hasAnyRole("ROLE_ACCOUNT",
                "ROLE_MESSAGE", "ROLE_OBJECT", "ROLE_PARAM", "ROLE_ROLE", "ROLE_SENSOR", "ROLE_UNIT", "ROLE_USER", "ROLE_USER2ROLE",
                "ROLE_USER2OBJECT", "ROLE_USER2TARIF", "ROLE_USER2UNIT", "ROLE_CURRENT_PARAM", "ROLE_EVENT", "ROLE_JOURNAL", "ROLE_TASK",
                "ROLE_USER2UNITGROUP",
                "ROLE_CONDITION",
                "ROLE_SENSORTYPE",
                "ROLE_EVENTTYPE",
                "ROLE_RESULT",
                "ROLE_CONDITIONGROUP",
                "ROLE_UNITGROUP",
                "ROLE_UNITTYPE",
                "ROLE_USERTYPE",
                "ROLE_CATALOG",
                "ROLE_CATALOGTYPE",
                "ROLE_CATALOGRECORD",
                "ROLE_AGGREGATEMODEL",
                "ROLE_AGGREGATETYPE",
                "ROLE_AGGREGATEBRAND")
                .anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll();

        http.csrf().disable();
    }


    //авторизация в памяти
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
//                .anyRequest().authenticated();
//        http
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("1234")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    //авторизация через фильтры
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
