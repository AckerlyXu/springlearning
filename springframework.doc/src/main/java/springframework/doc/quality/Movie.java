package springframework.doc.quality;

public class Movie {
      private String name;
      private int price;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Movie [name=" + name + ", price=" + price + "]";
	}
	
      
}
