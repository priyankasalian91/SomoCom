//package com.aipl.store.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//import com.aipl.store.service.impl.UserSecurityService;
//
//import utility.SecurityUtility;
//
//@Configuration
//@Order(1)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//	
//	@Autowired
//	private UserSecurityService userSecurityService;
//
//	
//	public BCryptPasswordEncoder passwordEncoder() {
//		return SecurityUtility.passwordEncoder();
//	}
//	
//	private static final String[] PUBLIC_MATCHERS = {
//			"/afcss/**",
//			"/afjs/**",
//			"/assets/**",
//			"/css/**",
//			"/js/**",
//			"/image/**",
//			"/somodesk-img/**",
//			"/somodesk-css/**",
//			"/somodesk-js/**",
//			"/somodesk-scss/**",
//
//			"/somodesk.in",
//			"/somodesk.in/login",
//			"/somodesk.in/register",
//			"/somodesk.in/pay",
//			"/somodesk.in/home",
//			"/somodesk.in/af/{link}",
//			"/somodesk.in/createPayment",
//			"/somodesk.in/verifyPayment",
//			"/somodesk.in/somoaffiindex",
//			"/v/new",
//			"/v/add"
//
//			
//	};
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()
//			.antMatchers(PUBLIC_MATCHERS).permitAll()
//			.anyRequest().authenticated();
//		
//		
//		http
//		.antMatcher("/somodesk.in/**")
//		.authorizeRequests().anyRequest().authenticated()
//		.and().formLogin().loginPage("/somodesk.in/login")
//			  .defaultSuccessUrl("/somodesk.in/home", true)
//			.failureUrl("/somodesk.in/login?error")
//		.permitAll()
//		.and().logout().logoutUrl("/somodesk.in/logout").logoutSuccessUrl("/somodesk.in/login").deleteCookies("remember-me").permitAll()
//		.and()
//		.rememberMe().key("aSecretKey");
//	http.csrf().disable();
//		
//	
//	}
//	
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
//	}
//	
//
//
//}
