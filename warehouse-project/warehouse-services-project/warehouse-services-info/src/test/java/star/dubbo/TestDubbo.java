package star.dubbo;

import org.junit.Test;

import star.fw.ServiceLocator;


public class TestDubbo {

	@Test
	public void test() throws InterruptedException {
		// TODO Auto-generated method stub
		
		System.setProperty("spring.configfile", "spring-modules.xml");
		ServiceLocator.start();
		
		
		System.out.println("start...");
		while (true) {
			Thread.sleep(100000);
		}
	}

}
