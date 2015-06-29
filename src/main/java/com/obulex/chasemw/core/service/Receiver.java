package com.obulex.chasemw.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import reactor.event.Event;
import reactor.function.Consumer;

@Service
public class Receiver implements Consumer<Event<Map<String,List<?>>>>{
	
	@Override
	public void accept(Event<Map<String, List<?>>> ev) {
		System.out.println("Event : ");
	}	
}