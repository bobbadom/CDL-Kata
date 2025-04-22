import org.example.Checkout;
import org.example.PricingRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class CheckoutTest {
    private Map<Character, PricingRule> pricingRules;


    @BeforeEach
    public void setup() {
        pricingRules = new HashMap<>();
        pricingRules.put('A', new PricingRule(50, 3, 130));
        pricingRules.put('B', new PricingRule(30, 2, 45));
        pricingRules.put('C', new PricingRule(20, 0, 0));
        pricingRules.put('D', new PricingRule(15, 0, 0));
    }


    @Test
    public void testSingleItem() {
        Checkout checkout = new Checkout(pricingRules);
        checkout.scan('A');
        assertEquals(50, checkout.getTotal());
    }


    @Test
    public void testMultipleItemsNoSpecial() {
        Checkout checkout = new Checkout(pricingRules);
        checkout.scan('C');
        checkout.scan('D');
        assertEquals(35, checkout.getTotal());
    }


    @Test
    public void testSpecialPriceA() {
        Checkout checkout = new Checkout(pricingRules);
        checkout.scan('A');
        checkout.scan('A');
        checkout.scan('A');
        assertEquals(130, checkout.getTotal());
    }


    @Test
    public void testSpecialPriceB() {
        Checkout checkout = new Checkout(pricingRules);
        checkout.scan('B');
        checkout.scan('B');
        assertEquals(45, checkout.getTotal());
    }


    @Test
    public void testMixedItems() {
        Checkout checkout = new Checkout(pricingRules);
        checkout.scan('A');
        checkout.scan('B');
        checkout.scan('A');
        checkout.scan('B');
        checkout.scan('A'); // Total: 130 (3A) + 45 (2B) = 175
        assertEquals(175, checkout.getTotal());
    }


    @Test
    public void testIncompleteSpecial() {
        Checkout checkout = new Checkout(pricingRules);
        checkout.scan('A');
        checkout.scan('A');
        assertEquals(100, checkout.getTotal());
    }


    @Test
    public void testNonDiscountedItems() {
        Checkout checkout = new Checkout(pricingRules);
        checkout.scan('C');
        checkout.scan('C');
        checkout.scan('C');
        assertEquals(60, checkout.getTotal());
    }
    @Test
    public void testInvalidSKUThrowsException() {
        Checkout checkout = new Checkout(pricingRules);
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> checkout.scan('X')
        );
        assertEquals("Unknown SKU: X", exception.getMessage());
    }


    @Test
    public void testPerformanceLargeInput() {
        Checkout checkout = new Checkout(pricingRules);
        int numItems = 100;
        for (int i = 0; i < numItems; i++) {
            checkout.scan('A');
        }
        int expected = (numItems / 3) * 130 + (numItems % 3) * 50;
        assertEquals(expected, checkout.getTotal());
    }


    @Test
    public void testUnorderedScanning() {
        Checkout checkout = new Checkout(pricingRules);
        checkout.scan('B');
        checkout.scan('A');
        checkout.scan('B');
        checkout.scan('A');
        checkout.scan('A');
        assertEquals(175, checkout.getTotal());
    }


}
