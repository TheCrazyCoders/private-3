package com.techsophy.vsc.bo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*@SpringBootApplication
@EntityScan("com.techsophy.vsc.entity")
@EnableJpaRepositories("com.techsophy.vsc.repository")*/

@EntityScan("com.techsophy.vsc.entity")
@EnableJpaRepositories("com.techsophy.vsc.repository")
@SpringBootApplication
@PropertySource("classpath:application.properties")
public class VscBoApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(VscBoApplication.class, args);
	}

}

