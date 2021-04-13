package by.vsu.soa.ioay.config;

import by.vsu.soa.ioay.security.MessagePermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MessagePermissionEvaluator permissionEvaluator;

    //@Autowired private UsernameAuthenticationFailureHandler authFailureHandler;

    //@Autowired private UsernamePasswordAuthenticationFilter filter;

    @Autowired
    protected void configureGlobal(final AuthenticationManagerBuilder auth, final UserDetailsService userDetailsService) throws Exception {
        try{
            auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        } catch (Exception e) {
            throw new RuntimeException(e);
        };
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        //http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        http
            .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**", "/**/favicon.ico", "/login", "/info").permitAll()
                .antMatchers("/*.xlsx").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login")
                //.failureHandler(authFailureHandler)
                //.successHandler(authSuccesshandler)
                .and()
            /*.rememberMe()
                .rememberMeCookieName("remember-me")
                .tokenValiditySeconds(tokenExpiredTime)
                .tokenRepository(rememberMeService)
                .and()*/
            .logout()
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
            .headers()
                .frameOptions().sameOrigin();

        http.csrf().disable();
    }

    @Override
    public void configure(final WebSecurity web) {
        final DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(permissionEvaluator);
        web.expressionHandler(handler);
    }
/*
    @Bean
    public UsernamePasswordAuthenticationFilter userPassAuthFilter() throws Exception {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
*/
}
