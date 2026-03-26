package com.example.calculatorapp;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    ComboBox<String> languageSelectMenu;
    @FXML
    TextField itemNumbersInput;
    @FXML
    Button enterItemButton;
    @FXML
    VBox itemsContainer;
    @FXML
    Label totalPriceLabel;

    @FXML public void handleLanguageConfirmed(){
        String selectedLanguage = languageSelectMenu.getValue();
        Locale locale = null;
        switch (selectedLanguage){
            case "English":
                locale = new Locale("en", "US");
                break;
            case "Finnish":
                locale = new Locale("fi", "FI");
                break;
            case "Swedish":
                locale = new Locale("sv", "SE");
                break;
            case "Arabic":
                locale = new Locale("ar", "SA");
                break;
            case "Japanese":
                locale = new Locale("ja", "JP");
                break;
            default:
                locale = new Locale("en", "US");
                break;
        }

        try{
            MainApp.setRoot("lang.MessagesBundle", locale);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML public void handleEnterItems(){

        itemsContainer.getChildren().clear();
        int num = Integer.parseInt(itemNumbersInput.getText());
        for(int i=0; i<num; i++){
            HBox hbox = new HBox();
            TextField quantityTextField = new TextField();
            TextField priceTextField = new TextField();
            hbox.getChildren().addAll(quantityTextField, priceTextField);
            hbox.setSpacing(10);
            itemsContainer.getChildren().add(hbox);
        }
    }

    @FXML public void handleCalculate(){
        double total = 0;
        for(Node node: itemsContainer.getChildren()){
            if(node instanceof HBox row){
                TextField quantityTextField = (TextField) row.getChildren().get(0);
                TextField priceTextField = (TextField) row.getChildren().get(1);

                try {
                    String quantityText = quantityTextField.getText().trim();
                    String priceText = priceTextField.getText().trim();

                    if(!quantityText.isEmpty() && !priceText.isEmpty()){
                        double quantity = Double.parseDouble(quantityText);
                        double price = Double.parseDouble(priceText);
                        total += quantity * price;
                    }
                }  catch (Exception e){
                    System.out.println("Skip line");
                }
            }
        }
        totalPriceLabel.setText(String.format("%.2f", total));
    }


    public void initialize(){
        languageSelectMenu.getItems().clear();
        languageSelectMenu.getItems().addAll("English", "Finnish", "Swedish", "Arabic", "Japanese");
        languageSelectMenu.getSelectionModel().selectFirst();
    }
}
