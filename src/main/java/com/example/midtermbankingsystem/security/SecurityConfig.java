package com.example.midtermbankingsystem.security;

import com.example.midtermbankingsystem.filter.CustomAuthenticationFilter;
import com.example.midtermbankingsystem.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests().anyRequest().authenticated()
//                .and()
//                .httpBasic();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("lisa")
//                .password(encoder.encode("lisa"))
//                .roles("ADMIN");
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderConfig.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**").permitAll();
        http.authorizeRequests().antMatchers(POST, "/api/transactions/third-party/new").permitAll();


        http.authorizeRequests().antMatchers(PATCH, "/api/accounts/admin/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/api/accounts/admin/delete/{id}").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/accounts/balance/{id}").hasAnyAuthority("ACCOUNT_HOLDER","ADMIN");

        http.authorizeRequests().antMatchers(POST, "/api/account-holders/admin/new").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/api/account-holders/admin/delete/{id}").hasAnyAuthority("ADMIN");

        http.authorizeRequests().antMatchers(POST, "/api/admins/admin/new").hasAnyAuthority("ADMIN");

        http.authorizeRequests().antMatchers(POST, "/api/checking-accounts/admin/new").hasAnyAuthority("ADMIN");

        http.authorizeRequests().antMatchers(POST, "/api/credit-card-accounts/admin/new").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/credit-card-accounts/{id}").hasAnyAuthority("ACCOUNT_HOLDER","ADMIN");

        http.authorizeRequests().antMatchers(POST, "/api/savings-accounts/admin/new").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/savings-accounts/{id}").hasAnyAuthority("ACCOUNT_HOLDER","ADMIN");

        http.authorizeRequests().antMatchers(POST, "/api/student-checking-accounts/admin/new").hasAnyAuthority("ADMIN");

        http.authorizeRequests().antMatchers(POST, "/api/third-parties/admin/new").hasAnyAuthority("ADMIN");

        http.authorizeRequests().antMatchers(POST, "/api/transactions/account-holder/new").hasAnyAuthority("ACCOUNT_HOLDER");


        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
