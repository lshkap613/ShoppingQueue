
public class Item {
	private String name;
	private double price;
	private int quantity;
	
	/**
	 * Constructor
	 * @param name name of item
	 * @param price price of item
	 */
	public Item (String name, double price) {
		this.name = name;
		this.price = price;
		quantity = 1;
	}
	
	
	/**
	 * Constructor
	 * @param name name of item
	 * @param price price of item
	 * @param quantity number of items
	 */
	public Item (String name, double price, int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	// GETTERS AND SETTERS:
	
	public String getName() {
		return name;
	}

	
	public void setName(String itemName) {
		this.name = itemName;
	}

	
	public double getPrice() {
		return price;
	}

	
	public void setPrice(double itemPrice) {
		this.price = itemPrice;
	}
	
	
	public int getQuantity() {
		return this.quantity;
	}
	
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * To String method
	 */
	@Override
	public String toString() {
		return "Item [itemName=" + name + ", itemPrice=" + price + "]";
	}
	
	
}
