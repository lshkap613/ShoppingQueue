import java.util.ArrayList;
import java.util.Scanner;

public class Store {
	Scanner input;
	private String name;
	private PriorityHeap<Shopper> checkoutLine = new PriorityHeap<Shopper>();

	
	/**
	 * Constructor:
	 * @param name name of store
	 */
	public Store(String name) {
		this.name = name;
	}
	
	
	/**
	 * Method simulating entering the store (just a welcome sign)
	 */
	public void enter() {
		System.out.println("Welcome to " + name + "!");
	}
	
	
	/**
	 * Method to create a new shopper.
	 * @param input
	 * @return returns shopper created
	 */
	public Shopper createShopper(Scanner input) {		
		System.out.print("\nCustomer name: ");
		String name = input.nextLine();
		Shopper newShopper = new Shopper(name);
				
		return newShopper;
	}
	
	
	/**
	 * Method that prints the store's menu
	 */
	public void storeMenu() {
		System.out.println();
		System.out.println("1. Add");
		System.out.println("2. Remove");
		System.out.println("3. View Cart");
		System.out.println("4. New customer");
		System.out.println("5. Switch customer");
		System.out.println("6. View Customers");
		System.out.println("7. Check out all customers");
		System.out.println("8. Quit");
		
	}
	
	
	/**
	 * Method that prints out all shoppers in store
	 * @param shoppers ArrayList of all shoppers
	 */
	public void viewShoppers(ArrayList<Shopper> shoppers) {
		System.out.println("\nCustomers:");
		for (int num = 1; num <= shoppers.size(); num++) {
			System.out.println(num + ". " + shoppers.get(num - 1).getName());
		}
	}
	
	
	/**
	 * Method that returns number of shoppers in the store
	 * @param shoppers ArrayList of shoppers
	 * @return number of shoppers in store
	 */
	public int numOfShoppers(ArrayList<Shopper> shoppers) {
		return shoppers.size();
	}
	
	
	/**
	 * Method to choose an item from the store (by letting the
	 * user make up and create an Item
	 * @return (created and) chosen item
	 */
	public Item chooseItem() {
		input = new Scanner(System.in);
		String itemName;
		double itemPrice;
		int itemQuantity;

		System.out.print("Item name: ");
		itemName = input.nextLine();
		
		System.out.print("\nItem price: ");
		itemPrice = input.nextDouble();
		validator(0.01, Double.MAX_VALUE, itemPrice, input);
		
		System.out.println("\nItem quantity: ");
		itemQuantity = input.nextInt();
		input.nextLine();
		
		// create new item
		Item newItem = new Item(itemName, itemPrice, itemQuantity);

		return newItem;
	}
	
	
	/**
	 * Method that checks out all shoppers from the store
	 * @param shoppers ArrayList of all shoppers in store 
	 */
	public void checkout(ArrayList<Shopper> shoppers) {
		for (Shopper shopper : shoppers) {
			double originalPrice = shopper.getTotalPrice();
			
			// if customer on checkout line has an empty cart, skip to next customer
			if (originalPrice == 0) {
				System.out.println(shopper.getName() + " is leaving store because cart is empty.");
				continue;
			}
			// if there is a discount, show coupon and reset total price
			if (shopper.discount() != 0) {
				System.out.println("\nCOUPON: %15 off on purchases over $100");
				shopper.setTotalPrice(originalPrice - shopper.discount());
			}
			// enqueue all shoppers 
			checkoutLine.enqueue(shopper);
		}
		
		// while there are still customers to check out...
		while (!checkoutLine.isEmpty()) {
			// State which customer is paying now
			Shopper paying = checkoutLine.peek();
			System.out.println("\n" + paying.getName() + " is paying now");
			
			// show reciept
			paying.reciept();
			
			// dequeue from check out line
			checkoutLine.dequeue();
			
			// reset cart to empty and totalPrice to 0
			paying.setCart(new ArrayList<Item>()); // ?
			paying.resetTotalPrice();
		}
		
		shoppers.clear(); // reset shoppers array
		
	}
	
	
	/**
	 * Method to leave store (and exit program). The method issues a warning 
	 * against shop lifting before exiting, since this method may be called 
	 * without first checking out
	 */
	public void leave() {
		input = new Scanner(System.in);
		System.out.println("Are you sure you would like to quit? ('Y/N')");
		char answer = input.nextLine().toLowerCase().charAt(0);
		
		answer = yesNoValidate(answer);
		
		if (answer == 'y') {
			System.out.println("\nDon't even think about shoplifting. Goodbye.");
			System.exit(0);
		}
	}
	
	
	/**
	 * Method that validates yes/no responses. It only accepts y or n as a 
	 * valid answer
	 * @param answer user input
	 * @return answer, which will only be the character y or n
	 */
	public char yesNoValidate(char answer) {
		while (answer != 'y' && answer != 'n') {
			System.out.println("\nPlease enter 'Y' for yes or 'N' for no.");
			answer = input.nextLine().toLowerCase().charAt(0);
		}
		
		return answer;
	}
	
	public int validator(int min, int max, int num, Scanner input) {
		while(num < min) {
			System.out.println("Invalid option. Enter a number that is at least " + min + ".");
			num = input.nextInt();
		}
		
		while (num > max) {
			System.out.println("Invalid input. Enter a number that is not greater than " + max + ".");
			num = input.nextInt();
		}
		
		// clear buffer
		input.nextLine();
		return num;
	}
	
	public double validator(double min, double max, double num, Scanner input) {
		while(num < min) {
			System.out.println("Invalid option. Enter a number that is at least " + min + ".");
			num = input.nextInt();
		}
		
		while (num > max) {
			System.out.println("Invalid input. Enter a number that is not greater than " + max + ".");
			num = input.nextInt();
		}
		
		// clear buffer
		input.nextLine();
		return num;
	}
}
