package client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import server.Game;

public class Hrad extends StackPane implements Policko {
    private int id;
    private Color colorBackround = Color.BROWN;
    private Color color = Color.WHITE;
    private Circle figurka = new Circle();
    private Rectangle rectangle;
    private boolean isActive;
    private GameClient gameClient;


    public Hrad(int id, GameClient gameClient) {
        this.gameClient = gameClient;
        this.isActive = false;
        this.id = id;
        Label label = new Label("");
        rectangle = new Rectangle(43, 43, this.colorBackround);
        this.getChildren().addAll(rectangle, new Circle(20, color), label, figurka);
        this.setAlignment(Pos.CENTER);
        this.setOnMouseClicked((MouseEvent e) -> {
            if(gameClient.getIsYourTurn()) {
                if (this.isActive) {
                    this.rectangle.setFill(colorBackround);
                    gameClient.posunFigurkou(this.id, this);
                    setFigurku(gameClient.getColor());

                    this.isActive = false;
                }
            }
        });
    }


    @Override
    public int getID() {
        return this.id;
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
    public void setChip(Color color, String znak) {

    }

    @Override
    public void setIsActive(boolean bool) {
        this.isActive = bool;
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

    public void setNormalCircleColor() {

    }
}
