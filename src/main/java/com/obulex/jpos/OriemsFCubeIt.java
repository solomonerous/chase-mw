package com.obulex.jpos;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.jpos.q2.Q2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationPid;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;


public class OriemsFCubeIt {
	@PostConstruct
    private void pid() throws IOException {
        File file = new File("ofcubeit.pid");
        new ApplicationPid().write(file);
        file.deleteOnExit();
    }
	private static Logger oriemslog = LoggerFactory.getLogger(OriemsFCubeIt.class);
	public static void main(String[] args) {
		oriemslog.debug("Starting OriemsFCubeIt...");
		ApplicationContext ctx = SpringApplication.run(OriemsFCubeIt.class, args);
		oriemslog.debug("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
        	oriemslog.debug(beanName);
        }
        Q2 q2= new Q2();
        q2.start();
        oriemslog.debug("starting Q2 ...");
    }
}
