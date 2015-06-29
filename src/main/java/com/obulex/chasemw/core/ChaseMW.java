package com.obulex.chasemw.core;

import static reactor.event.selector.Selectors.$;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.Reactor;

import com.obulex.chasemw.core.service.Publisher;
import com.obulex.chasemw.core.service.Receiver;

@SpringBootApplication
public class ChaseMW implements CommandLineRunner{
	
	@Autowired
    private Reactor reactor;

    @Autowired
    private Receiver receiver;

    @Autowired
    private Publisher publisher;
    
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Starting ChaseMW");		
		
		Map<String, List<?>> transactionMap = new HashMap<String, List<?>>();
		List<String> isoList = new ArrayList<String>();
		isoList.add("Don");
		isoList.add("Anne");
		isoList.add("Soraya");
		isoList.add("Zamira");
		transactionMap.put(TransactionType.ISO.toString(), isoList);
		if(transactionMap.size() > 0)
		{
			for(TransactionType transactionType : TransactionType.values())
			{
				if(transactionMap.containsKey(transactionType.toString()))
				{
					System.out.println("Start rector for : " + transactionType.toString() + " receiver");
					reactor.on($(transactionType.toString()), receiver);
				}				
			}
			publisher.publishTransactions(transactionMap);
		}
		System.out.println("END");
	}
	
	public static void main(String[] args) {
        SpringApplication.run(ChaseMW.class, args);
    }
}
