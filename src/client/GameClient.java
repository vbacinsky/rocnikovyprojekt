package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GameClient extends Application {
    private int akt_money = 10;
    private BorderPane border;
    private boolean isYourTurn;
    private ServerConnection serverConnection;
    private Color color;
    private Mapa map;
    private ArrayList<Policko> activeList = new ArrayList<>();
    private Map<String, PlayerTemplate> mapTemplates = new HashMap<>();
    private int act_position;
    private String nick;
    private PlayerTemplate downLeftPlayer;
    private boolean isChipPressed;
    private Label message = new Label("");
    boolean exist = false;


    private void newGameAction() {
        if (!exist) {
        StartDialog dialog = new StartDialog(this);
        this.serverConnection = dialog.showDialog();
        exist = true;
    }
    }

    private void exitGameAction() {
        Platform.exit();
    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setMinWidth(1850);
        primaryStage.setMinHeight(1050);
        primaryStage.setMaxHeight(1050);
        primaryStage.setMaxWidth(1850);
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        border = new BorderPane();
        border.setCenter(grid);
        MenuBar menuBar = createMenuBar();
        border.setTop(menuBar);
        border.setStyle("-fx-background-color: #e6bc55;");
        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slovania obchodnikmi");
        primaryStage.show();
    }

    public void createGame(boolean isYourTurn, StartInfoPlayer myInfo, StartInfoPlayer opponentInfo) {

        if(isYourTurn) this.message.setText("NOW IS YOUR TURN");
        else this.message.setText("WAIT FOR TURN");
        this.message.setFont(new Font(30));
        this.nick = myInfo.getNick();
        this.act_position = myInfo.getActPosition();
        this.isYourTurn = isYourTurn;
        HBox root = new HBox(50);
        root.setPadding(new Insets(60, 20, 20, 20));
        this.color = myInfo.getColor();

        PlayerTemplate upLeftPlayer = new PlayerTemplate(opponentInfo, this);
        this.downLeftPlayer = new PlayerTemplate(myInfo, this);

        mapTemplates.put(myInfo.getNick(), downLeftPlayer);
        mapTemplates.put(opponentInfo.getNick(), upLeftPlayer);


        //PlayerTemplate upRightPlayer = new PlayerTemplate("Player 2", true, null, null, "bla", "bla", null);
        //PlayerTemplate downRightPlayer = new PlayerTemplate("Player 3", true, null, null, "bla", "bla", null);

        VBox leftBox = createBox(upLeftPlayer, downLeftPlayer);
        this.map = new Mapa(this, myInfo, opponentInfo);
        //VBox rightBox = createBox(upRightPlayer, downRightPlayer);

        root.getChildren().addAll(leftBox, map);


        Platform.runLater(() -> {
            border.getChildren().add(root);
        });



    }

    public void vyber_policko(String[] serverResponseTokens) {
        for (int i = 2; i < serverResponseTokens.length; i++) {
            for (Policko policko : map.getPolickoList()) {
                if(policko.getID() == Integer.valueOf(serverResponseTokens[i])) {
                    policko.setColorBackround(Color.BLACK);
                    policko.setIsActive(true);
                    activeList.add(policko);
                }
            }
        }
    }

    public void change_position_opponent(String[] serverResponseTokens) {

        //prehladnejsie
        this.map.getPolickoByID().get(Integer.valueOf(serverResponseTokens[2])).setFigurku(findColor(serverResponseTokens[3]));

        this.map.getPolickoByID().get(Integer.valueOf(serverResponseTokens[4])).deleteFigurku();

        for(Policko pol : this.activeList) {
            pol.setNormalBackround();
            pol.setIsActive(false);
        }
        this.activeList.clear();
    }

    private Color findColor(String color) {
        Color c = null;
        switch(color) {
            case "BLUE":
                c = Color.BLUE;
                break;
            case "RED":
                c = Color.RED;
                break;
            case "GREEN":
                c = Color.GREEN;
                break;
            case "YELLOW":
                c = Color.YELLOW;
                break;
            default:
                break;
        }
        return c;
    }

    public void started_mission(String[] serverResponseTokens) {
        PlayerTemplate template = this.mapTemplates.get(serverResponseTokens[1]);
        template.setColorFrom();

    }



    public void new_mission(String[] serverResponseTokens) {

        PlayerTemplate template = this.mapTemplates.get(serverResponseTokens[1]);

        template.setCastleFrom(serverResponseTokens[2]);
        template.setCastleTo(serverResponseTokens[3]);
        if(this.nick.equals(serverResponseTokens[1])) {
            template.plusMoney(serverResponseTokens[4]);
        }

        template.setPoints(serverResponseTokens[4]);

        if("true".equals(serverResponseTokens[5])) template.setColorFrom();
        else template.setNormalColorFrom();


    }


    public void startMove(String nick) {
        this.serverConnection.sendMessage("START_MOVE " + nick);
    }



    public void vstupil_na_policko_s_cipom(Policko newActPolicko, String znak) {
        for(Policko pol : this.activeList) {
            pol.setNormalBackround();
            pol.setIsActive(false);
        }
        String message;
        message = "ENTERED_ON_CHIP " + this.nick + " " + znak + " " + newActPolicko.getID();
        this.serverConnection.sendMessage(message);
    }





    public void posunFigurkou(int id, Policko newActPolicko) {
        this.map.getAct_policko().deleteFigurku();
        this.map.setAct_policko(newActPolicko);
        this.act_position = id;
        for(Policko pol : this.activeList) {
            pol.setNormalBackround();
            pol.setIsActive(false);
        }
        String message;
        message = "CHANGE_POSITION " + this.nick + " " + id;
        this.serverConnection.sendMessage(message);


    }

    public void end_game(String[] serverResponseTokens) {
        System.out.println("Vitaz je " + serverResponseTokens[1]);

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("END GAME");
            alert.setHeaderText(null);
            alert.setContentText("Vitazom sa stava " + serverResponseTokens[1] + "! Dufame ze vas hra bavila");
            ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOK);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeOK) exitGameAction();
        });

        this.serverConnection.sendMessage("TERMINATE");

    }


    public void add_chip(String[] serverResponseTokens) {
        Policko policko = this.map.getPolickoByID().get(Integer.valueOf(serverResponseTokens[3]));
        policko.setChip(findColor(serverResponseTokens[1]), serverResponseTokens[2]);

    }

    public void someone_entered_on_chip(String[] serverResponseTokens) {
        boolean itIsMe = serverResponseTokens[1].equals(this.nick);
        Policko newPolicko = this.map.getPolickoByID().get(Integer.valueOf(serverResponseTokens[3]));


        if(!serverResponseTokens[2].equals("B")) {
            this.map.getPolickoByID().get(Integer.valueOf(serverResponseTokens[3])).setFigurku(findColor(serverResponseTokens[4]));
            this.map.getPolickoByID().get(Integer.valueOf(serverResponseTokens[5])).deleteFigurku();
            if(itIsMe) {
                this.map.getAct_policko().deleteFigurku();
                this.map.setAct_policko(newPolicko);
                this.act_position = newPolicko.getID();
            }
        }


        for(Policko pol : this.activeList) {
            pol.setNormalBackround();
            pol.setIsActive(false);
        }
        this.activeList.clear();


        this.map.getPolickoByID().get(Integer.valueOf(serverResponseTokens[3])).setNormalCircleColor();
        this.map.getPolickoByID().get(Integer.valueOf(serverResponseTokens[5])).setNormalCircleColor();


        if(serverResponseTokens[2].equals("Z")) this.mapTemplates.get(serverResponseTokens[1]).setNormalColorFrom();
    }




    public void setNewPlayerMove(String[] serverResponseTokens) {

        //pomenit to tak aby si pamatal len gameclient ze ci som na tahu alebo nie

        if(serverResponseTokens[1].equals(this.nick)) {
            this.setIsYourTurn(true);
            mapTemplates.get(this.nick).setIsYourTurn(true);
            Platform.runLater(() -> {
                this.message.setText("NOW IS YOUR TURN");
            });
        }
    }


    public void endMove() {
        this.serverConnection.sendMessage("END_MOVE");
    }



    public boolean getIsYourTurn() {
        return this.isYourTurn;
    }

    public void setIsYourTurn(boolean bool) {
        this.isYourTurn = bool;
    }


    private VBox createBox(PlayerTemplate playerUp, PlayerTemplate playerDown) {
        VBox box = new VBox(20);
        Rectangle rectangle = new Rectangle(320, 400);
        rectangle.setFill(Color.DARKORANGE);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);

        Rectangle rectangle2 = new Rectangle(320, 400);
        rectangle2.setFill(Color.DARKORANGE);
        rectangle2.setArcHeight(50);
        rectangle2.setArcWidth(50);


        box.getChildren().addAll(
                new StackPane(rectangle, playerUp),
                this.message,
                new StackPane(rectangle2, playerDown)
        );
        return box;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu mFile = new Menu("Game");
        MenuItem miFileNew = new MenuItem("New");
        miFileNew.setOnAction((ActionEvent event) -> newGameAction());
        MenuItem miFileExit = new MenuItem("Exit");
        miFileExit.setOnAction((ActionEvent event) -> exitGameAction());
        mFile.getItems().addAll(miFileNew, miFileExit);
        menuBar.getMenus().add(mFile);
        return menuBar;
    }

    public Color getColor() {
        return this.color;
    }

    public void setAct_position(int newPosition) {
        this.act_position = newPosition;
    }

    public boolean getIsChipPressed() {
        return this.downLeftPlayer.getisSomePressed();
    }

    public Chip getPressedChip() {
        return this.downLeftPlayer.getPressedChip();
    }

    public void removeChip(Chip chip) {
        this.downLeftPlayer.removeNegativeChip(chip);
    }

    public void put_new_chip(Color color, String znak, int id) {
        String stringColor = "";


        // TODO opravit jednoduchsie
        if(color == Color.BLUE) {
            stringColor = "BLUE";
        }
        if(color == Color.YELLOW) {
            stringColor = "YELLOW";
        }
        if(color == Color.RED) {
            stringColor = "RED";
        }
        if(color == Color.GREEN) {
            stringColor = "GREEN";
        }

        String string = "PUT_NEW_CHIP " + stringColor + " " + znak + " " + id;
        this.serverConnection.sendMessage(string);
    }


    public void setMessage(String string) {
        this.message.setText(string);
    }

}
