package com.obulex.jpos.service;

import java.util.Date;
import java.util.TimeZone;

import org.jpos.iso.ISOCurrency;
import org.jpos.iso.ISODate;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.q2.QBeanSupport;
import org.jpos.q2.iso.QMUX;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.space.SpaceUtil;
import org.jpos.util.NameRegistrar;

public class OriemsFCubeItManager extends QBeanSupport{
	@SuppressWarnings("unchecked")
	private Space<String, ?> sp = SpaceFactory.getSpace();
	private Space<?, ?> psp = SpaceFactory.getSpace("jdbm:OriemsChaseTxnSpaceClient");
	protected QMUX mux = null;
	private long echoInterval = 0;
	private long fcubeitInterval = 0;
	protected String readyKey = "";
	private Thread fcubeitThread;
	private static final String TRACE = "OPTS_TRACE";
	private static final String FCUBEIT = "OPTS_FCUBEIT.";
	private static final String ECHO = "OPTS_ECHO.";

	@Override
	 protected void startService() throws Exception {
		 echoInterval = Long.parseLong(cfg.get("echo-interval"));
		 log.info("startService : echo-interval : " + echoInterval);
		 fcubeitInterval = Long.parseLong(cfg.get("fcubeit-interval"));
		 log.info("startService : fcubeit-interval : " + fcubeitInterval);
		 mux = (QMUX) NameRegistrar.getIfExists("mux." + cfg.get("mux"));
		 readyKey = cfg.get("channel-ready");
		 log.info("startService : readyKey : " + readyKey);
		 fcubeitThread = new Thread(new FCubeIt());
		 fcubeitThread.start();
	}

	private void doBalanceInquiry(String fromAccount) throws ISOException {
		readyKey = cfg.get("channel-ready");
		 log.info("doBalanceInquiry : readyKey : " + readyKey);
		 ISOMsg resp = mux.request(doBalanceInquiryMessage(fromAccount), cfg.getLong("timeout"));
		 if (resp != null) {
			 NameRegistrar.register(ECHO + readyKey, false);
		 } else {
			 NameRegistrar.register(ECHO + readyKey, true);
		 }
	}

	private ISOMsg doBalanceInquiryMessage(String fromAccount) {
		 ISOMsg m = new ISOMsg ();
		 Date date = new Date();
		 long traceNumber = SpaceUtil.nextLong(psp, TRACE) % 100000;
		 try {
			 m.setMTI("0200");
			 m.set(2,ISOUtil.padright(cfg.get("our-id"),16, '9'));//Primary account number
			 m.set(3, ISOUtil.padright("31",6, '0'));
			 m.set(4,ISOCurrency.convertToIsoMsg(0,
					 ISOCurrency.getIsoCodeFromAlphaCode(cfg.get("default-currency"))));
			 m.set(7, ISODate.getDateTime(date));
			 m.set(11, ISOUtil.zeropad (Long.toString (traceNumber), 6)); // STAN
			 m.set(12,ISODate.getTime(date,
					 TimeZone.getTimeZone("Africa/Nairobi")));
			 m.set(13,ISODate.getDate(date,
					 TimeZone.getTimeZone("Africa/Nairobi")));
			 m.set(28, ISOUtil.padright("C",9, '0'));
			 m.set(32,cfg.get("our-id"));
			 m.set(37, ISOUtil.padleft(Long.toString (traceNumber), 12, '0')); //RRN ours
			 m.set(41,cfg.get("terminal-id"));
			 m.set(43,cfg.get("our-address"));
			 m.set(49,ISOCurrency.getIsoCodeFromAlphaCode(cfg.get("default-currency")));
			 m.set(61, cfg.get("network-id"));
			 m.set(102,fromAccount);
		 } catch (ISOException e) {
			log.warn(e.fillInStackTrace());
		 }
		 m.dump(System.out, "Balance Inquiry: " + readyKey);
		 return m;
	}

	private ISOMsg doFundsTransferMessage(String fromAccount, String toAccount,
			Double amount, Double billingAmount) {
		 ISOMsg m = new ISOMsg ();
		 long traceNumber = SpaceUtil.nextLong(psp, TRACE) % 100000;
		 Date date = new Date();
		 try {
			 m.setMTI("0200");
			 m.set(2,ISOUtil.padright(cfg.get("our-id"),16, '9'));
			 m.set(3, ISOUtil.padright("42",6, '0'));
			 m.set(4, ISOCurrency.convertToIsoMsg(amount,
					 ISOCurrency.getIsoCodeFromAlphaCode(cfg.get("default-currency"))));
			 m.set(7, ISODate.getDateTime(date));
			 m.set(11, ISOUtil.zeropad (Long.toString (traceNumber), 6));
			 m.set(12,ISODate.getTime(date,
					 TimeZone.getTimeZone("Africa/Nairobi")));
			 m.set(13,ISODate.getDate(date,
					 TimeZone.getTimeZone("Africa/Nairobi")));
			 m.set(18,"6010");
			 m.set(22,"901");
			 m.set(25,"00");
			 m.set(26,"12");
			 m.set(28, ISOUtil.padright("C",9, '0'));
			 m.set(32,cfg.get("our-id"));
			 m.set(33,cfg.get("our-id"));
			 m.set(37,ISOUtil.padleft(Long.toString (traceNumber), 12, '0'));
			 m.set(41,cfg.get("terminal-id"));
			 m.set(42,cfg.get("our-id") + "000000006");
			 m.set(43,cfg.get("our-address"));
			 m.set(49,ISOCurrency.getIsoCodeFromAlphaCode(cfg.get("default-currency")));
			 m.set(60, "27          ");
			 m.set(61, cfg.get("network-id"));
			 m.set(102,fromAccount);
			 m.set(103,toAccount);
		 } catch (ISOException e) {
			log.warn(e.fillInStackTrace());
		 }
		 m.dump(System.out, "Funds Transfer: " + readyKey);
		 return m;
	}

