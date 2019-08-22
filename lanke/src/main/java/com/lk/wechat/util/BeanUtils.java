package com.lk.wechat.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class BeanUtils {

	private static ApplicationContext applicationContext;

	private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	public static void setApplicationContext(ApplicationContext applicationContext) {
		synchronized (BeanUtils.class) {
			logger.debug("setApplicationContext, notifyAll");
			BeanUtils.applicationContext = applicationContext;
			BeanUtils.class.notifyAll();
		}
	}

	public static ApplicationContext getApplicationContext() {
		synchronized (BeanUtils.class) {
			while (applicationContext == null) {
				try {
					logger.debug("getApplicationContext, wait...");
					BeanUtils.class.wait(60000);
					if (applicationContext == null) {
						logger.warn("Have been waiting for ApplicationContext to be set for 1 minute", new Exception());
					}
				} catch (InterruptedException ex) {
					logger.debug("getApplicationContext, wait interrupted");
				}
			}
			return applicationContext;
		}
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return getApplicationContext().getBean(name, requiredType);
	}

}
