package org.example;


import java.util.HashMap;
import java.util.Map;


public class Checkout {
    private final Map<Character, PricingRule> pricingRules;
    private final Map<Character, Integer> itemsScanned;


    public Checkout(Map<Character, PricingRule> pricingRules) {
        this.pricingRules = pricingRules;
        this.itemsScanned = new HashMap<>();
    }


    public void scan(char item) {


        if (!pricingRules.containsKey(item)) {
            throw new IllegalArgumentException("Unknown SKU: " + item);
        }
        itemsScanned.put(item, itemsScanned.getOrDefault(item, 0) + 1);
        System.out.println("Scanned item: " + item + " | Running total: " + getTotal() + "p");
    }


    public int getTotal() {
        int total = 0;
        for (Map.Entry<Character, Integer> entry : itemsScanned.entrySet()) {
            char item = entry.getKey();
            int quantity = entry.getValue();


            PricingRule rule = pricingRules.get(item);
            if (rule != null) {
                if (rule.getSpecialQuantity() > 0) {
                    int specialSets = quantity / rule.getSpecialQuantity();
                    int remainder = quantity % rule.getSpecialQuantity();
                    total += specialSets * rule.getSpecialPrice() + remainder * rule.getUnitPrice();
                } else {
                    total += quantity * rule.getUnitPrice();
                }
            }
        }
        return total;
    }
}
