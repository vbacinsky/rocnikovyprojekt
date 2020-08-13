package client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Hrad extends StackPane implements Policko {
    private int id;
    private Color colorBackround = Color.RED;
    private Color color = Color.WHITE;


    public Hrad(int id) {
        this.id = id;
        Label label = new Label("" + id);
        this.getChildren().addAll(new Rectangle(43, 43, colorBackround), new Circle(20, color), label);
        this.setAlignment(Pos.CENTER);
    }







    @Override
    public void setColorBackround(Color newColor) {

    }
}
