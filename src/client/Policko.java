package client;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public interface Policko {

   public void setColorBackround(Color newColor);

   public int getID();

   public void setFigurku(Color color);

   public void setIsActive(boolean bool);

   public void deleteFigurku();

   public void setNormalBackround();

}
