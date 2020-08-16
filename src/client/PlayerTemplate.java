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
    private Text castleFrom;
    private Text castleTo;
    private Text points;
    private Chip pressedChip;
    private boolean isYourTurn;

    public PlayerTemplate(StartInfoPlayer info, GameClient gameClient) {

        this.isYourTurn = gameClient.getIsYourTurn();

        this.color = info.getColor();
        this.gameClient = gameClient;

        this.setSpacing(10);

        HBox image_nick = new HBox(40);

        VBox nick_money = new VBox(20);
        nick_money.setPadding(new Insets(0, 10, 10, 10));

        nick_money.setAlignment(Pos.CENTER);
        this.points = new Text("0 POINTS");
        points.setFont(new Font(20));
        Text nick = new Text(info.getNick());
        nick.setFont(new Font(20));
        this.money = new Text("10 F");
        money.setFont(new Font(30));


        nick_money.getChildren().addAll(nick, points, money);

        Circle circle = new Circle();
        circle.setRadius(10);
        circle.setFill(this.color);

        if(info.getImage() == null) {
            Rectangle rec = new Rectangle(110,185);
            rec.setFill(Color.WHITE);
            image_nick.getChildren().addAll(rec, nick_money, circle);
        } else {
            final ImageView selectedImage = new ImageView();
            selectedImage.setImage(info.getImage());
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
        this.castleFrom = new Text(info.getFrom());
        this.castleFrom.setFill(Color.RED);
        Text fromText = new Text("FROM: ");
        fromText.setFill(Color.WHITE);
        fromBox.getChildren().addAll(fromText, castleFrom);


        HBox toBox = new HBox(10);
        this.castleTo = new Text(info.getTo());
        Text toText = new Text("TO: ");
        toText.setFill(Color.WHITE);
        toBox.getChildren().addAll(toText, castleTo);

        this.getChildren().addAll(image_nick, positiveChips, negativeChips, fromBox, toBox);


        if(!info.getIsOpponent()) {
            HBox buttonBox = new HBox(10);
            Button end = new Button("END");
            Button buy = new Button("BUY");
            Button showRules = new Button("RUL");
            Button showMission = new Button("M");
            Button hod_kockou = new Button("START");


            end.setOnAction((
                    ActionEvent) -> {
                try {
                    if(isYourTurn) {
                        this.isSomePressed = false;
                        this.isYourTurn = false;
                        this.gameClient.setIsYourTurn(false);
                        this.gameClient.endMove();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("chyba");
                }
            });

            hod_kockou.setOnAction((
                    ActionEvent) -> {
                try {
                    if(isYourTurn) this.gameClient.startMove(info.getNick());
                } catch (NumberFormatException e) {
                    System.out.println("chyba");
                }
            });

            buy.setOnAction((
                    ActionEvent) -> {
                try {
                    if(isYourTurn) {
                        BuyDialog buyDialog = new BuyDialog(this);
                        buyDialog.showDialog();
                    }
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

    public void plusMoney(String plusMoney) {
        String[] x = this.money.getText().split(" ");
        this.money.setText(Integer.valueOf(x[0]) + Integer.valueOf(plusMoney) + " F");
    }

    public int getMoney() {
        String[] x = this.money.getText().split(" ");
        return Integer.valueOf(x[0]);
    }

    public void setPoints(String plusPoints){
        String[] x = this.points.getText().split(" ");
        this.points.setText("" + Integer.valueOf(x[0] + Integer.valueOf(plusPoints)) + " POINTS");
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

    public void setCastleFrom(String newFrom) {
        this.castleFrom.setText(newFrom);
    }

    public void setColorFrom() {
        this.castleFrom.setFill(Color.RED);
    }

    public void setNormalColorFrom() {
        this.castleFrom.setFill(Color.BLACK);
    }

    public void setCastleTo(String newTo) {
        this.castleTo.setText(newTo);
    }

    public void setPressedChip(Chip pressedChip) {
        this.pressedChip = pressedChip;
    }

    public Chip getPressedChip() {
        return this.pressedChip;
    }

    public void removeNegativeChip(Chip chip) {
        this.negativeChips.remove(chip);
        this.gridPaneN.getChildren().remove(chip);
        this.isSomePressed = false;
    }

    public boolean getIsYourTurn() {
        return this.isYourTurn;
    }

    public void setIsYourTurn(boolean bool) {
        this.isYourTurn = bool;
    }
}
