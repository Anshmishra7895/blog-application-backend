package com.example.Blog_application.configs;


import com.example.Blog_application.security.CustomUserDetailService;
import com.example.Blog_application.security.JwtAuthenticationEntryPoint;
import com.example.Blog_application.security.JwtAuthenticationFilter;
import com.example.Blog_application.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
//@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true) // by this we can apply security on every method
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(customizer -> customizer.disable());
//        // here we directly used the lambda expression, otherwise we have to make a object of Customizer<CsrfConfigurer<HttpSecurity>> customizer = new Customizer<CsrfConfigurer<HttpSecurity>>
//        // but because @Override Customizer it is a functional interface, so we can easily convert it into the lambda expression
//        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
//        // using above code we disable the csrf, and now anyone can see our api's data, but now if you want to enable to form we due below formLogin code
//        http.formLogin(Customizer.withDefaults());
//        // it now is showing the form on browser, but still in postman we are not getting the desired data, it's showing the form while hitting the api,
//        http.httpBasic(Customizer.withDefaults());
//        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
//        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(AppConstants.PUBLIC_STATIC_URLS)
                    .permitAll() // Allow access to the login endpoint
                    .requestMatchers(HttpMethod.GET).permitAll()
                    // by doing this we allow everyone to access GET Api's without token
                    .anyRequest().authenticated() // All other requests require authentication
            )
//            .formLogin(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .exceptionHandling(exceptionHandling ->
                    exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint))
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
}


    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthenticationManager authenticationManagerBean(){
//        return super.authenticationManagerBean();
//    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        // Configure your authentication provider here
        return authenticationManagerBuilder.build();
    }

}
