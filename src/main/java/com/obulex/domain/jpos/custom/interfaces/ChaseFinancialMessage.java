package com.obulex.domain.jpos.custom.interfaces;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.obulex.domain.jpos.interfaces.FinancialMessage;

public class ChaseFinancialMessage extends FinancialMessage {

    public ChaseFinancialMessage(String mtiCode) {
        super(mtiCode);
    }

    @Override
    public void setLocalTransactionFeeAmount(String code) throws ISOException {
        this.setField(28, ISOUtil.padright(code,9, '0'));
    }

    @Override
    protected void setForwardingInstitution(String code) throws ISOException {
    }

    @Override
    protected void setToAcount(String toAccount) throws ISOException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setCardAcceptor(String ourId, String constant) throws ISOException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setMerchantCategory(String code) throws ISOException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setPOSEntryMode(String posEntryMode) throws ISOException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setPOSCondition(String code) throws ISOException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setReservedPrivate(String code) throws ISOException {
        // TODO Auto-generated method stub

    }
}
