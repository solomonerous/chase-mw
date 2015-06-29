package com.obulex.chasemw.core.config;

import java.util.concurrent.CountDownLatch;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;

@EnableAutoConfiguration
@Configuration
public class ChaseMWConfig {
	
	public static final int NUMBER_OF_TRANSACTIONS = 5;
	
	@Bean
	public Environment env()
	{
		return new Environment();
	}
	
	@Bean
	public Reactor createReactor(Environment env)
	{
		return Reactors.reactor().
				env(env).
				dispatcher(Environment.THREAD_POOL).
				get();
	}
	
	@Bean
    public CountDownLatch latch() {
        return new CountDownLatch(NUMBER_OF_TRANSACTIONS);
    }
}
