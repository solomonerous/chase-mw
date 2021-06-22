package com.obulex.jpos.service;

import java.util.ArrayList;
import java.util.List;

import org.jpos.core.Configuration;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.iso.QMUX;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.space.SpaceUtil;
import org.jpos.transaction.Context;
import org.jpos.util.Log;
import org.jpos.util.NameRegistrar;

import com.obulex.domain.evolve.MiddlewareRequest;
import com.obulex.domain.evolve.MiddlewareTransaction;
import com.obulex.domain.evolve.Status;
import com.obulex.domain.evolve.TransactionType;
import com.obulex.domain.jpos.custom.interfaces.ChaseFinancialMessage;
import com.obulex.domain.jpos.custom.interfaces.ChaseFundsTransferMessage;
import com.obulex.domain.jpos.interfaces.MessageTypes;
import com.obulex.domain.jpos.interfaces.NetworkMessage;
import com.obulex.jpos.custom.service.ChaseBalanceInquiryMessenger;
import com.obulex.jpos.custom.service.ChaseFundsTransferMessenger;


public class Processor implements Runnable{

    private ProcessManager parent;
    private Log log;
    private Configuration cfg;
    protected QMUX mux = null;
    private long echoInterval = 0;
    private long processorInterval = 0;
    protected String readyKey = "";
    private static final String TRACE = "OPTS_TRACE";
    private static final String PROCESSOR = "OPTS_PROCESSOR.";
    private static final String ECHO = "OPTS_ECHO.";
    @SuppressWarnings("unchecked")
    private Space<String, ?> sp = SpaceFactory.getSpace();
    private Space<?, ?> psp = SpaceFactory.getSpace("jdbm:OriemsJPOSTxnSpaceClient");

    public Processor(ProcessManager pm) {

        this.setParent(pm);
        this.log = this.parent.getLog();
        this.cfg = this.parent.getConfiguration();
        echoInterval = Long.parseLong(cfg.get("echo-interval"));
        log.info("startService : echo-interval : " + echoInterval);
        processorInterval = Long.parseLong(cfg.get("processor-interval"));
        log.info("startService : processor-interval : " + processorInterval);
        mux = (QMUX) NameRegistrar.getIfExists("mux." + cfg.get("mux"));
        readyKey = cfg.get("channel-ready");
        log.info("startService : readyKey : " + readyKey);
    }

    /**
    *
    * @param request
    */
   protected void processRequest(MiddlewareRequest request) {
	   log.info("Request : ------------------------------------------- : ");
       long traceNumber = SpaceUtil.nextLong(psp, TRACE) % 100000;
       readyKey = cfg.get("channel-ready");
       log.info(request.getTransactionType().toString() +" : readyKey : " + readyKey);
       ISOMsg response = null;
       try {
           long start = System.currentTimeMillis();
           response = mux.request (this.doMessage(request,traceNumber),
                   cfg.getLong("timeout"));
           if (response != null) {
                NameRegistrar.register(ECHO + readyKey, false);
                if(!(boolean) NameRegistrar.getIfExists(PROCESSOR + readyKey))
                    NameRegistrar.register(PROCESSOR + readyKey, true);
                long duration = System.currentTimeMillis() - start;
                log.info("Response time (ms):" + duration);
                processReponse(response,request);
           } else {
                NameRegistrar.register(ECHO + readyKey, true);
           }
        } catch (ISOException e) {
           log.warn(e);
        } catch (NullPointerException e) {
        	log.warn(e);
		}
   }

   /**
    *
    * @param response
    * @param request
    */
   private void processReponse(ISOMsg response, MiddlewareRequest request) {
       MiddlewareTransaction middlewareTransaction = new MiddlewareTransaction();
       middlewareTransaction.setRequest(request);
       try {
           String messageStatusCode = (String) response.getValue(39);
           log.info("response : " + messageStatusCode);
           middlewareTransaction.setMessageStatusCode(messageStatusCode);
           middlewareTransaction.setOriemsTraceNumber(response.getString(11));
           middlewareTransaction.setBankTraceNumber(response.getString(54));
           middlewareTransaction.setEcho((request.getTransactionType()
                   .equalsIgnoreCase(
                           TransactionType.NETWORK.toString())) ? true : false);
           //TODO check the logic here
           if(messageStatusCode.equals("00"))
           {
               request.setStatus(Status.FAILED.toString());
           }
           else
           {
               request.setStatus(Status.SUCCESS.toString());
           }
       } catch (ISOException e) {
           log.warn(e);
       }
   }

