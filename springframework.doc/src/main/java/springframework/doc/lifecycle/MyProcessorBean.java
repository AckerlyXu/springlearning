package springframework.doc.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
/**
 * 
 * @author xuqiang
 * 实现了BeanPostProcessor接口的bean，它的回调方法对所有的bean都凑效
 */
public class MyProcessorBean implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		   System.out.println(bean);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		// TODO Auto-generated method stub
		  System.out.println(bean);
		return bean;
	}

}
