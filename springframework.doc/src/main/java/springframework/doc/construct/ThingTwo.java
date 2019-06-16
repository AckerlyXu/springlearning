package springframework.doc.construct;

public class ThingTwo {
	private String str;
	private int integer;
	
  public ThingTwo(String str, int integer) {
		super();
		this.str = str;
		this.integer = integer;
	}
public String getInfo() {
	return str+integer;
}
public int getCount() {
	  return 2;
  }
}
