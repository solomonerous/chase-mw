package com.obulex.jpos.service;

import java.util.HashMap;
import java.util.Map;

import org.jpos.core.Configuration;

import com.obulex.domain.jpos.interfaces.FinancialMessage;
import com.obulex.jpos.interfaces.FinancialMessengerPlan;

public abstract class FinancialMessenger extends Messenger implements FinancialMessengerPlan{

    protected static final String LOCAL_TRANSACTION_FEE_CODE="local-transaction-fee-code";

    protected Double amount = null;
    protected String fromAccount = null;
    protected String localTransactionFeeAmount = null;
    protected Map<Integer, String> customFields = new HashMap<Integer, String>();

    public FinancialMessenger(FinancialMessage messageBuilder, Configuration config ,
            long traceNumber) {
        super(messageBuilder, config, traceNumber);
    }

    @Override
    public void buildISOMessage()
    {
    }

    @Override
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    @Override
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    protected abstract void setProcessingCode(String code);

    /**
    *
    * @param toAccount
    */
   protected abstract void setToAcount(String toAccount);
   /**
    *
    * @param constant
    */
   protected abstract void setCardAcceptorConstant(String constant);
   /**
    *
    * @param code
    */
   protected abstract void setMerchantCategory(String code);
   /**
    *
    * @param posEntryMode
    */
   protected abstract void setPOSEntryMode(String posEntryMode);
   /**
    *
    * @param code
    */
   protected abstract void setPOSCondition(String code);
   /**
    *
    * @param code
    */
   protected abstract void setReservedPrivate(String code);

   /**
   *
   * @param code
   */
   protected abstract void setLocalTransactionFeeAmount(String code);
}
