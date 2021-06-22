package com.obulex.jpos.interfaces;

import java.util.Date;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public interface MessagePlan {

    /**
     *
     */
    void setMtiCode() throws ISOException;
    /**
     * computes current JPOS ISO date time
     * @throws ISOException
     */
    void setTransactionDatetime(Date date) throws ISOException;

    /**
     *
     * @param networkId
     */
    void setNetwork(String networkId) throws ISOException;

    /**
     * @param traceNumber
     * @throws ISOException
     */
    void setSTAN(long traceNumber) throws ISOException;

    /**
     *
     * @return ISOMsg
     */
    ISOMsg getIsoMessage();
}
