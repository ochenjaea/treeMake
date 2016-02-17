package com.taxholic.core.configuration.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.taxholic.core.authority.AuthService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(authService()).passwordEncoder( new BCryptPasswordEncoder());
	}
	
	@Override
	public void configure( WebSecurity web ) throws Exception {
		web
		    .ignoring()
		        .antMatchers( "/js/**" )
		        .antMatchers( "/css/**" )
		        .antMatchers( "/images/**" )
		        ;
	}
 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
		http
		.csrf().disable()
		.authorizeRequests()
		 	.antMatchers( "/login**" ).permitAll()
			.antMatchers("/admin/**").access("hasRole('ROLE_ADM')")
			.antMatchers("/system/**").access("hasRole('ROLE_ADM') or hasRole('ROLE_SYS')")
			.and()
		.formLogin()
			.loginPage( "/login.do" )
	        .loginProcessingUrl( "/login" )
	        .defaultSuccessUrl( "/admin/main.do" )
	        .failureUrl( "/login.do?err=1" )
	        .usernameParameter( "userNm" )
	        .passwordParameter( "password" )
	        .and()
		.logout()
			.logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ) )
			.logoutSuccessUrl( "/" )
			.deleteCookies( "JSESSIONID" )
			.invalidateHttpSession( true )
			.and()
		.rememberMe()
			.tokenValiditySeconds(864000)
			.and()
		.sessionManagement()			//첫번째 로그인 사용자는 로그아웃, 두번째 사용자 로그인 session-registry-alias : 접속자 정보보기
			.maximumSessions(1)
			.expiredUrl("/expireSession")
		;
			 
	}
	
	@Bean
    public UserDetailsService authService(){
        return new AuthService();
    }
	
  
}