package springframework.doc;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import springframework.doc.service.impl.PetStoreServiceImpl;

public class App {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("weave.xml", App.class);
		PetStoreServiceImpl bean = applicationContext.getBean(PetStoreServiceImpl.class);
		bean.service();
		System.out.println("hello ");
	}
}
