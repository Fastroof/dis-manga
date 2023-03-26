package com.fastroof.ftpr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class FtprApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtprApplication.class, args);
	}

//	@EnableWebSecurity
//	@Configuration
//	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//		@Override
//		protected void configure(HttpSecurity http) throws Exception {
//			http.csrf().disable()
//					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
//					.authorizeRequests()
//					.antMatchers(HttpMethod.POST, "/login","/register", "/help-request").permitAll()
//					.anyRequest().authenticated();
//		}
//	}
}
