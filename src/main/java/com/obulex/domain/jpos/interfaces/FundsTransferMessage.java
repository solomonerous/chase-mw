package com.obulex.domain.jpos.interfaces;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

public abstract class FundsTransferMessage extends FinancialMessage {

    public FundsTransferMessage(String mtiCode) {
        super(mtiCode);
    }

    @Override
    public void setToAcount(String toAccount) throws ISOException {
        this.setField(103,toAccount);
    }

    @Override
    public void setCardAcceptor(String ourId, String constant) throws ISOException {
        this.setField(42,ourId + ISOUtil.padleft(constant, 9, '0'));
    }

    @Override
    public void setMerchantCategory(String code) throws ISOException {
        this.setField(18,code);
    }

    @Override
    public void setPOSEntryMode(String posEntryMode) throws ISOException {
        this.setField(22,posEntryMode);
    }

    @Override
    public void setPOSCondition(String code) throws ISOException {
        this.setField(25,code);
    }

    @Override
    public void setForwardingInstitution(String code) throws ISOException
    {
        this.setField(33,code);
    }
}
