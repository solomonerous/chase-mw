package com.obulex.jpos.custom.service;

import java.util.Date;

import org.jpos.core.Configuration;
import org.jpos.iso.ISOException;

import com.obulex.domain.jpos.custom.interfaces.ChaseFundsTransferMessage;
import com.obulex.jpos.service.FundsTransferMessenger;

public class ChaseFundsTransferMessenger extends FundsTransferMessenger {


    private static final String CUSTOM_FIELD_26 = "custom-field-26";
    private static final String RESERVED_PRIVATE = "reserved-private";
    private String processingCode;
    private String reservedPrivate;

    public ChaseFundsTransferMessenger(ChaseFundsTransferMessage messageBuilder,
            Configuration config, long traceNumber) {
        super(messageBuilder, config, traceNumber);
        this.setCardAcceptorConstant(this.config.get(CARD_ACCEPTOR_CODE));
        this.setMerchantCategory(this.config.get(MERCHANT_CATEGORY_CODE));
        this.setPOSCondition(this.config.get(POS_GOOD_CONDITION));
        this.setPOSEntryMode(this.config.get(POS_ENTRY_MODE));
        this.customField(26, this.config.get(CUSTOM_FIELD_26));
        this.setReservedPrivate(this.config.get(RESERVED_PRIVATE));
        this.setLocalTransactionFeeAmount(this.config.get(LOCAL_TRANSACTION_FEE_CODE));
    }

    @Override
    public void buildISOMessage()
    {
        try {
            this.checkForNull();

            this.messageBuilder.setMtiCode();
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setPAN(this.config.get(OUR_ID_KEY));//2
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setProcessingCode(this.processingCode);//3
            ((ChaseFundsTransferMessage) this.messageBuilder).setAmount(amount,
                    this.config.get(CURRENCY_KEY));//4
            this.messageBuilder.setTransactionDatetime(new Date());//7
            ((ChaseFundsTransferMessage) this.messageBuilder).setSTAN(this.traceNumber);//11
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setLocalTransactionTime(this.config.get(TIMEZONE_KEY));//12
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setLocalTransactionDate(this.config.get(TIMEZONE_KEY));//13
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setMerchantCategory(this.merchantCategory);//18
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setPOSEntryMode(this.pOSEntryMode);//22
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setPOSCondition(this.pOSCondition);//25
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setLocalTransactionFeeAmount(this.localTransactionFeeAmount);//28
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setAquirerInstitution(this.config.get(OUR_ID_KEY));//32
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setForwardingInstitution(this.config.get(OUR_ID_KEY));//33
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setRetrievalReference(this.traceNumber);//37
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setCardAcceptorTerminal(this.config.get(TERMINAL_ID_KEY));//41
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setCardAcceptor(this.config.get(OUR_ID_KEY),this.cardAcceptorConstant);//42
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setCardAcceptorNameAddress(this.config.get(OUR_ADDRESS_KEY));//43
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setTransactionCurrency(this.config.get(CURRENCY_KEY));//49
            ((ChaseFundsTransferMessage) this.messageBuilder)
                .setReservedPrivate(this.reservedPrivate);//60
            this.messageBuilder.setNetwork(this.config.get(NETWORK_ID_KEY));//61
            ((ChaseFundsTransferMessage) this.messageBuilder).setFromAccount(fromAccount);//102
            ((ChaseFundsTransferMessage) this.messageBuilder).setToAcount(toAccount);//103

            for(Integer field : customFields.keySet())
            {
                this.messageBuilder.setField(field,customFields.get(field));
            }
        }
        catch (ISOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setReservedPrivate(String code) {
        this.reservedPrivate = code;
    }

    @Override
    public void setProcessingCode(String code) {
        this.processingCode = code;
    }

    @Override
    public void customField(Integer field, String message) {
        this.customFields.put(field, message);
    }

    @Override
    protected void setLocalTransactionFeeAmount(String code) {
        this.localTransactionFeeAmount = code;
    }


}
