package springframework.doc.mutiscope;

public  abstract class MySingleBean {
	public MyPrototypeBean getRandom() {
		return createPrototype();
		
	}
	public  abstract MyPrototypeBean createPrototype() ;

}
