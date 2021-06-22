package com.obulex.domain.jpos.interfaces;

import java.util.Date;
import java.util.TimeZone;

import org.jpos.iso.ISOCurrency;
import org.jpos.iso.ISODate;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;

import com.obulex.jpos.interfaces.FinancialMessagePlan;

public abstract class FinancialMessage extends Message implements FinancialMessagePlan {

    protected Date now;

     public FinancialMessage(String mtiCode) {
        super(mtiCode);
        now = new Date();
    }
    @Override
    public void setPAN(String ourId) throws ISOException {
        this.setField(2, ISOUtil.padright(ourId,16, '9'));
    }

    @Override
    public void setProcessingCode(String code) throws ISOException {
       this.setField(3,ISOUtil.padright(code,6, '0'));
    }

    @Override
    public void setLocalTransactionTime(String timezone) throws ISOException {
        this.setField(12,ISODate.getTime(this.now,
                TimeZone.getTimeZone(timezone)));
    }

    @Override
    public void setLocalTransactionDate(String timezone) throws ISOException {
        this.setField(13,ISODate.getDate(this.now,
                TimeZone.getTimeZone(timezone)));
    }

    @Override
    public void setAquirerInstitution(String ourId) throws ISOException {
        this.setField(32,ourId);
    }

    @Override
    public void setCardAcceptorTerminal(String terminalId) throws ISOException {
        this.setField(41,terminalId);
    }

    @Override
    public void setCardAcceptorNameAddress(String ourAddress) throws ISOException {
        this.setField(43,ourAddress);
    }

    @Override
    public void setTransactionCurrency(String currency) throws ISOException {
        this.setField(49,
                ISOCurrency.getIsoCodeFromAlphaCode(currency));
    }

    @Override
    public void setRetrievalReference(long traceNumber) throws ISOException {
        this.setField(37,
                ISOUtil.padleft(Long.toString (traceNumber), 12, '0'));
    }

    @Override
    public void setFromAccount(String fromAccount) throws ISOException {
        this.setField(102,fromAccount);
    }


    @Override
    public void setAmount(Double amount, String defaultCurrency) throws ISOException {
       this.setField(4,ISOCurrency.convertToIsoMsg(amount,
                     ISOCurrency.getIsoCodeFromAlphaCode(defaultCurrency)));
    }

    /**
    *
    * @param toAccount
    * @throws ISOException
    */
   protected abstract void setToAcount(String toAccount) throws ISOException;
   /**
    *
    * @param ourId
    * @param constant
    * @throws ISOException
    */
   protected abstract void setCardAcceptor(String ourId, String constant) throws ISOException;
   /**
    *
    * @param code
    * @throws ISOException
    */
   protected abstract void setMerchantCategory(String code) throws ISOException;
   /**
    *
    * @param posEntryMode
    * @throws ISOException
    */
   protected abstract void setPOSEntryMode(String posEntryMode) throws ISOException;
   /**
    *
    * @param code
    * @throws ISOException
    */
   protected abstract void  setPOSCondition(String code) throws ISOException;
   /**
    *
    * @param code
    * @throws ISOException
    */
   protected abstract void setLocalTransactionFeeAmount(String code) throws ISOException;
   /**
    *
    * @param code
    * @throws ISOException
    */
   protected abstract void setForwardingInstitution(String code) throws ISOException;

   /**
    *
    * @param code
    * @throws ISOException
    */
   protected abstract void setReservedPrivate(String code) throws ISOException;
}
