package com.obulex.jpos.service;

import java.lang.reflect.Field;

import org.jpos.core.Configuration;
import org.jpos.iso.ISOMsg;

import com.obulex.domain.jpos.interfaces.Message;
import com.obulex.jpos.interfaces.MessengerPlan;

public abstract class Messenger implements MessengerPlan{

    protected Message messageBuilder;
    protected Configuration config;
    protected long traceNumber;

    protected static final String TIMEZONE_KEY ="default-timezone";
    protected static final String CURRENCY_KEY ="default-currency";
    protected static final String OUR_ID_KEY="our-id";
    protected static final String NETWORK_ID_KEY="network-id";
    protected static final String OUR_ADDRESS_KEY="our-address";
    protected static final String TERMINAL_ID_KEY="terminal-id";

    public Messenger(Message messageBuilder, Configuration config, long traceNumber)
    {
       this.messageBuilder = messageBuilder;
       this.config = config;
       this.traceNumber = traceNumber;
    }

    public ISOMsg getIsoMessage() {
        return this.messageBuilder.getIsoMessage();
    }

    protected void checkForNull() throws IllegalArgumentException, IllegalAccessException
    {
        for (Field f : this.getClass().getFields()) {
              f.setAccessible(true);
              if (f.get(this) == null) {
                  throw new NullPointerException(f.getName() + " is null");
              }
            }
    }

    public abstract void buildISOMessage();
}
