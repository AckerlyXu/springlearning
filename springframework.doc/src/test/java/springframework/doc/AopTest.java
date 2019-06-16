package springframework.doc;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import springframework.doc.aop.AppConfig;
import springframework.doc.aop.example.aspect.target.MyTarget;

public class AopTest {
	/**
	 * ����aopע�ⷽʽ
	 */
	// @Test
	public void test() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		MyTarget bean = context.getBean(MyTarget.class);
		System.out.println(bean.perform(" arg1"));

	}

	/**
	 * ����aopע��+xml��ʽ
	 */
//	@Test
	public void test2() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop.xml");
		MyTarget bean = context.getBean(MyTarget.class);
		System.out.println(bean.perform(" arg1"));

	}

	/**
	 * ����xml��ʽ
	 */
	@Test
	public void test3() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("aop2.xml");
		MyTarget bean = context.getBean(MyTarget.class);
		System.out.println(bean.perform(" arg1"));

	}

}
