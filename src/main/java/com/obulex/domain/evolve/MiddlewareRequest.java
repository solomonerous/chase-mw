package com.obulex.domain.evolve;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.obulex.domain.interfaces.OriemsEntity;

@Entity
@Table(name="mw_request")
public class MiddlewareRequest implements OriemsEntity<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(MiddlewareRequest.class);

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name="target_account")
    private String targetAccount;
    
    @Column(name="source_account")
    private String sourceAccount;

    private Double amount;

    private String status;

    @Column(name="transaction_type")
    private String transactionType;

    @Column(name="date_created")
    private Calendar dateCreated;

    @Column(name="other_details")
    private String otherDetails;

    @Override
    public Integer getId() {
        return id;
    }

    /**
     * @return the sourceAccount
     */
    public String getSourceAccount() {
        return sourceAccount;
    }

    /**
     * @param sourceAccount the sourceAccount to set
     */
    public void setSourceAccount(String sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    /**
     * @return the targetAccount
     */
    public String getTargetAccount() {
        return targetAccount;
    }

    /**
     * @param targetAccount the targetAccount to set
     */
    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the transactionType
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * @param transactionType the transactionType to set
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * @return the dateCreated
     */
    public Calendar getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((amount == null) ? 0 : amount.hashCode());
        result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
        result = prime * result + ((sourceAccount == null) ? 0 : sourceAccount.hashCode());
        result = prime * result + ((targetAccount == null) ? 0 : targetAccount.hashCode());
        result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (this == obj)
            return true;
        if (obj == null)
        {
            logger.debug("Object is null");

            return false;
        }
        if (getClass() != obj.getClass())
        {
            logger.debug("Class is not MiddleWareRequest.class");

            return false;
        }
        MiddlewareRequest other = (MiddlewareRequest) obj;
        if (amount == null) {
            logger.debug("this amount is null");

            if (other.amount != null)
                return false;
        } else if (!amount.equals(other.amount))
        {
            logger.debug("amounts are not equal");
            return false;
        }
        if (dateCreated == null) {
            if (other.dateCreated != null)
                return false;
        } else if (!sdf.format(dateCreated.getTime())
                .equals(sdf.format(other.dateCreated.getTime())))
        {
            logger.debug("dates are not equal");
            return false;
        }
        if (sourceAccount == null) {
            if (other.sourceAccount != null)
                return false;
        } else if (!sourceAccount.equals(other.sourceAccount))
        {
            logger.debug("sourceAccounts are not equal");
            return false;
        }
        if (targetAccount == null) {
            if (other.targetAccount != null)
                return false;
        } else if (!targetAccount.equals(other.targetAccount))
        {
            logger.debug("targetAccounts are not equal");
            return false;
        }
        if (transactionType == null) {
            return other.transactionType == null;
        } else if (!transactionType.equals(other.transactionType))
        {
            logger.debug("transactionTypes are not equal");
            return false;
        }
        return true;
    }

    /**
     * @return the otherDetails
     */
    public String getOtherDetails() {
        return otherDetails;
    }

    /**
     * @param otherDetails the otherDetails to set
     */
    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }


}
