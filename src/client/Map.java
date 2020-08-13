package client;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import server.Game;

import javax.swing.text.Position;

public class Map extends GridPane {
    private final int width = 1032;
    private final int height = 903;

    private ArrayList<Policko> polickoList = new ArrayList<>();

    private GameClient gameClient;
    public Map (GameClient gameClient) {
        this.gameClient = gameClient;

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("resources\\mapa.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.setWidth(width);
        this.setHeight(height);
       // GraphicsContext backround = this.getGraphicsContext2D();
        //backround.fillRect(50,400, width, height);


        int row = scanner.nextInt();
        int col = scanner.nextInt();

        int id_ciest = 15;
        int id_hradov = 0;
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
                        Cesta cesta = new Cesta(id_ciest);
                        this.add(cesta, j, i);
                        polickoList.add(cesta);
                        id_ciest++;
                        break;
                    case 4 :
                        Hrad hrad = new Hrad(id_hradov);
                        this.add(hrad, j,  i);
                        polickoList.add(hrad);
                        id_hradov++;
                        break;
                    case 5:
                        Hrad hrad1 = new Hrad(id_hradov);
                        this.add(hrad1, j,  i);
                        polickoList.add(hrad1);
                        id_hradov++;
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
