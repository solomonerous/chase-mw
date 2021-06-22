package com.obulex.domain.jpos.custom.interfaces;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.obulex.domain.jpos.interfaces.FundsTransferMessage;

public class ChaseFundsTransferMessage extends FundsTransferMessage{
    /**
     *
     * @param mtiCode
     */
    public ChaseFundsTransferMessage(String mtiCode) {
        super(mtiCode);
    }

    @Override
    public void setReservedPrivate(String code) throws ISOException {
        this.setField(60,ISOUtil.padright(code,12, ' '));
    }

    @Override
    public void setLocalTransactionFeeAmount(String code) throws ISOException {
        this.setField(28, ISOUtil.padright(code,9, '0'));
    }

    @Override
    public void setForwardingInstitution(String code) throws ISOException {
        this.setField(33,code);
    }
}
