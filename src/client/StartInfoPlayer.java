package client;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class StartInfoPlayer {
    private String nick;
    private Color color;
    private String to;
    private String from;
    private int act_position;
    private Image image;
    private boolean isOpponent;

    public StartInfoPlayer(String nick, String color, String from, String to, String character, String act_position, boolean isOpponent) {
        this.nick = nick;
        findColor(color);
        findCharacter(character);
        this.from = from;
        this.to = to;
        this.act_position = Integer.valueOf(act_position);
        this.isOpponent = isOpponent;
    }

    private void findColor(String color) {
        switch(color) {
            case "BLUE":
                this.color = Color.BLUE;
                break;
            case "RED":
                this.color = Color.RED;
                break;
            case "GREEN":
                this.color = Color.GREEN;
                break;
            case "YELLOW":
                this.color = Color.YELLOW;
                break;
            default:
                break;
        }
    }

    private void findCharacter(String character) {
        Image image = null;
        try {
            image = new Image(new FileInputStream("resources\\" + character + ".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.image = image;
    }


    public String getFrom() {
        return from;
    }

    public String getTo(){
        return to;
    }


    public int getActPosition() {
        return this.act_position;
    }

    public String getNick() {
        return this.nick;
    }

    public boolean getIsOpponent() {
        return this.isOpponent;
    }

    public Color getColor() {
        return this.color;
    }

    public Image getImage() {
        return this.image;
    }



}
