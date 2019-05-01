package com.autoever.pilot.configs;

import com.autoever.pilot.common.security.RESTAuthenticationEntryPoint;
import com.autoever.pilot.common.security.RESTAuthenticationFailureHandler;
import com.autoever.pilot.common.security.RESTAuthenticationSuccessHandler;
import com.autoever.pilot.common.security.RESTLogoutSuccessHandler;
import com.autoever.pilot.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired UserService userService;

    @Autowired RESTAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired RESTAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired RESTAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired RESTLogoutSuccessHandler logoutSuccessHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(userService.passwordEncoder())
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // Restdocs security 적용 예외
        web.ignoring().mvcMatchers("/docs/index.html");
        // 기본 정적 리소스 security 적용 예외
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint)
                .and()

                .authorizeRequests()
                    .antMatchers("/api/users/**").hasAnyAuthority("USER")
//                    .antMatchers("/api/users/**").permitAll()
                    .antMatchers("/api/file").permitAll()
                    .anyRequest().permitAll()

                .and()

                .formLogin()
                    .loginProcessingUrl("/api/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(authenticationSuccessHandler)
                    .failureHandler(authenticationFailureHandler)
                .and()

                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true)
        ;

        http
                .sessionManagement()
                .maximumSessions(1) // 사용자 아이디 당 1개 세션 허용 가능
        ;
    }
}
