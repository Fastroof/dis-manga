package com.fastroof.ftpr;

import com.fastroof.ftpr.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Application Class.
 */
@SpringBootApplication
@EnableSwagger2
public class FtprApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FtprApplication.class, args);
	}

	/**
	 * The WebSecurityConfig Class.
	 */
	@EnableWebSecurity
	@Configuration
	@EnableGlobalMethodSecurity(prePostEnabled = true)
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		/** The JWT authorization filter. */
		@Autowired
		JWTAuthorizationFilter jwtAuthorizationFilter;

		/**
		 * Security configuration.
		 *
		 * @param http the http
		 * @throws Exception the exception
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
					.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/books","/books/**").permitAll()
					.antMatchers(HttpMethod.POST, "/help-request").permitAll()
					.antMatchers("/v2/api-docs",
							"/configuration/ui",
							"/swagger-resources/**",
							"/configuration/security",
							"/swagger-ui/**",
							"/javadoc/**",
							"/webjars/**").permitAll()
					.anyRequest().authenticated();
		}
	}
}
