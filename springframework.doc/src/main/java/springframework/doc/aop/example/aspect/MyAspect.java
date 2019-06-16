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
	// ..��������㼶�İ�����ʡ�Ե�������������
	// ��������ȥ��*ImplҲû������
//	@Pointcut(" execution(* springframework..*Impl.*perform(..)) ")

	// ���˵�е���ʽֻƥ�䵽�ӿڣ���ô����Ѱ������ӿ����ڰ�����Ϊimpl���Ӱ�
	// Ȼ��Ѱ������Ϊ �ӿ���+Impl���࣬Ϊ��������ɴ���
	// @Pointcut(" execution(* springframework..target.*.*(..)) ")

	// @Pointcut(" within(springframework..*) ")
	// target������ͨ���д��
//	@Pointcut(" target(springframework.doc.aop.example.aspect.target.MyTarget) ")
//	public void myPointCut() {
//
//	}

//@target�����е�������Ҫ�ж�Ӧ��ע��
	// @annotation��������Ҫ�ж�Ӧ��ע��
//	@Around(value = " myPointCut() && args(arg1,..) && (@target(springframework.doc.aop.example.aspect.MyAnnotation) || @annotation(springframework.doc.aop.example.aspect.MyAnnotation)) ")
//	public Object aspect1(ProceedingJoinPoint pjp, String arg1) {
//		System.out.println("����ִ֪ͨ����");
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
	// ��֪��Ϊʲô����������Բ�����
	// @Before("execution(** ..MyTargetImpl*.perform(*))")
	public void aspect2() {
		System.out.println("before");
	}

	// �е����������args(arg1)����ô�е��Ӧ�ķ�����Ҫ������Ӧ�Ĳ���
	@Pointcut(" target(springframework.doc.aop.example.aspect.target.MyTarget) && args(arg1,..) ")
	public void myPointCut(String arg1) {

	}

	// ����е����ڷ������в��������õ�ʱ��ҲҪ�Ѳ���д��
	// ����е�����е����ϵ�ע��MyAnnotation�����ȫ�У����Ȼ�÷����ϵ�
	// ���û��MyAnnotation���͵�ע���򲻻�ƥ�䵽
	@Around(value = " myPointCut(arg1)  && (@target(myAnnotation1) || @annotation(myAnnotation1))  && this(parent)", argNames = "arg1,myAnnotation1,parent")
	// argnames���е��ж����������ͬ��Ȼ�󷽷��������Ϳ��Բ�ͬ
	// ����������������е��ж����������ͬ����ô����ʡ��argnames

	public Object aspect1(ProceedingJoinPoint pjp, String arg1, MyAnnotation myAnnotation, MyIntroduce myIntroduce) {
		System.out.println("����ִ֪ͨ����");
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
		System.out.println("����ִ֪ͨ����");
		Object result = null;
		try {
			result = pjp.proceed(pjp.getArgs());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result + "arg1" + " " + myAnnotation.value();
	}

	// ���value��֧���ҵ��ӿ��Ժ���ʵ��
	@DeclareParents(value = "springframework..target.impl.*", defaultImpl = MyIntroduceImpl.class)
	public static MyIntroduce introduce;
}
