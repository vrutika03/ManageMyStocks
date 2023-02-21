package com.dalhousie.csci5308.mms.configuration;


import com.dalhousie.csci5308.mms.dao.JDBCConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
@Configuration
@Order(1)
public class UserLoginConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(JDBCConfiguration.getInstance().getDataSource())
                .usersByUsernameQuery("select USER_NAME, USER_PASSWORD, ENABLED from T_USER_DETAILS where USER_NAME=?")
                .authoritiesByUsernameQuery("select USER_NAME,USER_ROLE from T_USER_DETAILS where USER_NAME=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/user/**").authorizeHttpRequests()
                .anyRequest().hasAuthority("USER")
                .and()
                .formLogin()
                .loginPage("/user/Login")
                .loginProcessingUrl("/user/Login")
                .defaultSuccessUrl("/user/home")
                .permitAll()
                .and().exceptionHandling().accessDeniedPage("/user/Login")
                .and().logout()
                .logoutUrl("/logout")
                .and().csrf().disable();
    }
}


