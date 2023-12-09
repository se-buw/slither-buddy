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
        gc.fillRect(pos_x * segmentSize_, pos_y * segmentSize_, segmentSize_, segmentSize_);
    };

    boolean collision_barrier(int head_x, int head_y){
        if (pos_x == head_x && pos_y == head_y) {
            return true;
        }
        return false;
    }

    private Color color = Color.GREEN;
    private int segmentSize_ = 30;
    private int pos_x;
    private int pos_y;
    private String name_;

}