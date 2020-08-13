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

public class PlayerTemplate extends VBox {


    private boolean isSomePressed = false;
    private ArrayList<Chip> positiveChips = new ArrayList<>();
    private ArrayList<Chip> negativeChips = new ArrayList<>();
    private Text money;
    private Color color;
    private GridPane gridPaneP = new GridPane();
    private GridPane gridPaneN = new GridPane();
    private GameClient gameClient;

    public PlayerTemplate(String playerNick, boolean isOpponent, Image image, String color, String from, String to, GameClient gameClient) {

        this.gameClient = gameClient;

        switch(color) {
            case "BLUE":
                this.color = Color.LIGHTBLUE;
                break;
            case "RED":
                this.color = Color.RED;
                break;
            case "GREEN":
                this.color = Color.GREEN;
                break;
            case "YELLOW":
                this.color = Color.YELLOW;
                break;
             default:
                 break;
        }
        this.setSpacing(10);

        HBox image_nick = new HBox(40);

        VBox nick_money = new VBox(50);
        nick_money.setPadding(new Insets(10, 10, 10, 10));

        nick_money.setAlignment(Pos.CENTER);
        Text nick = new Text(playerNick);
        nick.setFont(new Font(20));
        this.money = new Text("10");
        money.setFont(new Font(30));


        nick_money.getChildren().addAll(nick, money);

        Circle circle = new Circle();
        circle.setRadius(10);
        circle.setFill(this.color);

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
        Text castleA = new Text(from);
        Text fromText = new Text("FROM: ");
        fromText.setFill(Color.WHITE);
        fromBox.getChildren().addAll(fromText, castleA);


        HBox toBox = new HBox(10);
        Text castleB = new Text(to);
        Text toText = new Text("TO: ");
        toText.setFill(Color.WHITE);
        toBox.getChildren().addAll(toText, castleB);

        this.getChildren().addAll(image_nick, positiveChips, negativeChips, fromBox, toBox);


        if(!isOpponent) {
            HBox buttonBox = new HBox(10);
            Button end = new Button("END");
            Button buy = new Button("BUY");
            Button showRules = new Button("RUL");
            Button showMission = new Button("M");
            Button hod_kockou = new Button("Start move");



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
            buttonBox.getChildren().addAll(end, buy, showMission, showRules, hod_kockou);
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
