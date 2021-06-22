package com.obulex.jpos.service;

import org.jpos.core.Configuration;

import com.obulex.domain.jpos.interfaces.FundsTransferMessage;

public abstract class FundsTransferMessenger extends FinancialMessenger {

    protected static final String MERCHANT_CATEGORY_CODE="merchant-category-code";
    protected static final String POS_GOOD_CONDITION = "pos-good-condition";
    protected static final String CARD_ACCEPTOR_CODE="card-acceptor-code";
    protected static final String POS_ENTRY_MODE = "pos-entry-mode";

    protected String toAccount = null;
    protected String cardAcceptorConstant;
    protected String merchantCategory;
    protected String pOSEntryMode;
    protected String pOSCondition;

    public FundsTransferMessenger(FundsTransferMessage messageBuilder, Configuration config, long traceNumber) {
        super(messageBuilder, config, traceNumber);
    }

    @Override
    public void buildISOMessage()
    {

    }

    @Override
    public void setToAcount(String toAccount) {
       this.toAccount = toAccount;
    }

    @Override
    public void setCardAcceptorConstant(String constant) {
        this.cardAcceptorConstant = constant;
    }

    @Override
    public void setMerchantCategory(String code) {
        this.merchantCategory = code;
    }

    @Override
    public void setPOSEntryMode(String posEntryMode) {
        this.pOSEntryMode = posEntryMode;
    }

    @Override
    public void setPOSCondition(String code) {
        this.pOSCondition = code;
    }

    protected abstract void customField(Integer field, String rawMessage);
}
