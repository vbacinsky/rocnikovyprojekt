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


public class StartDialog {

    private Stage dialogStage;

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

        /*
        while(true) {
            int length = Integer.parseInt(nick.getText());
            if(length > 0 && length < 13) break;
            nick.setAccessibleText("1-12 characters");
        }

        */

        Button btnOk = new Button("pripoj");
        central.getChildren().add(btnOk);
        central.setAlignment(Pos.CENTER);

        btnOk.setOnAction((ActionEvent event)-> {
            try {
                game.pripojSA(nick.getText());
                dialogStage.close();
                }
            catch (NumberFormatException e) {
                System.out.println("chyba");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void showDialog() {
        dialogStage.showAndWait();
    }





}
