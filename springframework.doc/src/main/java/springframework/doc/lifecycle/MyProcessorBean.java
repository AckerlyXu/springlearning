package springframework.doc.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
/**
 * 
 * @author xuqiang
 * ʵ����BeanPostProcessor�ӿڵ�bean�����Ļص����������е�bean����Ч
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
