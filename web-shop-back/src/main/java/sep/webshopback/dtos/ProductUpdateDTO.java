package sep.webshopback.dtos;

public class ProductUpdateDTO {
	
	private long id;
	private String name;
	private String price;
	private long quantity;
	
	public ProductUpdateDTO() {}

	public ProductUpdateDTO(long id, String name, String price, long quantity) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}



	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
