package guru.sfg.brewery.config;


import guru.sfg.brewery.security.JpaUserDetailsService;
import guru.sfg.brewery.security.RequestParamAuthFilter;
import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RequestParamAuthFilter requestParamAuthFilter(AuthenticationManager authenticationManager) {
        RequestParamAuthFilter filter = new RequestParamAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilterBefore(requestParamAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests(authorize ->
                        authorize
                                .antMatchers("/h2-console/**").permitAll() // do not use in production
                                .antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                                .antMatchers("/beers/find", "/beers*").permitAll()
                                .antMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").hasAnyRole("ADMIN", "USER", "CUSTOMER")
                                .antMatchers(HttpMethod.GET, "/api/v1/beer/**").hasAnyRole("ADMIN", "USER", "CUSTOMER")
                                .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                                .mvcMatchers(HttpMethod.GET, "/brewery/api/v1/breweries").hasAnyRole("CUSTOMER", "ADMIN")
                                .mvcMatchers("/brewery/breweries/**").hasAnyRole("CUSTOMER", "ADMIN")
                                .mvcMatchers("/customer/new").hasAnyRole("ADMIN")
                )
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();

        // h2 console config
        http.headers().frameOptions().sameOrigin();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("spring")
//                .password("guru")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }


    @Bean
    PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//        return new LdapShaPasswordEncoder();
//        return new StandardPasswordEncoder();
//        return new BCryptPasswordEncoder();

        //Delegate it to spring framework
        return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //it is do not need as it is one instance in application and spring know and take it
//    @Autowired
//    JpaUserDetailsService jpaUserDetailsService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication()
////                .withUser("spring")
////                .password("{bcrypt}$2a$12$/ADUThGsiVP4iW0BEFnVzuoUfjrZtc0r2LpHitTyV1LZGifUfAbvu") //{noop}
////                .roles("ADMIN")
////                .and()
////                .withUser("user")
////                .password("{sha256}921b95c2f47fb7c9886558d8a6d4e40ccbbb3b8153ae24d1cb537c7e9342d7b6d41ed58649518934") //{noop}
////                .roles("USER");
////
////        auth.inMemoryAuthentication()
////                .withUser("scott")
////                .password("{bcrypt10}$2a$15$8Ld2R4szo7mVFjAChizgsOchr42NRrAinsby6xDbCLhHkCjHest56") //{noop}
////                .roles("CUSTOMER");
//
////        auth.userDetailsService(jpaUserDetailsService).passwordEncoder(passwordEncoder());
//    }

}
