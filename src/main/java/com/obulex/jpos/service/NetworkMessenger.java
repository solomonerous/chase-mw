package com.obulex.jpos.service;

import java.util.Date;

import org.jpos.core.Configuration;
import org.jpos.iso.ISOException;

import com.obulex.domain.jpos.interfaces.NetworkMessage;

public class NetworkMessenger extends Messenger{

    public NetworkMessenger(NetworkMessage messageBuilder, Configuration config,
            long traceNumber) {
        super(messageBuilder, config, traceNumber);
    }

    @Override
    public void buildISOMessage() {
        try {
            this.checkForNull();
            this.messageBuilder.setMtiCode();
            this.messageBuilder.setNetwork(this.config.get(NETWORK_ID_KEY));
            this.messageBuilder.setTransactionDatetime(new Date());
            this.messageBuilder.setSTAN(traceNumber);
            ((NetworkMessage)this.messageBuilder).setNetworkManagementInfo();
        } catch (IllegalArgumentException | IllegalAccessException | ISOException e) {
            e.printStackTrace();
        }

    }

}
