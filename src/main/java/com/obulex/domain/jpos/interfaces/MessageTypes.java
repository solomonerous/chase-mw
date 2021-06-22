package com.obulex.domain.jpos.interfaces;

import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

public enum MessageTypes {
    FINANCIAL (FinancialCodes.F_0200,FinancialCodes.F_0201),
    NETWORK (NetworkCodes.N_0800,NetworkCodes.N_0801);

    private MessageTypes(MessageTypeCodes ... pList){
        for (MessageTypeCodes group : pList){
            group.addMtiCode(this);
        }
    }

    private interface MessageTypeCodes {
        EnumSet<MessageTypes> getMtiCodes();
        void addMtiCode(MessageTypes code);
    }

    public enum FinancialCodes implements MessageTypeCodes{
        F_0200,F_0201,F_0210,F_0220,F_0221,F_0230;

        private List<MessageTypes> mtiCodes = new LinkedList<MessageTypes>();

        @Override
        public EnumSet<MessageTypes> getMtiCodes() {
            return EnumSet.copyOf(mtiCodes);
        }

        @Override
        public void addMtiCode(MessageTypes code) {
            mtiCodes.add(code);
        }


        static { // forcing initiation of dependent enum
            try {
                Class.forName(MessageTypes.class.getName());
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("Class MessageTypes not found", ex);
            }
        }

        @Override
        public String toString() {
            String s = super.toString();
            return s.split("_")[1];
        }
    }

    public enum NetworkCodes implements MessageTypeCodes{
        N_0800,N_0801,N_0810;

        private List<MessageTypes> mtiCodes = new LinkedList<MessageTypes>();

        @Override
        public EnumSet<MessageTypes> getMtiCodes() {
            return EnumSet.copyOf(mtiCodes);
        }

        @Override
        public void addMtiCode(MessageTypes code) {
            mtiCodes.add(code);
        }
        static { // forcing initiation of dependent enum
            try {
                Class.forName(MessageTypes.class.getName());
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("Class MessageTypes not found", ex);
            }
        }

        @Override
        public String toString() {
            String s = super.toString();
            return s.split("_")[1];
        }
    }



}
