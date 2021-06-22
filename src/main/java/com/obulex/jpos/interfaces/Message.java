package com.obulex.jpos.interfaces;

public interface Message {
	/**
	 *
	 * @author don
	 *
	 */
	public enum MessageType {
		NETWORK,FINANCIAL;
	}

	/**
	 *
	 * @param mtiCode
	 */
	void setMtiCode(String mtiCode);

	boolean isValidMessageType();
}
