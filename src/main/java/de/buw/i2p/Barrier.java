package de.buw.i2p;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;

public class Barrier {

    public Barrier(String name){
        name_ = name;
    }
    void place_barrier(){
        Random random = new Random();

        int x = random.nextInt(19);
        int y = random.nextInt(19);

        if (x == 5 || x == 15 && y <= 15 && y >= 9){
            x =+ 1;
        }

        pos_x = x;
        pos_y = y;
    }

    void draw_barrier(GraphicsContext gc){
        gc.setFill(Color.GREENYELLOW);
        gc.fillRect(pos_x * 20, pos_y * 20, 20, 20);

        gc.setFill(Color.GREEN);
        gc.fillOval(pos_x * 20 + 10, pos_y * 20 +10, 3, 3);
    };

    boolean collision_barrier(int head_x, int head_y){
        if ((pos_x * segmentSize_) == (head_x * segmentSize_) && (pos_y * segmentSize_) == (head_y * segmentSize_)){
            System.out.println(name_ + ":" + pos_x + ";" + pos_y + "/n");
            System.out.println("Schlange:" + ":" + head_x + ";" + head_y + "/n");
            return true;
        } else {
            return false;
        }
    }

    private Color color = Color.GREEN;
    private int segmentSize_;
    private int pos_x;
    private int pos_y;
    private String name_;

}