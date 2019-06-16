package springframework.doc.construct;

public class ThingOne {

	private ThingTwo two;
	private ThingThree three;

	public ThingOne(ThingTwo two, ThingThree three) {

		super();
		this.two = two;
		this.three = three;
	}

	public int getCount() {
		return two.getCount() + three.getCount();
	}

}
