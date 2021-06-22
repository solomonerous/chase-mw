package com.obulex.jpos.interfaces;

import org.jpos.iso.ISOException;

public interface NetworkMessagePlan extends MessagePlan {
    /**
     *
     * @throws ISOException
     */
    void setNetworkManagementInfo() throws ISOException;
}
