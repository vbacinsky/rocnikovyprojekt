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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameClient extends Application {
    private int akt_money = 10;
    private Stage primaryStage;
    private Menu mFile;
    private MenuItem miFileNew;
    private MenuItem miFileExit;
    private GridPane grid;
    private BorderPane border;
    private Color color = Color.LIGHTBLUE;



    private boolean newAction() {
        StartDialog dialog = new StartDialog(this);
        dialog.showDialog();
        return true;
    }

    private boolean exitAction() {
        Platform.exit();
        return true;
    }


    public static void main(String[] args) {
       // Client client = new Client("localhost",22223);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setWidth(1850);
        primaryStage.setHeight(1050);
        primaryStage.setMinWidth(1850);
        primaryStage.setMinHeight(1050);
        primaryStage.setMaxHeight(1050);
        primaryStage.setMaxWidth(1850);
        grid = new GridPane();
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

        mFile = new Menu("Game");
        miFileNew = new MenuItem("New");
        miFileExit = new MenuItem("Exit");
        mFile.getItems().addAll(miFileNew, miFileExit);
        menuBar.getMenus().add(mFile);


        miFileNew.setOnAction((ActionEvent event) -> {
            newAction();
        });

        miFileExit.setOnAction((ActionEvent event) -> {
            exitAction();
        });
    }

    public void pripojSA(String nick) throws IOException {
        HBox root = new HBox(50);
        root.setPadding(new Insets(60, 20, 20, 20));

        //LEFT
        VBox leftBox = new VBox(100);

        //up
        Template upBox = new Template("Player 1", true, null, this);

        //down
        Image image = null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\volod\\Desktop\\shrek.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Template myBox = new Template(nick, false, image, this);

        leftBox.getChildren().addAll(new StackPane(makeBackround(), upBox), new StackPane(makeBackround(), myBox));


        //Mid
        Map map = new Map();

        //RIGHT
        VBox rightBox = new VBox(100);


        //up
        Template upBoxRight = new Template("Player 2", true, null, this);

        //down
        Template downBoxRight = new Template("Player 3", true, null, this);

        rightBox.getChildren().addAll(new StackPane(makeBackround(), upBoxRight), new StackPane(makeBackround(), downBoxRight));


        root.getChildren().addAll(leftBox, map, rightBox);
        border.getChildren().add(root);



    }

    private Rectangle makeBackround() {
        Rectangle rectangle = new Rectangle(320, 400);
        rectangle.setFill(Color.DARKORANGE);
        rectangle.setArcHeight(50);
        rectangle.setArcWidth(50);

        return rectangle;
    }

    public Color getColor() {
        return this.color;
    }

}
