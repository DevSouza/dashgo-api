package br.com.dashgo.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.dashgo.security.filter.JwtTokenAuthorizationFilter;
import br.com.dashgo.security.handle.FilterChainExceptionHandler;
import br.com.dashgo.security.handle.ResolverAccessDeniedHandler;
import br.com.dashgo.security.user.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity(
//		debug = true
)
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private UserDetailsServiceImpl userDetailsService;
	@Autowired private FilterChainExceptionHandler filterChainExceptionHandler;
	@Autowired private ResolverAccessDeniedHandler resolverAccessDeniedHandler;
	
	@Bean
	public JwtTokenAuthorizationFilter authenticationJwtTokenFilter() {
		return new JwtTokenAuthorizationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
			.csrf().disable()
			.exceptionHandling()
			.accessDeniedHandler(resolverAccessDeniedHandler)
			.and()
			
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			
			// AuthController
			.antMatchers(HttpMethod.POST, "/auth/signin").permitAll()
			.antMatchers(HttpMethod.POST, "/auth/refreshtoken").permitAll()
			.anyRequest().authenticated();
		
		http
			.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(filterChainExceptionHandler, ChannelProcessingFilter.class);
	}
}