	private void doFCubeIt() throws ISOException {
		 readyKey = cfg.get("channel-ready");
		 log.info("doFCubeIt : readyKey : " + readyKey);
		 ISOMsg resp = mux.request(doFCubeItISOMsg(), cfg.getLong("timeout"));
		 if (resp != null) {
			 NameRegistrar.register(FCUBEIT + readyKey, true);
		 } else {
			 NameRegistrar.register(ECHO + readyKey, false);
		 }
	 }

	private void doFundsTransfer(String fromAccount, String toAccount, double amount,
			double billingAmount) {
		readyKey = cfg.get("channel-ready");
		log.info("doFundsTransfer : readyKey : " + readyKey);
		ISOMsg resp = null;
		try {
			resp = mux.request (doFundsTransferMessage(fromAccount, toAccount,
					 amount, billingAmount), cfg.getLong("timeout"));
			 if (resp != null) {
				 NameRegistrar.register(ECHO + readyKey, false);
			 } else {
				 NameRegistrar.register(ECHO + readyKey, true);
			 }
		 } catch (ISOException e) {
			log.warn(e);
		 }
	}

	private ISOMsg doFCubeItISOMsg() {
		 long traceNumber = SpaceUtil.nextLong(psp, TRACE) % 100000;
		 ISOMsg m = new ISOMsg ();
		 Date date = new Date();
		 try {
			 m.setMTI("0800");
			 m.set (7, ISODate.getDateTime(date));
			 m.set (11, ISOUtil.zeropad (Long.toString (traceNumber), 6));
			 m.set(61, cfg.get("network-id"));
			 m.set(70, ISOUtil.zeropad(1, 3));
		 } catch (ISOException e) {
			 e.printStackTrace();
		 }
		 m.dump(System.out, "Echo message: " + readyKey);
		 return m;
	}

	public class FCubeIt implements Runnable {
		public FCubeIt () {
			super ();
		}
		public void run () {
			readyKey = cfg.get("channel-ready");
			log.info("FCubeIt : readyKey : " + readyKey);
			int count = 1;
			while (running ())
			{
				if(count > 5)
				{
					stop();
				}
				Boolean registered = null;
				Boolean echo = null;
				Object sessionId = sp.rd (readyKey, cfg.getLong("timeout"));
				if (sessionId == null)
				 {
					log.info ("Channel " + readyKey + " not ready");
					 NameRegistrar.register(FCUBEIT + readyKey, false);
					 continue;
				 }
				 else
				 {
					 log.info ("Channel " + readyKey + " ready");
					 registered =  (Boolean) NameRegistrar
							 .getIfExists(FCUBEIT + readyKey);
					 echo = (Boolean) NameRegistrar
							 .getIfExists(ECHO + readyKey);
				 }

				 log.info("Registration of Channel : "  + registered);
				 log.info("Registration of echo Channel : "  + echo);
				 try
				 {
					 if(registered == null || echo.equals(true))
					 {
						 doFCubeIt();
					 }
					 registered =  (Boolean) NameRegistrar
							 .getIfExists(FCUBEIT + readyKey);
					 if(registered.equals(true))
					 {

						 log.info("Balance Inquiry for test trust account");
						 doBalanceInquiry(cfg.get("test-trust-account"));
						 log.info("Balance Inquiry for test collection account");
						 doBalanceInquiry(cfg.get("test-collection-account"));
						 doFundsTransfer(cfg.get("test-trust-account"),
								 cfg.get("test-collection-account"), 1000, 0);
						 log.info("Balance Inquiry for test trust account");
						 doBalanceInquiry(cfg.get("test-trust-account"));
						 log.info("Balance Inquiry for test collection account");
						 doBalanceInquiry(cfg.get("test-collection-account"));
					 }
				 }
				 catch (Throwable t)
				 {
					t.printStackTrace();
				 }
				 ISOUtil.sleep(echoInterval);
				 count++;
			}
		}
	}
}
