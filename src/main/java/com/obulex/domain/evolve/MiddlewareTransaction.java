package com.obulex.domain.evolve;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.obulex.domain.interfaces.OriemsEntity;

@Entity
@Table(name="mw_transaction")
public class MiddlewareTransaction implements OriemsEntity<Integer> {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @OneToOne()
    @JoinColumn(name="mw_request_id")
    private MiddlewareRequest request;

    @Column(name="bank_trace_number")
    private String bankTraceNumber;

    @Column(name="message_status_code")
    private String messageStatusCode;

    @Column(name="oriems_trace_number")
    private String oriemsTraceNumber;

    private boolean echo;

    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the request
     */
    public MiddlewareRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(MiddlewareRequest request) {
        this.request = request;
    }

    /**
     * @return the bankTraceNumber
     */
    public String getBankTraceNumber() {
        return bankTraceNumber;
    }

    /**
     * @param bankTraceNumber the bankTraceNumber to set
     */
    public void setBankTraceNumber(String bankTraceNumber) {
        this.bankTraceNumber = bankTraceNumber;
    }

    /**
     * @return the messageStatusCode
     */
    public String getMessageStatusCode() {
        return messageStatusCode;
    }

    /**
     * @param messageStatusCode the messageStatusCode to set
     */
    public void setMessageStatusCode(String messageStatusCode) {
        this.messageStatusCode = messageStatusCode;
    }

    /**
     * @return the oriemsTraceNumber
     */
    public String getOriemsTraceNumber() {
        return oriemsTraceNumber;
    }

    /**
     * @param oriemsTraceNumber the oriemsTraceNumber to set
     */
    public void setOriemsTraceNumber(String oriemsTraceNumber) {
        this.oriemsTraceNumber = oriemsTraceNumber;
    }

    /**
     * @return the echo
     */
    public boolean isEcho() {
        return echo;
    }

    /**
     * @param echo the echo to set
     */
    public void setEcho(boolean echo) {
        this.echo = echo;
    }


}
