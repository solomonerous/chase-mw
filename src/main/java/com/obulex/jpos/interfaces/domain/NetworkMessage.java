package com.obulex.jpos.interfaces.domain;

public class NetworkMessage extends GeneralMessage{

	public NetworkMessage(String mtiCode) {
		super(mtiCode);
	}

	String mtiCode;

	public enum MTICODES {
		 N_800,N_801,N_810;
		 @Override public String toString() {
			 String s = super.toString();
			 return s.split("_")[1];
		 }
	}
}
