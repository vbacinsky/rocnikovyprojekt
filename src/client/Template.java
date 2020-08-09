package client;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;

public class Template extends VBox {


    private boolean isSomePressed = false;
    private ArrayList<Chip> positiveChips = new ArrayList<>();
    private ArrayList<Chip> negativeChips = new ArrayList<>();
    private Text money;
    private Color color;
    private GridPane gridPaneP = new GridPane();
    private GridPane gridPaneN = new GridPane();

    public Template (String nick_of_player, boolean isOpponent, Image image, GameClient client) {
        this.color = client.getColor();
        this.setSpacing(10);

        HBox image_nick = new HBox(40);

        VBox nick_money = new VBox(50);
        nick_money.setPadding(new Insets(10, 10, 10, 10));

        nick_money.setAlignment(Pos.CENTER);
        Text nick = new Text(nick_of_player);
        nick.setFont(new Font(20));
        this.money = new Text("10");
        money.setFont(new Font(30));


        nick_money.getChildren().addAll(nick, money);

        Circle circle = new Circle();
        circle.setRadius(10);
        circle.setFill(Color.WHITE);

        if(image == null) {
            Rectangle rec = new Rectangle(110,185);
            rec.setFill(Color.WHITE);
            image_nick.getChildren().addAll(rec, nick_money, circle);
        } else {
            final ImageView selectedImage = new ImageView();
            selectedImage.setImage(image);
            selectedImage.setFitHeight(185);
            selectedImage.setFitWidth(110);
            image_nick.getChildren().addAll(selectedImage, nick_money, circle);
        }

        HBox positiveChips = new HBox(5);
        //tu sa potom bude ukladat postupne kazdy jeden chip

        positiveChips.getChildren().addAll(new Text("positive chips: "), this.gridPaneP);

        HBox negativeChips = new HBox(5);
        //tu sa potom bude ukladat postupne kazdy jeden chip
        negativeChips.getChildren().addAll(new Text("negative chips: "), this.gridPaneN);


        HBox fromBox = new HBox(10);
        Text castleA = new Text("CASTLE A");
        Text from = new Text("FROM: ");
        from.setFill(Color.WHITE);
        fromBox.getChildren().addAll(from, castleA);


        HBox toBox = new HBox(10);
        Text castleB = new Text("CASTLE B");
        Text to = new Text("TO: ");
        to.setFill(Color.WHITE);
        toBox.getChildren().addAll(to, castleB);

        this.getChildren().addAll(image_nick, positiveChips, negativeChips, fromBox, toBox);


        if(isOpponent == false) {
            HBox buttonBox = new HBox(10);
            Button end = new Button("END");
            Button buy = new Button("BUY");
            Button showRules = new Button("RUL");
            Button showMission = new Button("M");


            buy.setOnAction((
                    ActionEvent) -> {
                try {
                    BuyDialog buyDialog = new BuyDialog(this);
                    buyDialog.showDialog();
                } catch (NumberFormatException e) {
                    System.out.println("chyba");
                }
            });



            showRules.setOnAction((
                    ActionEvent event) -> {
                try {
                    InfoDialog dialog = new InfoDialog();
                    dialog.showDialog();
                } catch (NumberFormatException e) {
                    System.out.println("chyba");
                }
            });
            buttonBox.getChildren().addAll(end, buy, showMission, showRules);
            buttonBox.setAlignment(Pos.BOTTOM_CENTER);
            this.getChildren().add(buttonBox);
        }
    }

    public void setMoney(String string){
        this.money.setText(string);
    }

    public int getMoney() {
        return Integer.valueOf(this.money.getText());
    }

    public void addPositiveChips(Chip positiveChip) {
        this.positiveChips.add(positiveChip);
        gridPaneP.addRow(0, positiveChip);
    }

    public void addNegativeChips (Chip negativeChip) {
        this.negativeChips.add(negativeChip);
        gridPaneN.addRow(0, negativeChip);
    }

    public Color getColor() {
        return this.color;
    }

    public boolean getisSomePressed() {
        return isSomePressed;
    }

    public void setisSomePressed(boolean bool) {
        this.isSomePressed = bool;
    }
}
