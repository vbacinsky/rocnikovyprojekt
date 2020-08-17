package client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;

public class BuyDialog {

    private static  ArrayList<Chip> chips = new ArrayList<>();
    private static PlayerTemplate playerTemplate;
    private static Button btnOk;
    private static int price = 0;
    private Stage dialogStage = new Stage();

    public BuyDialog(PlayerTemplate playerTemplate) {
        this.playerTemplate = playerTemplate;
        Scene scene = new Scene(new Group());

        dialogStage.setTitle("Table");
        dialogStage.setWidth(550);
        dialogStage.setHeight(600);

        final VBox mainBox = new VBox();
        mainBox.setSpacing(5);
        mainBox.setPadding(new Insets(10, 10, 10, 10));
        mainBox.setAlignment(Pos.CENTER);

        final Label labelNegative = new Label("Negative chips");
        labelNegative.setFont(new Font("Arial", 20));

        scene.getStylesheets().add("styles/styles.css");

        btnOk = new Button("BUY FOR 0F");

        final VBox buttonBoxN = new VBox();
        buttonBoxN.setSpacing(5);
        buttonBoxN.setPadding(new Insets(40, 10, 10, 10));

        buttonBoxN.getChildren().addAll(new ButtonBox(false, "Z"), new ButtonBox(false, "RC"), new ButtonBox(false, "B"));

        final HBox negativeBox = new HBox();
        negativeBox.setSpacing(5);
        negativeBox.setPadding(new Insets(10, 10, 10, 10));
        negativeBox.setAlignment(Pos.CENTER);

        ObservableList<Information> dataNegative = FXCollections.observableArrayList(
                new Information("Z - Zbojnici", "1 F"),
                new Information("RC - Rozbita cesta", "1 F"),
                new Information("B - Bazina", "1 F")
        );

        TableChips tableNegative = new TableChips("negative", dataNegative);
        negativeBox.getChildren().addAll(tableNegative, buttonBoxN);
        mainBox.getChildren().addAll(labelNegative, negativeBox, btnOk);
        ((Group) scene.getRoot()).getChildren().addAll(mainBox);

        btnOk.setOnAction((
                ActionEvent event) -> {
            try {
                if(playerTemplate.getMoney() < price) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("POZOR");
                    alert.setHeaderText(null);
                    alert.setContentText("Tvoj aktualny pocet fazul je " + playerTemplate.getMoney() + ", ale cena je prilis vysoka, az " + price);
                    ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(buttonTypeOK);
                    alert.show();
                } else {
                    int x = playerTemplate.getMoney() - price;
                    playerTemplate.setMoney("" + x + " F");
                    for (Chip chip : chips) {
                        playerTemplate.addNegativeChips(chip);
                    }
                }
                price = 0;
                btnOk.setText("BUY FOR 0 F");

                chips.clear();

                dialogStage.close();
            } catch (NumberFormatException e) {
                System.out.println("chyba");
            }
        });


        dialogStage.setScene(scene);
    }

    public void showDialog() {
        dialogStage.showAndWait();
    }


    public static class Information {

        private final SimpleStringProperty nazovzetonu;
        private final SimpleStringProperty cena;

        private Information (String name, String price) {
            this.nazovzetonu = new SimpleStringProperty(name);
            this.cena = new SimpleStringProperty(price);
        }

        public String getNazovzetonu() {
            return nazovzetonu.get();
        }

        public void setNazovzetonu(String oName) {
            nazovzetonu.set(oName);
        }

        public String getCena() {
            return cena.get();
        }

        public void setCena (String vName) {
            cena.set(vName);
        }
    }

    private static class TableChips extends TableView {

        public TableChips(String string, ObservableList<Information> data) {

            final Label label= new Label(string + " chips");
            label.setFont(new Font("Arial", 20));
            this.setItems(data);


            this.setEditable(true);

            TableColumn name = new TableColumn("Nazov zetonu");
            name.setMinWidth(150);
            name.setCellValueFactory(new PropertyValueFactory<Information, String>("nazovzetonu"));


            TableColumn price = new TableColumn("Cena");
            price.setMinWidth(150);
            price.setCellValueFactory(new PropertyValueFactory<Information, String>("cena"));


            this.getColumns().addAll(name, price);
            this.setMaxHeight(138);
        }

    }

    private static class ButtonBox extends HBox {
        private int countChips = 0;
        private boolean isPositive;
        private String znak = "";

        public ButtonBox(boolean isPositive, String znak) {
            this.znak = znak;
            this.isPositive = isPositive;
            this.setSpacing(10);
            Button Add = new Button("ADD");
            Button Remove = new Button("REMOVE");
            Label pocitadloLabel = new Label("0");
            this.getChildren().addAll(Add, Remove, pocitadloLabel);

            Add.setOnAction((
                    ActionEvent event) -> {
                try {
                    if (this.isPositive) {
                        price = price + 2;
                    } else {
                        price = price + 1;
                    }

                    chips.add(new Chip(playerTemplate.getColor(), this.znak, playerTemplate));
                    countChips++;
                    String string;
                    string = "" + countChips;
                    pocitadloLabel.setText(string);
                    pocitadloLabel.setTextFill(Color.RED);

                    btnOk.setText("BUY FOR " + price + " F");

                } catch (NumberFormatException e) {
                    System.out.println("chyba");
                }
            });

            Remove.setOnAction((
                    ActionEvent event) -> {
                try {
                    if(countChips > 0) {
                        if (this.isPositive) {
                            price = price - 2;
                        } else {
                            price = price - 1;
                        }
                        countChips--;
                        if (countChips == 0) {
                            pocitadloLabel.setTextFill(Color.BLACK);
                        }
                        String string;
                        string = "" + countChips;
                        pocitadloLabel.setText(string);

                        for(Chip chip : chips) {
                            if(chip.getZnak() == this.znak) {
                                chips.remove(chip);
                                break;
                            }
                        }
                        btnOk.setText("BUY FOR " + price + " F");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("chyba");
                }
            });
        }
    }
}