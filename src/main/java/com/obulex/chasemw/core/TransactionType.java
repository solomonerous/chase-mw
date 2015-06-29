package com.obulex.chasemw.core;

import org.apache.commons.lang3.text.WordUtils;

public enum TransactionType {
	ISO, MPESA, RTGS, FEEDBACK_MESSAGE;
	
	 @Override public String toString() {
		 String s = super.toString();
		 return s.toLowerCase();
	 }
	 
	 public String toMethodString() {
		 String s = super.toString();
		 s = s.toLowerCase();
		 s = WordUtils.capitalize(s, '_');
		 return s.substring(0,1).toLowerCase() + s.substring(1);
	 }
	 
}
