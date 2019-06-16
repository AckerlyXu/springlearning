package springframework.doc.aop.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;

import springframework.doc.aop.example.aspect.introduce.MyIntroduce;
import springframework.doc.aop.example.aspect.introduce.impl.MyIntroduceImpl;

@Aspect
public class MyAspect {
	// ..代表任意层级的包，能省略到类名包括类名
	// 所以这里去掉*Impl也没有问题
//	@Pointcut(" execution(* springframework..*Impl.*perform(..)) ")

	// 如果说切点表达式只匹配到接口，那么它会寻找这个接口所在包的名为impl的子包
	// 然后寻找类名为 接口名+Impl的类，为这个类生成代理
	// @Pointcut(" execution(* springframework..target.*.*(..)) ")

	// @Pointcut(" within(springframework..*) ")
	// target不允许通配符写法
//	@Pointcut(" target(springframework.doc.aop.example.aspect.target.MyTarget) ")
//	public void myPointCut() {
//
//	}

//@target代表切点所在类要有对应的注解
	// @annotation代表方法上要有对应的注解
//	@Around(value = " myPointCut() && args(arg1,..) && (@target(springframework.doc.aop.example.aspect.MyAnnotation) || @annotation(springframework.doc.aop.example.aspect.MyAnnotation)) ")
//	public Object aspect1(ProceedingJoinPoint pjp, String arg1) {
//		System.out.println("环绕通知执行了");
//		Object result = null;
//		try {
//			result = pjp.proceed(pjp.getArgs());
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return result + "arg1";
//	}

	@Before("execution(**	 springframework.doc.aop.example.aspect.target.impl.MyTargetImpl.perform(..))")
	// 不知道为什么，下面这个试不出来
	// @Before("execution(** ..MyTargetImpl*.perform(*))")
	public void aspect2() {
		System.out.println("before");
	}

	// 切点如果声明了args(arg1)，那么切点对应的方法需要定义相应的参数
	@Pointcut(" target(springframework.doc.aop.example.aspect.target.MyTarget) && args(arg1,..) ")
	public void myPointCut(String arg1) {

	}

	// 如果切点所在方法含有参数，调用的时候也要把参数写上
	// 获得切点或者切点类上的注解MyAnnotation，如果全有，优先获得方法上的
	// 如果没有MyAnnotation类型的注解则不会匹配到
	@Around(value = " myPointCut(arg1)  && (@target(myAnnotation1) || @annotation(myAnnotation1))  && this(parent)", argNames = "arg1,myAnnotation1,parent")
	// argnames与切点中定义的名称相同，然后方法参数名就可以不同
	// 如果方法参数名和切点中定义的名称相同，那么可以省略argnames

	public Object aspect1(ProceedingJoinPoint pjp, String arg1, MyAnnotation myAnnotation, MyIntroduce myIntroduce) {
		System.out.println("环绕通知执行了");
		Object result = null;
		try {
			result = pjp.proceed(pjp.getArgs());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result + "arg1" + " " + myAnnotation.value() + myIntroduce.execute();
	}

	public Object aspect3(ProceedingJoinPoint pjp, String arg1, MyAnnotation myAnnotation) {
		System.out.println("环绕通知执行了");
		Object result = null;
		try {
			result = pjp.proceed(pjp.getArgs());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result + "arg1" + " " + myAnnotation.value();
	}

	// 这个value不支持找到接口以后找实现
	@DeclareParents(value = "springframework..target.impl.*", defaultImpl = MyIntroduceImpl.class)
	public static MyIntroduce introduce;
}
