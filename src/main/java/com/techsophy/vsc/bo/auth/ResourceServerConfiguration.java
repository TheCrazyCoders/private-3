package com.techsophy.vsc.bo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	JsonToUrlEncodedAuthenticationFilter jsonFilter;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		.addFilterBefore(jsonFilter, ChannelProcessingFilter.class)
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
		.antMatchers("/oauth/token/").permitAll()
		.antMatchers("/**").
		hasAnyAuthority(
				"ROLE_Reception",
				"ROLE_BackOffice", 
				"ROLE_Biometrics", 
				"ROLE_Cashier", 
				"ROLE_Typing",
				"ROLE_Embassy", 
				"ROLE_Hub", 
				"ROLE_Delivery",
				"ROLE_Manager",
				"ROLE_Auditor",
				"ROLE_Supervisor",
				"ACTUATOR")
		.and().httpBasic().and().csrf().disable();
		//		http
		//				.csrf().disable()
		//				.cors().disable()
		//				.authorizeRequests().antMatchers("/oauth/token/").permitAll()
		//				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		//				.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
		////				.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
		////				.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
		//		http
		//				.cors()
		//				.and()
		//				.csrf()
		//				.disable()
		//				.sessionManagement()
		//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		//				.and()
		//				.authorizeRequests()
		//				.antMatchers("/",
		//						"/favicon.ico",
		//						"/**/*.png",
		//						"/**/*.gif",
		//						"/**/*.svg",
		//						"/**/*.jpg",
		//						"/**/*.html",
		//						"/**/*.css",
		//						"/**/*.js")
		//				.permitAll()
		//				.antMatchers("/oauth/token/").permitAll()
		//				.anyRequest()
		//				.authenticated();

	}
}
