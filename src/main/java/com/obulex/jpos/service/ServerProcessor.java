package com.obulex.jpos.service;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class ServerProcessor implements MessageProcessor {

	@Override
	public ISOMsg process(ISOMsg message) {
		ISOMsg result = (ISOMsg) message.clone();
		try {
			result.setResponseMTI();
			result.set(39, "00");
		} catch (ISOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
    }

}
