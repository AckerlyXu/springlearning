package springframework.doc.aop.example.aspect.introduce.impl;

import springframework.doc.aop.example.aspect.introduce.MyIntroduce;

public class MyIntroduceImpl implements MyIntroduce {

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return "EXECUTE";
	}

	@Override
	public String perform() {
		// TODO Auto-generated method stub
		return "perform";
	}

}
