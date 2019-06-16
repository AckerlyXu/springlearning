package springframework.doc.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import springframework.doc.aop.example.aspect.MyAspect;
import springframework.doc.aop.example.aspect.target.MyTarget;
import springframework.doc.aop.example.aspect.target.impl.MyTargetImpl;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
	@Bean
	public MyAspect myAspect() {
		return new MyAspect();
	}

	@Bean
	public MyTarget myTarget() {
		return new MyTargetImpl();
	}
}
