package com.dalhousie.csci5308.mms.configuration;
import com.dalhousie.csci5308.mms.dao.JDBCConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@Order(-1)
public class AdminLoginConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(JDBCConfiguration.getInstance().getDataSource())
                .usersByUsernameQuery("select USER_NAME, USER_PASSWORD, ENABLED from T_USER_DETAILS where USER_NAME=?")
                .authoritiesByUsernameQuery("select USER_NAME,USER_ROLE from T_USER_DETAILS where USER_NAME=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().antMatchers("/").permitAll().antMatchers("/register").permitAll();
        http.antMatcher("/admin/**").
                authorizeHttpRequests()
                .anyRequest()
                .hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginPage("/admin/Login")
                .loginProcessingUrl("/admin/Login")
                .defaultSuccessUrl("/admin/home")
                .permitAll().and()
                .logout()
                .logoutUrl("/logout")
                .and().exceptionHandling().accessDeniedPage("/admin/Login")
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
