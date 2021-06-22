package com.obulex.domain.jpos.interfaces;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.obulex.jpos.interfaces.NetworkMessagePlan;

public class NetworkMessage extends Message implements NetworkMessagePlan {

    public NetworkMessage(String mtiCode) {
        super(mtiCode);
    }

    @Override
    public void setNetworkManagementInfo() throws ISOException {
        this.isoMessage.set(70,ISOUtil.zeropad(1, 3));
    }

}
