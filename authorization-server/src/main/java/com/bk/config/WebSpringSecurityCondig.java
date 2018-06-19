package com.bk.config;

import com.authorization.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(10)
@EnableWebSecurity
public class WebSpringSecurityCondig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserinfoService userinfoService;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userinfoService);
		authProvider.setPasswordEncoder(encoder());
		authProvider.setHideUserNotFoundExceptions(false);

		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}


	@Override
	protected void configure(HttpSecurity security) throws Exception {

		security.requestMatchers().anyRequest().
		    and().formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password").permitAll().
		    and().authorizeRequests().antMatchers("/oauth/*").permitAll();
		    //and().csrf().disable();
	}

	
}
