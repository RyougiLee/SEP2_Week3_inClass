package com.example.calculatorapp;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class DbResourceBundleTest {

    @Test
    void testBundleAccess() {
        Map<String, String> mockData = new HashMap<>();
        mockData.put("key1", "value1");
        mockData.put("key2", "value2");

        try (MockedStatic<LocalizationService> locService = mockStatic(LocalizationService.class)) {
            locService.when(() -> LocalizationService.getLabelsByLanguage("English")).thenReturn(mockData);

            DbResourceBundle bundle = new DbResourceBundle("English");

            assertEquals("value1", bundle.handleGetObject("key1"));
            assertEquals("value2", bundle.handleGetObject("key2"));
            
            Enumeration<String> keys = bundle.getKeys();
            int count = 0;
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                assertTrue(mockData.containsKey(key));
                count++;
            }
            assertEquals(2, count);
        }
    }
}
