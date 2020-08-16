package client;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import server.Server;

import java.io.IOException;


public class StartDialog {

    private Stage dialogStage;
    private ServerConnection serverConnection;

    public StartDialog(GameClient game) {
        dialogStage = new Stage();

        BorderPane border = new BorderPane();
        border.setStyle("-fx-background-color: #e184e6;");
        Scene scene = new Scene(border);
        dialogStage.setScene(scene);
        VBox central = new VBox();
        central.setPadding(new Insets(20, 20, 20, 20));
        central.setSpacing(30);

        border.setCenter(central);

        Label lblnick = new Label("Enter your nickname, max 12 characters");
        lblnick.setFont(new Font("Cambria", 32));
        central.getChildren().add(lblnick);

        TextField nick = new TextField();
        central.getChildren().add(nick);

        Button btnOk = new Button("pripoj");
        central.getChildren().add(btnOk);
        central.setAlignment(Pos.CENTER);

        btnOk.setOnAction((ActionEvent event) -> {
            try {
                this.serverConnection = new ServerConnection("localhost",22222, game, nick.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            dialogStage.close();
        });

    }

    public ServerConnection showDialog() {
        dialogStage.showAndWait();
        return this.serverConnection;
    }





}
