package com.obulex.chasemw.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.Reactor;
import reactor.event.Event;

@Service
public class Publisher {
	@Autowired
    Reactor reactor;
	
    public void publishTransactions(Map<String, List<?>> transactionsMap) throws InterruptedException {
        long start = System.currentTimeMillis();


        for(String key : transactionsMap.keySet())
        {
        	Map<String,List<?>> receiverMap = new HashMap<>();
        	receiverMap.put(key, transactionsMap.get(key));
        	reactor.notify(key, Event.wrap(receiverMap));
        	receiverMap.clear();
        }        

        long elapsed = System.currentTimeMillis()-start;

        System.out.println("Elapsed time: " + elapsed + "ms");
        System.out.println("Average time per transaction: " + elapsed/transactionsMap.size() + "ms");
    }
}


