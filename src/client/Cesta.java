package client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class Cesta extends StackPane implements Policko{
    private int id;
    private Color colorBackround = Color.GRAY;
    private Color color = Color.WHITE;
    private Label label = new Label("");
    private boolean hasChip = false;
    private Chip act_chip = null;

    public Cesta(int id) {
        this.id = id;
        label.setText(""+id);
        this.getChildren().addAll(new Rectangle(43, 43, colorBackround), new Circle(20, color), label);
        this.setAlignment(Pos.CENTER);
    }

    public void setColorBackround(Color newColor) {
        this.colorBackround = newColor;
    }

    public void setColor(Color newColor) {
        this.color = newColor;
    }

    public void setLabel(String string) {
        this.label.setText(string);
    }
}
