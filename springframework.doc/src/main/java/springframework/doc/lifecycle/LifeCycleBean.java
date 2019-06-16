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
		System.out.println("close方法被调用了");
		
	}
	//postconstruct注解要生效需要在xml中配置context:annotation-config
	//主要是注册这个beanCommonAnnotationBeanPostProcessor
	@PostConstruct
	public void postInit() {
		System.out.println("postInit方法被调用了");
	}

	public void destory() {
		System.out.println("destory方法被调用了");
		
	}
	@Override
	public void start() {
		// TODO Auto-generated method stub
		 System.out.println("start 方法被调用了");
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		System.out.println("stop 方法被调用了");
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
		System.out.println("stop callback方法被调用了");
		callback.run();
	}
	
}
