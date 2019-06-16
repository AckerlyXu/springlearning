package springframework.doc.factory;

public class FactoryProduct {
	private String name;
	private int number;
	
    public FactoryProduct(String name, int number) {
		super();
		this.name = name;
		this.number = number;
	}

	public String getProduct() {
    	return name+number;
    }
}
