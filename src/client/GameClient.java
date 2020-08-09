package client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameClient extends Application {
    private int akt_money = 10;
    private BorderPane border;

    private void newGameAction() {
        StartDialog dialog = new StartDialog(this);
        dialog.showDialog();
    }

    private void exitGameAction() {
        Platform.exit();
    }


    public static void main(String[] args) {
       // Client client = new Client("localhost",22223);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //TODO: rozdlit do funkcii .. createMenuBar, ...
        primaryStage.setWidth(1850);
        primaryStage.setHeight(1050);
        primaryStage.setMinWidth(1850);
        primaryStage.setMinHeight(1050);
        primaryStage.setMaxHeight(1050);
        primaryStage.setMaxWidth(1850);
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(2);
        border = new BorderPane();
        border.setCenter(grid);
        MenuBar menuBar = new MenuBar();
        border.setTop(menuBar);
        border.setStyle("-fx-background-color: #e6bc55;");
        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Slovania obchodnikmi");
        primaryStage.show();

        Menu mFile = new Menu("Game");
        MenuItem miFileNew = new MenuItem("New");
        MenuItem miFileExit = new MenuItem("Exit");
        mFile.getItems().addAll(miFileNew, miFileExit);
        menuBar.getMenus().add(mFile);

        miFileNew.setOnAction((ActionEvent event) -> newGameAction());

        miFileExit.setOnAction((ActionEvent event) -> exitGameAction());
    }

    public void createGame(String nick) {
        HBox root = new HBox(50);
        root.setPadding(new Insets(60, 20, 20, 20));

        //down
        Image image = null;
        try {
            image = new Image(new FileInputStream("resources/shrek.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PlayerTemplate upLeftPlayer = new PlayerTemplate("Player 1", true, null);
        PlayerTemplate downLeftPlayer = new PlayerTemplate(nick, false, image);
        PlayerTemplate upRightPlayer = new PlayerTemplate("Player 2", true, null);
        PlayerTemplate downRightPlayer = new PlayerTemplate("Player 3", true, null);

        VBox leftBox = createBox(upLeftPlayer, downLeftPlayer);
        Map map = new Map();
        VBox rightBox = createBox(upRightPlayer, downRightPlayer);

        root.getChildren().addAll(leftBox, map, rightBox);
        border.getChildren().add(root);
    }

    private VBox createBox(PlayerTemplate playerUp, PlayerTemplate playerDown) {
        VBox box = new VBox(100);
        Rectangle rectangle = new Rectangle(320, 400);
        rectangle.setFill(Color.DARKORANGE);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);

        box.getChildren().addAll(
                new StackPane(rectangle, playerUp),
                new StackPane(rectangle, playerDown)
        );

        return box;
    }

    public static Color getColor() {
        return Color.LIGHTBLUE;
    }

}
