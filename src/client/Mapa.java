package client;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Mapa extends GridPane {
    private final int width = 1032;
    private final int height = 903;

    private ArrayList<Policko> polickoList = new ArrayList<>();
    private Map<Integer, Policko> polickoByID = new HashMap<>();
    private Policko act_policko;

    private GameClient gameClient;
    public Mapa(GameClient gameClient, StartInfoPlayer myInfo, StartInfoPlayer opponentInfo) {
        this.gameClient = gameClient;

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("resources/mapa.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.setWidth(width);
        this.setHeight(height);


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
                        this.add(new Rectangle(43, 43, Color.BROWN), j, i);
                        break;
                    case 3:
                        Cesta cesta = new Cesta(id_ciest, gameClient);
                        this.add(cesta, j, i);
                        polickoList.add(cesta);
                        polickoByID.put(id_ciest, cesta);
                        id_ciest++;
                        break;
                    case 4 :
                        Hrad hrad = new Hrad(id_hradov, gameClient);
                        this.add(hrad, j,  i);
                        polickoList.add(hrad);
                        polickoByID.put(id_hradov, hrad);
                        id_hradov++;
                        break;
                    case 5:
                        Hrad hrad1 = new Hrad(id_hradov, gameClient);
                        this.add(hrad1, j,  i);
                        polickoList.add(hrad1);
                        polickoByID.put(id_hradov, hrad1);
                        id_hradov++;
                        break;
                    default:
                        break;
                }
            }
        }

        makeNameCastle("HAN", "IGOVS", "KY", 15, 1, 4);
        makeNameCastle("LUB", "OVNIA", "NSKY", 14, 2, 12);
        makeNameCastle("KAM", "ENIC", "KY", 18, 1, 20);
        makeNameCastle("KA", "PUSA", "NY", 17, 5, 1);
        makeNameCastle("K", "OSIC", "E", 19, 4, 11);
        makeNameCastle("TR", "ENCI", "N", 20, 6, 20);
        makeNameCastle("T", "RNAV", "A", 16, 9, 17);
        makeNameCastle("S", "ASOV", "", 16, 10, 1);
        makeNameCastle("ST", "RECN", "O", 17, 11, 11);
        makeNameCastle("BAR", "DEJO", "V", 18, 13, 6);
        makeNameCastle("BRA", "TISLA" ,"VA", 17, 14, 16);
        makeNameCastle("S", "ARIS", "", 20, 18, 1);
        makeNameCastle("SP", "ISSK", "Y", 20, 18, 14);
        makeNameCastle("BL", "ATNI", "CA", 19, 19, 20);
        makeNameCastle("FI", "LAKO", "VO", 17, 16, 1);



        for(Policko x : polickoList) {
            //kludne cez map
            if(x.getID() == myInfo.getActPosition()) {
                x.setFigurku(myInfo.getColor());
                this.act_policko = x;
            }

            if(x.getID() == opponentInfo.getActPosition()) {
                x.setFigurku(opponentInfo.getColor());
            }
        }
    }

    private void makeNameCastle(String a, String b, String c, int size, int row, int col) {

        //skratit
        Text first = new Text(a);
        first.setFont(new Font(size));
        first.setUnderline(true);
        GridPane.setHalignment(first, HPos.RIGHT);
        this.add(first, col, row);
        Text second = new Text(b);
        second.setFont(new Font(size));
        second.setUnderline(true);
        GridPane.setHalignment(second, HPos.CENTER);
        this.add(second, col+1, row);
        Text third = new Text(c);
        third.setFont(new Font(size));
        third.setUnderline(true);
        GridPane.setHalignment(third, HPos.LEFT);
        this.add(third, col + 2, row);
    }

    public Policko getAct_policko() {
        return this.act_policko;
    }

    public void setAct_policko(Policko policko) {
        this.act_policko = policko;
    }


    public ArrayList<Policko> getPolickoList() {
        return this.polickoList;
    }

    public Map<Integer, Policko> getPolickoByID() {
        return this.polickoByID;
    }
}
