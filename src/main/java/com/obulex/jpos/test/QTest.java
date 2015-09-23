package com.obulex.jpos.test;

import org.jpos.iso.ISOUtil;
import org.jpos.q2.Q2;
import org.jpos.q2.QBeanSupport;
import org.jpos.util.Log;

public class QTest extends QBeanSupport implements Runnable{

	volatile int state;
	long tickInterval = 1000;
	Log log;
	boolean debug;
	
	public QTest () {
		super();
		state = -1;
		log = Log.getLog(Q2.LOGGER_NAME, "qtest");
		log.info ("constructor");
	}

	@Override
	public void run() {
		for(int tickCount = 0; running(); tickCount++)
		{
			log.info("tick " + tickCount);
			ISOUtil.sleep(cfg.getLong("tickInterval",5000));
		}
		
	}
	
	@Override
	protected void startService()
	{
		new Thread(this).start();
	}
}
