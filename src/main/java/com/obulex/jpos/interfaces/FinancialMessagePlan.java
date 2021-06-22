package com.obulex.jpos.interfaces;

import org.jpos.iso.ISOException;

public interface FinancialMessagePlan extends MessagePlan {
    /**
     * @param ourId
     * @throws ISOException
     */
    void setPAN(String ourId) throws ISOException;
    /**
     * @param code
     * @throws ISOException
     */
    void setProcessingCode(String code) throws ISOException;
    /**
     * @param timezone
     * @throws ISOException
     */
    void setLocalTransactionTime(String timezone) throws ISOException;
    /**
     *
     * @param timezone
     * @throws ISOException
     */
    void setLocalTransactionDate(String timezone) throws ISOException;
    /**
     *
     * @param ourId
     * @throws ISOException
     */
    void setAquirerInstitution(String ourId) throws ISOException;
    /**
     *
     * @param terminalId
     * @throws ISOException
     */
    void setCardAcceptorTerminal(String terminalId) throws ISOException;
    /**
     *
     * @param ourAddress
     * @throws ISOException
     */
    void setCardAcceptorNameAddress(String ourAddress) throws ISOException;
    /**
     *
     * @param currency
     * @throws ISOException
     */
    void setTransactionCurrency(String currency) throws ISOException;

    /**
     *
     * @param traceNumber
     * @throws ISOException
     */
    void setRetrievalReference(long traceNumber) throws ISOException;
    /**
     *
     * @param fromAccount
     * @throws ISOException
     */
    void setFromAccount(String fromAccount) throws ISOException;

    /**
     *
     * @param amount
     * @throws ISOException
     */
    void setAmount(Double amount, String defaultCurrency) throws ISOException;
}
