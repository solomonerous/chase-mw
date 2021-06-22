package com.obulex.jpos.interfaces;

public interface FinancialMessengerPlan {
    /**
     *
     * @param fromAccount
     */
    void setFromAccount(String fromAccount);

    /**
     *
     * @param amount
     */
    void setAmount(Double amount);
}
