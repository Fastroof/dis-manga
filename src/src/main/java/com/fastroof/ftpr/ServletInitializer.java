package com.fastroof.ftpr;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The ServletInitializer Class.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configuration.
	 *
	 * @param application the spring application builder
	 * @return the spring application builder
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FtprApplication.class);
	}

}
