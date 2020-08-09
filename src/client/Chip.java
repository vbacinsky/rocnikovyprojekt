package client;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Chip extends StackPane {
    private Label label = new Label();
    private boolean isPositive;
    private boolean isPressed = false;
    private Circle circle;
    private Color color;
    private String znak;

    public Chip(boolean isPositive, Color color, String znak, PlayerTemplate playerTemplate) {
        this.color = color;
        this.znak = znak;
        this.isPositive = isPositive;
        this.circle = new Circle(15);
        circle.setFill(color);
        this.label.setText(this.znak);
        this.getChildren().addAll(this.circle, label);
        this.setOnMouseClicked((MouseEvent e) -> {
            if(!isPositive) {
                if(isPressed) {
                    label.setTextFill(Color.BLACK);
                    this.circle.setFill(this.color);
                    playerTemplate.setisSomePressed(false);
                    isPressed = false;
                } else if(!playerTemplate.getisSomePressed()) {
                    this.circle.setFill(Color.BLACK);
                    this.label.setTextFill(Color.WHITE);
                    playerTemplate.setisSomePressed(true);
                    this.isPressed = true;
                }
            }
        });
    }

    public boolean getIsPositive() {
        return this.isPositive;
    }

    public String getZnak() {
        return this.znak;
    }
}
