package springframework.doc.aop.example.aspect.target.impl;

import springframework.doc.aop.example.aspect.MyAnnotation;
import springframework.doc.aop.example.aspect.target.MyTarget;

@MyAnnotation("myAnnotation")
public class MyTargetImpl implements MyTarget {

	@MyAnnotation("abc")
	@Override
	public String perform(String arg1) {
		// TODO Auto-generated method stub
		return "hello ";
	}

}
