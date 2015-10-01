package com.obulex.jpos.interfaces.domain;

import com.obulex.jpos.interfaces.Message;

public class GeneralMessage implements Message{

	protected String mtiCode;

	public enum MTICODES {
		F_200,F_201,F_210,F_220,F_221,F_230,N_800,N_801,N_810;
		@Override public String toString() {
			String s = super.toString();
			return s.split("_")[1];
		}
	}

	public GeneralMessage(String mtiCode) {
		this.setMtiCode(mtiCode);
	}

	@Override
	public void setMtiCode(String mtiCode) {
		this.mtiCode = mtiCode;
	}

	@Override
	public boolean isValidMessageType() {
		boolean valid = false;
		for(MTICODES code: MTICODES.values())
		{
			if(code.toString() == mtiCode)
			{
				valid = true;
				break;
			}
		}
		return valid;
	}
}
