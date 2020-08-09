package client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Cesta extends StackPane {
    private Color colorBackground = Color.GRAY;
    private Color color = Color.WHITE;
    private Label label = new Label("");

    public Cesta() {
        this.getChildren().addAll(new Rectangle(43, 43, colorBackground), new Circle(20, color), label);
        this.setAlignment(Pos.CENTER);
    }

    public void setColorBackground(Color newColor) {
        this.colorBackground = newColor;
    }

    public void setColor(Color newColor) {
        this.color = newColor;
    }

    public void setLabel(String string) {
        this.label.setText(string);
    }
}
