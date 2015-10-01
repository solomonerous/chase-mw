package com.obulex.jpos.interfaces.domain;

public class FinancialMessage extends GeneralMessage{

	public FinancialMessage(String mtiCode) {
		super(mtiCode);
	}

	String mtiCode;

	public enum MTICODES {
		F_200,F_201,F_210,F_220,F_221,F_230;
		 @Override public String toString() {
			 String s = super.toString();
			 return s.split("_")[1];
		 }
	}
}
