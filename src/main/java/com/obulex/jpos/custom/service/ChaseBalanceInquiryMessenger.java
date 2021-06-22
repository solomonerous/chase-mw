package com.obulex.jpos.custom.service;

import java.util.Date;

import org.jpos.core.Configuration;
import org.jpos.iso.ISOException;

import com.obulex.domain.jpos.custom.interfaces.ChaseFinancialMessage;
import com.obulex.domain.jpos.interfaces.FinancialMessage;
import com.obulex.jpos.service.FinancialMessenger;

public class ChaseBalanceInquiryMessenger extends FinancialMessenger {

    private String processingCode;

    public ChaseBalanceInquiryMessenger(ChaseFinancialMessage messageBuilder,
            Configuration config, long traceNumber) {
        super(messageBuilder, config, traceNumber);
        this.setAmount(0.0);
        this.setLocalTransactionFeeAmount(this.config.get(LOCAL_TRANSACTION_FEE_CODE));
    }

    @Override
    public void buildISOMessage()
    {
        try {
            this.checkForNull();

            this.messageBuilder.setMtiCode();
            ((FinancialMessage) this.messageBuilder).setPAN(this.config.get(OUR_ID_KEY));//2
            ((FinancialMessage) this.messageBuilder)
                .setProcessingCode(this.processingCode);//3
            ((FinancialMessage) this.messageBuilder).setAmount(amount,
                    this.config.get(CURRENCY_KEY));//4
            this.messageBuilder.setTransactionDatetime(new Date());//7
            this.messageBuilder.setSTAN(this.traceNumber);//11
            ((FinancialMessage) this.messageBuilder)
                .setLocalTransactionTime(this.config.get(TIMEZONE_KEY));//12
            ((FinancialMessage) this.messageBuilder)
                .setLocalTransactionDate(this.config.get(TIMEZONE_KEY));//13
            ((ChaseFinancialMessage) this.messageBuilder)
                .setLocalTransactionFeeAmount(this.localTransactionFeeAmount);//28
            ((FinancialMessage) this.messageBuilder)
                .setAquirerInstitution(this.config.get(OUR_ID_KEY));//32
            ((FinancialMessage) this.messageBuilder)
                .setRetrievalReference(this.traceNumber);//37
            ((FinancialMessage) this.messageBuilder)
                .setCardAcceptorTerminal(this.config.get(TERMINAL_ID_KEY));//41
            ((FinancialMessage) this.messageBuilder)
                .setCardAcceptorNameAddress(this.config.get(OUR_ADDRESS_KEY));//43
            ((FinancialMessage) this.messageBuilder)
                .setTransactionCurrency(this.config.get(CURRENCY_KEY));//49
            this.messageBuilder.setNetwork(this.config.get(NETWORK_ID_KEY));//61
            ((FinancialMessage) this.messageBuilder).setFromAccount(fromAccount);//102
        }
        catch (ISOException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setProcessingCode(String code) {
        this.processingCode = code;
    }


    @Override
    protected void setToAcount(String toAccount) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setCardAcceptorConstant(String constant) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setMerchantCategory(String code) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setPOSEntryMode(String posEntryMode) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setPOSCondition(String code) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setReservedPrivate(String code) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setLocalTransactionFeeAmount(String code) {
      this.localTransactionFeeAmount = code;
    }
}
