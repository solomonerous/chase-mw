package com.obulex.jpos.interfaces;

public interface Message {
	/**
	 *
	 * @author don
	 *
	 */
	public enum MessageType {
		ETWORK,FINANCIAL;
	}

	/**
	 *
	 * @param mtiCode
	 */
	void setMtiCode(String mtiCode);

	boolean isValidMessageType();
}
