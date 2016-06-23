package gov.ohio.jfs.fn.cpi;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CPIMain {
	public static void main(String[] args) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/knight.xml");
	}
}
