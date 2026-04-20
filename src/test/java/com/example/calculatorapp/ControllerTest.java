package com.example.calculatorapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControllerTest extends ApplicationTest {

    private Controller controller;

    @BeforeAll
    public static void setupHeadless() {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
             // On Windows, if we can't run headful, we might need monocle, 
             // but let's try standard first or set headless property.
             System.setProperty("testfx.robot", "glass");
             System.setProperty("testfx.headless", "true");
             System.setProperty("prism.order", "sw");
             System.setProperty("glass.platform", "Monocle");
             System.setProperty("monocle.platform", "Headless");
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize MainApp.primaryStage to avoid NPE in setRoot
        Field field = MainApp.class.getDeclaredField("primaryStage");
        field.setAccessible(true);
        field.set(null, stage);

        try (MockedStatic<LocalizationService> locMock = mockStatic(LocalizationService.class)) {
            Map<String, String> mockLabels = new HashMap<>();
            mockLabels.put("ui.label.selectLanguage", "Select Language");
            mockLabels.put("ui.button.confirmLanguage", "Confirm");
            mockLabels.put("ui.label.enterNumbers", "Enter Numbers");
            mockLabels.put("ui.button.enterItems", "Enter Items");
            mockLabels.put("ui.label.itemQuantity", "Quantity");
            mockLabels.put("ui.label.unitPrice", "Price");
            mockLabels.put("ui.button.calculate", "Calculate");
            mockLabels.put("ui.label.total", "Total");
            
            locMock.when(() -> LocalizationService.getLabelsByLanguage(anyString())).thenReturn(mockLabels);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/calculatorapp/main-view.fxml"));
            loader.setResources(new DbResourceBundle("English"));
            Parent root = loader.load();
            controller = loader.getController();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @Test
    void testHandleEnterItems() {
        TextField itemNumbersInput = lookup("#itemNumbersInput").query();
        clickOn(itemNumbersInput).write("3");
        clickOn("#enterItemButton");

        VBox itemsContainer = lookup("#itemsContainer").query();
        assertEquals(3, itemsContainer.getChildren().size());
    }

    @Test
    void testHandleCalculate() {
        // Setup 1 item
        TextField itemNumbersInput = lookup("#itemNumbersInput").queryAs(TextField.class);
        interact(() -> {
            itemNumbersInput.setText("1");
            controller.handleEnterItems();
        });

        VBox itemsContainer = lookup("#itemsContainer").queryAs(VBox.class);
        TextField qtyField = (TextField) ((javafx.scene.layout.HBox) itemsContainer.getChildren().get(0)).getChildren().get(0);
        TextField priceField = (TextField) ((javafx.scene.layout.HBox) itemsContainer.getChildren().get(0)).getChildren().get(1);

        interact(() -> {
            qtyField.setText("2");
            priceField.setText("50");
        });

        interact(() -> {
            try (MockedStatic<CartService> cartMock = mockStatic(CartService.class)) {
                try {
                    controller.handleCalculate();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                cartMock.verify(() -> CartService.saveCartRecords(any(), any()), times(1));
            }
        });
        
        Label totalPriceLabel = lookup("#totalPriceLabel").queryAs(Label.class);
        assertEquals("100.00", totalPriceLabel.getText());
    }

    @Test
    void testHandleLanguageConfirmed() {
        ComboBox<String> languageSelectMenu = lookup("#languageSelectMenu").queryAs(ComboBox.class);
        interact(() -> languageSelectMenu.getSelectionModel().select("Finnish"));

        interact(() -> {
            try (MockedStatic<MainApp> mainAppMock = mockStatic(MainApp.class)) {
                controller.handleLanguageConfirmed();
                mainAppMock.verify(() -> MainApp.setRoot("Finnish"));
            }
        });
    }
}
