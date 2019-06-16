package springframework.doc.lifecycle;

import javax.annotation.PostConstruct;


import org.springframework.context.SmartLifecycle;

public class LifeCycleBean  implements AutoCloseable, SmartLifecycle{
    private String name;
    private int code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
    public void init(){
    	this.code = 100;
    }
    
	@Override
	public String toString() {
		return "LifeCycleBean [name=" + name + ", code=" + code + "]";
	}
	@Override
	public void close() throws Exception {
		System.out.println("close������������");
		
	}
	//postconstructע��Ҫ��Ч��Ҫ��xml������context:annotation-config
	//��Ҫ��ע�����beanCommonAnnotationBeanPostProcessor
	@PostConstruct
	public void postInit() {
		System.out.println("postInit������������");
	}

	public void destory() {
		System.out.println("destory������������");
		
	}
	@Override
	public void start() {
		// TODO Auto-generated method stub
		 System.out.println("start ������������");
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		System.out.println("stop ������������");
	}
	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getPhase() {
		// TODO Auto-generated method stub
		return -1;
	}
	@Override
	public boolean isAutoStartup() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void stop(Runnable callback) {
		// TODO Auto-generated method stub
		System.out.println("stop callback������������");
		callback.run();
	}
	
}
