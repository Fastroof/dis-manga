package com.fastroof.imagestorage;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * The ServletInitializer Class.
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Configure.
     *
     * @param application the application
     * @return the spring application builder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ImageStorageApplication.class);
    }

}
