package com.example.calculatorapp;

import java.util.List;

public class CalculatorModel {
    public double calculateItemCost(double quantity, double unitPrice) {
        if (quantity < 0 || unitPrice < 0) {
            return 0;
        }
        return quantity * unitPrice;
    }

    public double calculateTotal(List<Item> items) {
        double total = 0;
        for (Item item : items) {
            total += calculateItemCost(item.getQuantity(), item.getUnitPrice());
        }
        return total;
    }

    public static class Item {
        private final double quantity;
        private final double unitPrice;

        public Item(double quantity, double unitPrice) {
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public double getQuantity() { return quantity; }
        public double getUnitPrice() { return unitPrice; }
    }
}
