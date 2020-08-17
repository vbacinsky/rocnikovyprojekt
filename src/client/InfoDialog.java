package client;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class InfoDialog {

    private TableView<Information> table = new TableView<>();
    private Stage dialogStage = new Stage();

    private final ObservableList<Information> data =
            FXCollections.observableArrayList(
                    new Information("Z - Zbojnici", "Olupia ta o naklad, misiu musis robit od znova."),
                    new Information("RC - Rozbita cesta", "Stojis jedno kolo"),
                    new Information("B - Bazina", "Hod kockou a o tolko sa vrat spat")
            );


    public InfoDialog() {
        Scene scene = new Scene(new Group());

        dialogStage.setTitle("Table");
        dialogStage.setWidth(500);
        dialogStage.setHeight(550);

        final Label label = new Label("Short info");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        table.setMaxHeight(300);


        TableColumn znacka = new TableColumn("Označenie");
        znacka.setMinWidth(150);
        znacka.setCellValueFactory(new PropertyValueFactory<Information, String>("oznacenie"));


        TableColumn info = new TableColumn("Info");
        info.setMinWidth(300);
        info.setCellValueFactory(new PropertyValueFactory<Information, String>("info"));


        table.setItems(data);
        table.getColumns().addAll(znacka, info);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.setAlignment(Pos.CENTER);

        Button btnOk = new Button("OK");
        vbox.getChildren().addAll(label, table, btnOk);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        btnOk.setOnAction((
                ActionEvent event) -> {
            try {
                dialogStage.close();
            } catch (NumberFormatException e) {
                System.out.println("chyba");
            }
        });
        dialogStage.setScene(scene);
    }

    public void showDialog() {
        dialogStage.showAndWait();
    }


    public static class Information {

        private final SimpleStringProperty oznacenie;
        private final SimpleStringProperty info;

        private Information (String oName, String vName) {
            this.oznacenie = new SimpleStringProperty(oName);
            this.info = new SimpleStringProperty(vName);
        }

        public String getOznacenie() {
            return oznacenie.get();
        }

        public void setOznacenie(String oName) {
            oznacenie.set(oName);
        }

        public String getInfo() {
            return info.get();
        }

        public void setInfo (String vName) {
            info.set(vName);
        }

    }

}



