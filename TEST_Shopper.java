import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TEST_Shopper {

	@Test
	void test_stableSort() {
		PriorityHeap<Shopper> checkout = new PriorityHeap<Shopper>();
		
		// create 2 shopper with equal totalPurchaseValue
		Shopper first = new Shopper("First");
		first.setTotalPrice(50);
		
		Shopper second = new Shopper("Second");
		second.setTotalPrice(50);

		checkout.enqueue(first);
		checkout.enqueue(second);
		
		Shopper dequeuedFirst = checkout.dequeue();
		
		assertEquals(dequeuedFirst, first);
	}
	
	@Test
	void test_PriorityQueueWorks() {
		PriorityHeap<Shopper> checkout = new PriorityHeap<Shopper>();
		
		// create 2 shoppers
		Shopper firstLeast = new Shopper("First");
		firstLeast.setTotalPrice(20);
		
		Shopper secondMost = new Shopper("Second");
		secondMost.setTotalPrice(40);
		
		Shopper thirdMed = new Shopper("Third");
		thirdMed.setTotalPrice(30);

		checkout.enqueue(firstLeast);
		checkout.enqueue(secondMost);
		checkout.enqueue(thirdMed);
		
		Shopper dequeuedFirst = checkout.dequeue();
		Shopper dequeuedSecond = checkout.dequeue();
		Shopper dequeuedThird = checkout.dequeue();
		
		assertEquals(dequeuedFirst, secondMost);
		assertEquals(dequeuedSecond, thirdMed);
		assertEquals(dequeuedThird, firstLeast);
	}
	
	@Test
	void test_CouponDistribution() {
		Shopper under100 = new Shopper("");
		under100.setTotalPrice(99);
		
		Shopper exactly100 = new Shopper("");
		exactly100.setTotalPrice(100);
		
		Shopper over100 = new Shopper("");
		over100.setTotalPrice(101);
		
		
		assertEquals(under100.discount(), 0);
		assertEquals(exactly100.discount(), 0);
		assertEquals(over100.discount(), over100.getTotalPrice() * 0.15);
	}
	
	@Test
	void test_priceAndDiscountCorrect() {
		Store store = new Store("");
		Shopper customer = new Shopper("Mommy");
		
		customer.addItem(new Item("Ball", 3.5, 1));
		customer.addItem(new Item("Coloring Book", 12.99, 4));
		customer.addItem(new Item("Kite", 14.49, 2));
		customer.addItem(new Item("Paper Towels, Family Pack", 35, 1));
		
		assertEquals(customer.getTotalPrice(), 3.5 + 12.99*4 + 14.49*2 + 35, 0.0001);
		assertEquals(customer.discount(), 119.44 * 0.15);
	}
	
	@Test
	void test_emptyStoreAfterCheckoutProcess() {
		Store store = new Store("");
		
		ArrayList<Shopper> shoppers = new ArrayList<Shopper>();
		shoppers.add(new Shopper("A"));
		shoppers.add(new Shopper("B"));
		shoppers.add(new Shopper("C"));
		
		store.checkout(shoppers);
		
		assertTrue(shoppers.size() == 0);
	}

}