   /**
    *
    * @param request
    * @param traceNumber
    * @return
    */
   private ISOMsg doMessage(MiddlewareRequest request, long traceNumber) {
       ISOMsg m = null;
       switch (request.getTransactionType()) {
       case "BALANCE_INQUIRY":
           m = this.doBalanceInquiryMessage(request.getSourceAccount(), traceNumber);
           break;
       case "FUNDS_TRANSFER":
           m = this.doFundsTransferMessage(request.getSourceAccount(),
                   request.getTargetAccount(), request.getAmount(), traceNumber);
           break;
       case "NETWORK":
           m = this.doEcho(traceNumber);
           break;
       default:
           break;
       }
       return m;
   }

   /**
    *
    * @param fromAccount
    * @param toAccount
    * @param amount
    * @param traceNumber
    * @return
    */
   private ISOMsg doFundsTransferMessage(String fromAccount, String toAccount,
           Double amount, long traceNumber) {


        ChaseFundsTransferMessage msg =
                new ChaseFundsTransferMessage(MessageTypes.FinancialCodes.F_0200.toString());
        ChaseFundsTransferMessenger messenger =
                new ChaseFundsTransferMessenger(msg, cfg, traceNumber);

        messenger.setFromAccount(fromAccount);
        messenger.setAmount(amount);
        messenger.setToAcount(toAccount);
        messenger.setProcessingCode("42");

        messenger.buildISOMessage();

        ISOMsg m = messenger.getIsoMessage();
        m.dump(System.out, "Funds Transfer: " + readyKey);
        return m;
   }

   private ISOMsg doEcho(long traceNumber) {

       NetworkMessage msg =
               new NetworkMessage(MessageTypes.NetworkCodes.N_0800.toString());
       NetworkMessenger messenger = new NetworkMessenger(msg, cfg, traceNumber);
       messenger.buildISOMessage();
       ISOMsg m = messenger.getIsoMessage();
       m.dump(System.out, "Echo message: " + readyKey);
       return m;
   }

   private ISOMsg doBalanceInquiryMessage(String fromAccount, long traceNumber) {

       ChaseFinancialMessage msg = new ChaseFinancialMessage(
               MessageTypes.FinancialCodes.F_0200.toString());

       ChaseBalanceInquiryMessenger messenger =
               new ChaseBalanceInquiryMessenger(msg, cfg, traceNumber);

       messenger.setFromAccount(fromAccount);
       messenger.setProcessingCode("31");
       messenger.buildISOMessage();

       ISOMsg m = messenger.getIsoMessage();

       m.dump(System.out, "Balance Inquiry: " + readyKey);
       return m;
  }


    @Override
    public void run() {
        Boolean registered = null;
        Boolean echo = null;
        while (this.getParent().running())
        {
            /* Do nothing till the map is injected
            if (null != this.getParent().getMap()) {
                 Space sp = SpaceFactory.getSpace("tspace:default");
                 Context ctx = new Context();
                 sp.out("myTxQueue", ctx, 10000);
            }*/

             Object sessionId = sp.rd (readyKey, cfg.getLong("timeout"));
             if (sessionId == null)
             {
                 log.info ("Channel " + readyKey + " not ready");
                 NameRegistrar.register(PROCESSOR + readyKey, false);
                 continue;
             }
             else
             {
                 log.info ("Channel " + readyKey + " ready");
                 //registered =  (Boolean) NameRegistrar
                      //.getIfExists(PROCESSOR + readyKey);
                 echo = (Boolean) NameRegistrar
                      .getIfExists(ECHO + readyKey);
             }

             if(registered == null || echo.equals(true))
             {
                 MiddlewareRequest request = new MiddlewareRequest();
                 request.setTransactionType(TransactionType.NETWORK.toString());
                 this.processRequest(request);
                 ISOUtil.sleep(echoInterval);
             }

             log.info("Registration of Channel : "  + registered);
             log.info("Registration of echo Channel : "  + echo);

            List<MiddlewareRequest> requests = new ArrayList<MiddlewareRequest>();

            log.debug("Get middle ware transactions requests");

            log.debug("Request size : " + requests.size());
            try
            {
                for(MiddlewareRequest request: requests)
                {
                    String originalTransactionType = request.getTransactionType();

                    if(registered == null || echo.equals(true))
                    {
                        request.setTransactionType(TransactionType.NETWORK.toString());
                        this.processRequest(request);
                        ISOUtil.sleep(echoInterval);
                    }
                    registered =  (Boolean) NameRegistrar
                            .getIfExists(PROCESSOR + readyKey);

                    request.setTransactionType(originalTransactionType);
                    if(registered.equals(true))
                    {
                        request.setStatus(Status.PROCESSING.toString());
                        // do message
                        this.processRequest(request);
                    }
                }
            }
            catch (Throwable t)
            {
               t.printStackTrace();
            }
            ISOUtil.sleep(processorInterval);
        }
    }

    /**
     * @return the parent
     */
    protected synchronized ProcessManager getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(ProcessManager parent) {
        this.parent = parent;
    }

}
