package com.obulex.jpos.service;

import org.jpos.iso.ISOMsg;

public interface MessageProcessor {
	 public ISOMsg process(ISOMsg message);
}
