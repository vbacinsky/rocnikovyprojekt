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

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;


public class StartDialog {

    private Stage dialogStage;
    private ServerConnection serverConnection;

    public StartDialog(GameClient game) {
        dialogStage = new Stage();

        BorderPane border = new BorderPane();
        border.setStyle("-fx-background-color: #e184e6;");
        Scene scene = new Scene(border);
        dialogStage.setScene(scene);
        dialogStage.setHeight(300);
        dialogStage.setWidth(1000);
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
        AtomicBoolean can = new AtomicBoolean(false);
        btnOk.setOnAction((ActionEvent event) -> {
            try {

                lblnick.setText("CAKAJ NA DALSICH HRACOV. AK SA HRACI \nPRIPOJA, HRA SA AUTOMATICKY SPUSTI!");
                btnOk.setText("OK");
                central.getChildren().remove(nick);
                if(!can.get()) this.serverConnection = new ServerConnection("localhost",22222, game, nick.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(can.get()) dialogStage.close();
            can.set(true);
        });

    }

    public ServerConnection showDialog() {
        dialogStage.showAndWait();
        return this.serverConnection;
    }





}
