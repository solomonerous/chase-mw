package com.obulex.domain.jpos.interfaces;

import java.util.Date;

import org.jpos.iso.ISODate;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;

import com.obulex.jpos.interfaces.MessagePlan;


public class Message implements MessagePlan {

    private String mtiCode;
    protected ISOMsg isoMessage;

    public Message(String mtiCode)
    {
        this.mtiCode = mtiCode;
        this.setIsoMessage(new ISOMsg());
    }

    @Override
    public void setMtiCode() throws ISOException {
        this.isoMessage.setMTI(this.mtiCode);
    }

    @Override
    public void setTransactionDatetime(Date date) throws ISOException {
       this.setField(7, ISODate.getDateTime(date));
    }

    @Override
    public void setNetwork(String networkId) throws ISOException {
        this.setField(61,networkId);
    }

    @Override
    public ISOMsg getIsoMessage() {
        return this.isoMessage;
    }

    @Override
    public void setSTAN(long traceNumber) throws ISOException{
        this.setField(11,ISOUtil.zeropad (Long.toString (traceNumber), 6));
    }

    /**
     * @param isoMessage the isoMessage to set
     */
    private void setIsoMessage(ISOMsg isoMessage) {
        this.isoMessage = isoMessage;
    }


    /**
     * @return the mtiCode
     */
    public String getMtiCode() throws ISOException{
        return this.mtiCode;
    }

    public void setField(Integer field, String message)
    {
        try {
            this.isoMessage.set(field,message);
        } catch (ISOException e) {
            e.printStackTrace();
        }
    }
}
