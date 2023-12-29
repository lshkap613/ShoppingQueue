import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Shopper implements Comparable<Shopper>{
	private String name;
	private List<Item> cart;
	private double totalPrice;
	
	
	/**
	 * Constructor
	 * @param name name of Shopper
	 */
	public Shopper(String name) {
		this.name = name;
		cart = new ArrayList<Item>();
		totalPrice = 0;
	}
	
	// GETTERS AND SETTERS:
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Item> getCart() {
		return cart;
	}

	public void setCart(List<Item> cart) {
		this.cart = cart;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	
	public void setTotalPrice(double price) {
		this.totalPrice = price;
	}
	
	/**
	 * Resets cart's total price to 0;
	 */
	public void resetTotalPrice() {
		this.totalPrice = 0;
	}
	
	/**
	 * Determines if discount should be applied based on
	 * value of totalPrice (in cart)
	 * @return the discount if there is a discount,
	 * otherwise returns 0
	 */
	public double discount() {
		// If totalPrice is greater than 100, discount is %15 of original price
		if (totalPrice > 100) {
			return totalPrice * 0.15;
		// otherwise, discount is 0
		} else {
			return 0.0;
		}
	}
	
	
	/**
	 * Method to add a quantity of an item to the cart
	 * @param item item to add
	 * @param quantity number of those items to add
	 */
	public void addItem(Item item, int quantity) {
		// set the quantity of the item to variable quantity
		item.setQuantity(quantity);
		
		// add item to the cart
		cart.add(item);
		
		// increment price accordingly
		totalPrice += item.getPrice() * quantity;
	}
	
	
	/**
	 * Method to add one item to the cart
	 * @param item the item to add
	 */
	public void addItem(Item item) {
		// add item to cart
		cart.add(item);
		
		// increment price accordingly
		totalPrice += item.getPrice() * item.getQuantity();
	}
	
	
	/**
	 * Method to choose an item from the cart. Method provides opportunity
	 * to try to choose an item again if requested item does not exist in 
	 * cart. User input must be spelled correctly, case insensitive.
	 * @param input 
	 * @return returns the requested item if found, or null if not found
	 */
	public Item chooseItemFromCart(Scanner input) {
		// Initialize variable that will contain name of item searched for in cart
		String itemToRemove;
		
		// initialize flag variable to true
		boolean search = true;
		while (search) {
			// display cart
			seeCart();
			
			// Ask user for the name of the item s/he would like to remove from cart
			System.out.print("\nName of item you would like to remove: ");
			itemToRemove = input.nextLine();
			
			// Search cart for this item
			for (int item = 0; item < cart.size(); item++) {
				
				// If found, return the item
				if (cart.get(item).getName().compareToIgnoreCase(itemToRemove) == 0) {
					return cart.get(item);
				}
			}
			
			// If item not found, ask user if s/he would like to try to search the cart again
			System.out.println("\nItem does not exist in cart.");
			System.out.println("If you would like to start again, enter 'Y'");
			char answer = input.nextLine().toLowerCase().charAt(0);
			
			// If user does not answer y for yes, flag variable set to false...
			if (answer != 'y') {
				search = false;
			}
		}

		// and null is returned
		return null;
	}
	
	
	/**
	 * Method that removes an specific item of a certain quantity from
	 * the cart
	 * @param item item to remove
	 * @param quantity number of those items to remove
	 */
	public void removeItem(Item item, int quantity) {
		// find index of specified item in cart, or -1 if item not found
		int itemIndex = cart.indexOf(item);
		
		// if index is -1, print that the item does not exist in cart, and return
		if (itemIndex == -1) {
			System.out.println("This item does not exist in " + name + "'s cart.");
			return;
		}
		
		
		// If quantity requested to remove from cart is greater than the amount of that item that
		// is in the cart, notify user of this discrepancy, and remove all of that type of item
		// and adjust cart price
		if (quantity > item.getQuantity()) {
			System.out.println("\nOnly " + item.getQuantity() + " " + item.getName() + "(s) in your cart");
			System.out.println("\nRemoving all " + item.getQuantity() + " " + item.getName() + ("(s)."));
			
			cart.remove(itemIndex);
			totalPrice -= item.getPrice() * item.getQuantity();
		
			// If quantity requested to remove is equal the quantity of that item in the cart, remove all
			// and adjust cart price accordingly
		} else if (quantity == item.getQuantity()) {
			cart.remove(itemIndex);
			totalPrice -= item.getPrice() * item.getQuantity();
			
		// If quantity is within valid range, remove that amount of that item, and adjust price accordingly
		} else if (quantity > 0 && quantity < item.getQuantity()){
			System.out.println("Removing " + quantity + " " + item.getName() + "(s)");
			
			item.setQuantity(item.getQuantity() - quantity);
			totalPrice -= item.getPrice() * quantity;
		
		// If quantity requested to remove is 0 or less, throw exception
		} else {
			throw new IllegalArgumentException("Quantity to remove must be at least 1");
		}
	}

	
	/**
	 * Method that prints out all items in cart with the quantities
	 * and prices. If a discount has been applied, the discounted 
	 * price is also shown.
	 */
	public void seeCart() {
		System.out.println();
		
		// variable that will hold total price before discount of cart items
		double priceBeforeDiscount = 0;
		
		// print each item with quantitites and prices, meanwhile accumulating total price
		for (Item item : cart) {
			System.out.println(item.getQuantity() + " x " + item.getName() + ":");
			System.out.println("\t$" + item.getPrice() * item.getQuantity());
			priceBeforeDiscount += item.getPrice() * item.getQuantity();
		}
		
		// Display price before discount (even if no discount applied)
		System.out.println("~~~~~~~~~~~~~~~");
		System.out.printf("Total: $%.2f\n", priceBeforeDiscount);
		
		// If a discount had been applied before now, display discounted price
		if (priceBeforeDiscount != totalPrice) {
			double discount = priceBeforeDiscount - totalPrice;
			System.out.printf("Discount: $%.2f\n", discount);
			System.out.printf("\nTotal: $%.2f\n", totalPrice);
		}
		
		System.out.println();

	}
	
	
	/**
	 * Method that prints receipt. It is just the seeCart method with
	 * an added print statement stating "Receipt" with fancy squiggles.
	 */
	public void reciept() {
		System.out.println("\n~~~~~~  Receipt  ~~~~~~");
		seeCart();
	}
	

	/**
	 * CompareTo method, comparing shoppers according to totalPrice of cart
	 */
	@Override
	public int compareTo(Shopper o) {
		if (this.totalPrice > o.getTotalPrice()) {
			return 1;
		} else if (this.totalPrice < o.getTotalPrice()) {
			return -1;
		} else {
			return 0;
		}
	}
}