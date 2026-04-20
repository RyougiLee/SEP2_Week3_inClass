package com.example.calculatorapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorModelTest {

    private CalculatorModel model;

    @BeforeEach
    void setUp(){
        model = new CalculatorModel("English");
    }

    @Test
    @DisplayName("Test calculation of single item")
    void testCalculateItemCost(){
        assertEquals(50.0, model.calculateItemCost(2, 25.0));
        assertEquals(0.0, model.calculateItemCost(0, 100));
        assertEquals(0.0, model.calculateItemCost(-1, 100));
        assertEquals(0.0, model.calculateItemCost(1, -1));
    }

    @Test
    @DisplayName("Test calculation of all the items in the cart")
    void testCalculationTotalSum(){
        List<CalculatorModel.Item> cart = Arrays.asList(
                new CalculatorModel.Item(2, 10.0),
                new CalculatorModel.Item(1, 5.0),
                new CalculatorModel.Item(3, 100.0)
        );

        assertEquals(325.0, model.calculateTotal(cart));
        assertEquals(6, model.getTotalItems());
        assertEquals(325.0, model.getTotalCost());
    }

    @Test
    @DisplayName("Test language getter and setter")
    void testLanguageAccessors(){
        assertEquals("English", model.getLanguage());
        model.setLanguage("Finnish");
        assertEquals("Finnish", model.getLanguage());
    }

    @Test
    @DisplayName("Test Item accessors")
    void testItemAccessors(){
        CalculatorModel.Item item = new CalculatorModel.Item(5, 10.0);
        assertEquals(5, item.getQuantity());
        assertEquals(10.0, item.getUnitPrice());
    }
}
