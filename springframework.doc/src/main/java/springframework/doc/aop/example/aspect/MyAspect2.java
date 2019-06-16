package springframework.doc.aop.example.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class MyAspect2 {

	@Pointcut("execution(**	 springframework.doc.aop.example.aspect.target.impl.MyTargetImpl.perform(..))")
	public void pointCut() {

	}

}
