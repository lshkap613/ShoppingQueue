import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Shopper> shoppers = new ArrayList<Shopper>();
		Shopper currentShopper;
		int menuOption;
		
		// Instantiate store and enter
		Store store = new Store("Superiority Priority Shop");
		store.enter();
		
		// add a new shopper
		shoppers.add(store.createShopper(input));
		
		// set current shopper to that newly-created shopper
		currentShopper = shoppers.get(0);
		
		// loop until program exits
		while(true) {
			// Print current shopper
			System.out.println("\nCURRENT SHOPPER: " + currentShopper.getName());

			// Print store menu
			store.storeMenu();
			
			// Ask user to enter number of menu option
			System.out.println("Enter number of menu option");
			menuOption = input.nextInt();
			
			store.validator(1, 8, menuOption, input);

			// navigate menu based on selected menu option
			switch (menuOption) {
			
				// Add item to cart
				case 1: 
					currentShopper.addItem(store.chooseItem());
					break;
					
				// remove item from cart
				case 2:
					Item itemToRemove = currentShopper.chooseItemFromCart(input); // returns null if item doesn't exist in cart
					
					// if chosen item exists in the cart...
					if (itemToRemove != null) {
						// Ask user how many of those items to remove
						System.out.println("How many " + itemToRemove.getName() + "'s would you like to remove?");
						int removeQuantity = input.nextInt();
						
						// Remove that amount of that item from cart. (removeItem method handles invalid input for quantity)
						currentShopper.removeItem(itemToRemove, removeQuantity);
					}
					break;
					
				// View cart
				case 3:
					currentShopper.seeCart();
					break;
					
				// Create new shopper and add to shoppers ArrayList
				case 4: 
					currentShopper = store.createShopper(input);
					shoppers.add(currentShopper);
					break;
					
				// Switch shopper
				case 5:
					// See all shoppers in ordered list
					store.viewShoppers(shoppers);
					
					System.out.println("Enter the number of the customer you would like to switch to.");
					int shopperNum = input.nextInt();
					
					// Validate input
					store.validator(0, shoppers.size(), shopperNum, input);
					
					int shopperIndex = shopperNum - 1;
					
					// Set selected shopper to be current shopper
					currentShopper = shoppers.get(shopperIndex);
					break;
				
				// View all customers
				case 6:
					store.viewShoppers(shoppers);
					break;
				
				// checkout
				case 7: 
					store.checkout(shoppers);
					
					System.out.println("\nNo customers left in store.");
					System.out.println("Enter 1 to add a shopper, 2 to exit");
					
					int answer = input.nextInt();
					
					store.validator(1, 2, answer, input);
					
					if (answer == 1) {
						currentShopper = store.createShopper(input);
						shoppers.add(currentShopper);
					} else {
						store.leave();
						break;
					}
					break;
					
				// leave store
				case 8:
					store.leave();
					break;
					
				default:
					System.out.println("Invalid option.");
			}
		}

	}

}
