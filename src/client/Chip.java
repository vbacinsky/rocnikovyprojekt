package client;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Chip extends StackPane {
    private Label label = new Label();
    private boolean isPressed = false;
    private Circle circle;
    private Color color;
    private String znak;

    public Chip(Color color, String znak, PlayerTemplate playerTemplate) {
        this.color = color;
        this.znak = znak;
        this.circle = new Circle(15);
        circle.setFill(color);
        this.label.setText(this.znak);
        this.getChildren().addAll(this.circle, label);
        this.setOnMouseClicked((MouseEvent e) -> {
            if(playerTemplate.getIsYourTurn()) {
                if (isPressed) {
                    label.setTextFill(Color.BLACK);
                    this.circle.setFill(this.color);
                    playerTemplate.setisSomePressed(false);
                    isPressed = false;
                } else if (!playerTemplate.getisSomePressed()) {
                    this.circle.setFill(Color.BLACK);
                    this.label.setTextFill(Color.WHITE);
                    playerTemplate.setisSomePressed(true);
                    this.isPressed = true;
                    playerTemplate.setPressedChip(this);
                }
            }
        });
    }

    public String getZnak() {
        return this.znak;
    }

    public Color getColor() {
        return this.color;
    }
}
