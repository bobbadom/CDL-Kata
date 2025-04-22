package org.example;


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // Define pricing rules
        Map<Character, PricingRule> pricingRules = new HashMap<>();
        pricingRules.put('A', new PricingRule(50, 3, 130));
        pricingRules.put('B', new PricingRule(30, 2, 45));
        pricingRules.put('C', new PricingRule(20, 0, 0));
        pricingRules.put('D', new PricingRule(15, 0, 0));  // no special


        Checkout checkout = new Checkout(pricingRules);
        Scanner scanner = new Scanner(System.in);


        System.out.println("Enter SKUs to scan one by one (press Enter to finish):");


        while (true) {
            System.out.print("Scan item: ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.isEmpty()) {
                break;
            }
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
                checkout.scan(input.charAt(0));
            } else {
                System.out.println("Invalid input. Please enter a single letter (A-Z).");
            }
        }


        System.out.println("Final total: " + checkout.getTotal() + "p");
        scanner.close();
    }
}
