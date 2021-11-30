package sep.webshopback.dtos;

public class ProductDTO {
	
	private String name;
	private String price;
	private long quantity;
	
	public ProductDTO() {}
	
	public ProductDTO(String name, String price, long quantity) {
		super();
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	
	
}
