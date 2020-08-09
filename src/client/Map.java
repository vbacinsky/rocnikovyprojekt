package client;

import java.io.*;
import java.util.Scanner;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Map extends GridPane {
    private final int width = 1032;
    private final int height = 903;
    public Map () {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("resources\\mapa.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int row = scanner.nextInt();
        int col = scanner.nextInt();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int x = scanner.nextInt();
                switch (x) {
                    case 0:
                        this.add(new Rectangle(43, 43, Color.LIGHTGREEN), j, i);
                        break;
                    case 1:
                        this.add(new Rectangle(43, 43, Color.BLUE), j, i);
                        break;
                    case 2:
                        this.add(new Rectangle(43, 43, Color.RED), j, i);
                        break;
                    case 3:
                        this.add(new Cesta(), j, i);
                        break;
                    case 4:
                        this.add(new Rectangle(43, 43, Color.BROWN), j, i);
                        this.add(new Circle(21, Color.WHITE), j, i);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
