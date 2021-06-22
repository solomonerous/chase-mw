package com.obulex.jpos.service;

import java.util.Map;

import org.jpos.q2.QBeanSupport;

/**
 *
 * @author Donald O.
 *
 */
public class ProcessManager extends QBeanSupport{

    private Thread processorThread;
    /**
     * The dependency to inject
     */
    private Map<String, String> map;

    @Override
    protected void startService() throws Exception {
        processorThread = new Thread(new Processor(this));
        processorThread.start();
    }

	/**
	 * @return the map
	 */
	public Map<String, String> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

}
