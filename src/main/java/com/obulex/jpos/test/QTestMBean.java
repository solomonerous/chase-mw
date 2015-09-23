package com.obulex.jpos.test;

import org.jpos.q2.QBean;

public interface QTestMBean extends QBean {
	public void setTickInterval(long tickinterval);
	public long getTickInterval();
}
