package com.aipl.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.aipl.store.service.AffiliateUserService;
import com.aipl.store.service.impl.UserSecurityService;

import utility.SecurityUtility;


@Configuration
//@Order(2)
public class SecurityConfigCom extends WebSecurityConfigurerAdapter {
	

	@Autowired
	private UserSecurityService userSecurityService;


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}
	
	private static final String[] PUBLIC_MATCHERS = {
			"/wip",
			"/assets/**",
			"/aipl/**",
			"/cherise/**",
			"/css/**",
			"/js/**",
			"/fonts/**",
			"/image/**",
			"/",
			"/ecom_login",
			"/new-user",
			"/store",
			"/store/**",
			"/articleList/**",
			"/article-detail/**",
			"/v/add",
			"/v/new",
			"/af/{link}",
			"/somoaffiindex",
			"/somoIndex",
			"/login",
			"/register",
			"/brands",
			"/microsites-aipl",
			"/ecom_register",
			"/tapri",
			"/checkingEmail",
			"/privacy-policy",
			"/cookie-policy",
			"/terms",
			"/return-policy",
			"/shipping-policy",
			"/addfeedback",
			"/bulk-purchase",
			"/aboutus",
			"/contactus",
			"/test/**",
			"/test",
			"/old_home/**"
			
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		
		http
		.antMatcher("/**")
		.authorizeRequests().anyRequest().authenticated()
		.and().formLogin().loginPage("/ecom_login").usernameParameter("email")
			.failureUrl("/ecom_login?error")
		.permitAll()
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/ecom_login").deleteCookies("remember-me").permitAll()
		.and()
		.rememberMe().key("aSecretKey");
	
		http.csrf().disable();
			
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
	


}
