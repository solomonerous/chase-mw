package com.obulex.jpos.service;

import java.util.Date;

import org.jpos.iso.ISODate;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class MessagePreProcessor implements MessageProcessor {

	@Override
	public ISOMsg process(ISOMsg message) {
		ISOMsg result = (ISOMsg) message.clone();
		Date date = new Date();
	    try {
	    	if (message.getString("11") == null) {
			    /* random trace audit number */
			    String part1 = new StringBuilder(String.valueOf(date.getTime())).reverse().substring(0, 2);
			    String part2 = String.valueOf(Math.random()).substring(2, 4);
			    result.set(11, part1 + part2);
	    	}
	    	if (message.getString("37") == null) {
			    result.set(37, result.getString(11));
			}

			if (message.getString("7") == null) {
			    result.set(7, ISODate.getDateTime(date));
			}

			if (message.getString("12") == null) {
			    result.set(12, ISODate.getTime(date));
			}

			if (message.getString("13") == null) {
			    result.set(13, ISODate.getDate(date));
			}

		} catch (ISOException e) {
			e.printStackTrace();
		}

		
		return result;
	}

}
