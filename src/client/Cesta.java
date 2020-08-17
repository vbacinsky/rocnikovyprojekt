package client;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Cesta  extends StackPane implements Policko{
    private int id;
    private Color colorBackround = Color.GRAY;
    private Color color = Color.WHITE;
    private Label label = new Label("");
    private boolean hasChip = false;
    private Chip act_chip = null;
    private Circle figurka = new Circle();
    private Rectangle rectangle;
    private Circle circle;
    private boolean isActive;
    private GameClient gameClient;

    public Cesta(int id, GameClient gameClient) {
        this.gameClient = gameClient;
        this.isActive =false;
        this.id = id;
        label.setText("");
        this.circle = new Circle(20, color);
        this.rectangle = new Rectangle(43, 43, colorBackround);
        this.getChildren().addAll(this.rectangle , circle, label, figurka);
        this.setAlignment(Pos.CENTER);
        this.setOnMouseClicked((MouseEvent e) -> {
            if(gameClient.getIsYourTurn()) {
                if (this.isActive) {
                    this.rectangle.setFill(colorBackround);

                    if(this.circle.getFill() == Color.WHITE || this.circle.getFill() == this.gameClient.getColor()) {
                        setFigurku(gameClient.getColor());
                        gameClient.posunFigurkou(this.id, this);
                    } else {
                        this.hasChip = false;
                        gameClient.vstupil_na_policko_s_cipom(this, this.label.getText());
                    }
                }
                if (gameClient.getIsChipPressed() && !this.hasChip) {
                    act_chip = gameClient.getPressedChip();
                    gameClient.removeChip(act_chip);
                    gameClient.put_new_chip(act_chip.getColor(), act_chip.getZnak(), this.id);
                }
            }
        });
    }

    @Override
    public void setChip(Color color, String znak) {
        Platform.runLater(() -> {
            this.hasChip = true;
            this.circle.setFill(color);
            this.label.setText(znak);
        });
    }

    @Override
    public void setColorBackround(Color newColor) {
        this.rectangle.setFill(newColor);
    }


    @Override
    public void setNormalBackround() {
        this.rectangle.setFill(this.colorBackround);
    }



    @Override
    public void setIsActive(boolean bool) {
        this.isActive = bool;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void setFigurku(Color color) {
        this.figurka.setRadius(10);
        this.figurka.setFill(color);
    }

    @Override
    public void deleteFigurku() {
        this.figurka.setFill(null);
    }

    public void setColor(Color newColor) {
        this.color = newColor;
    }

    public void setLabel(String string) {
        this.label.setText(string);
    }

    public void setNormalCircleColor() {
        Platform.runLater(() -> {
            this.circle.setFill(color);
            this.label.setText("");
        });
    }
}
