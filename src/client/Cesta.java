package client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import server.Game;


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
        label.setText(""+id);
        this.circle = new Circle(20, color);
        this.rectangle = new Rectangle(43, 43, colorBackround);
        this.getChildren().addAll(this.rectangle , circle, label, figurka);
        this.setAlignment(Pos.CENTER);
        this.setOnMouseClicked((MouseEvent e) -> {
            if(gameClient.getIsYourTurn()) {
                if (this.isActive) {
                    this.rectangle.setFill(colorBackround);
                    setFigurku(gameClient.getColor());
                    gameClient.posunFigurkou(this.id, this);
                    // potom poslat na server request kde som sa posunul
                }
                if (gameClient.getIsChipPressed() && !this.hasChip) {
                    act_chip = gameClient.getPressedChip();
                    this.circle.setFill(act_chip.getColor());
                    this.label.setText(act_chip.getZnak());
                    this.hasChip = true;
                    gameClient.removeChip(act_chip);
                }
            }
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
}
