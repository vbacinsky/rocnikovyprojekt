package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import org.w3c.dom.css.Rect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameClient extends Application {
    private int akt_money = 10;
    private BorderPane border;
    private boolean isYourTurn;
    ServerConnection serverConnection;


    private void newGameAction() {
        StartDialog dialog = new StartDialog(this);
        this.serverConnection = dialog.showDialog();
    }

    private void exitGameAction() {
        Platform.exit();
    }


    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //TODO: rozdlit do funkcii .. createMenuBar, ...
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

    public void createGame(String nick, String color, String from, String to, String Stringimage, boolean isYourTurn) {
        this.isYourTurn = isYourTurn;
        HBox root = new HBox(50);
        root.setPadding(new Insets(60, 20, 20, 20));
        Image image = null;
        try {
            image = new Image(new FileInputStream("resources/" + Stringimage + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PlayerTemplate upLeftPlayer = new PlayerTemplate("Player 1", true, null, "black", "bla", "bla", null);
        PlayerTemplate downLeftPlayer = new PlayerTemplate(nick, false, image, color, from, to, this);
        PlayerTemplate upRightPlayer = new PlayerTemplate("Player 2", true, null, "black", "bla", "bla", null);
        PlayerTemplate downRightPlayer = new PlayerTemplate("Player 3", true, null, "black", "bla", "bla", null);

        VBox leftBox = createBox(upLeftPlayer, downLeftPlayer);
        Map map = new Map(this);
        VBox rightBox = createBox(upRightPlayer, downRightPlayer);


        root.getChildren().addAll(leftBox, map, rightBox);
        Platform.runLater(() -> {
            border.getChildren().add(root);
        });


    }

    private boolean getIsYourTurn() {
        return this.isYourTurn;
    }


    private VBox createBox(PlayerTemplate playerUp, PlayerTemplate playerDown) {
        VBox box = new VBox(100);
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

}
