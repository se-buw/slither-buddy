package de.buw.i2p;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;
import java.awt.Point;
import java.util.ArrayList;

public class Barrier {//new barrierclass

    private Color color = Color.GREENYELLOW;
    private int segmentSize;
    private int pos_x;
    private int pos_y;
//position of left upper corner

    public Barrier(int segmentSize){
        this.segmentSize = segmentSize;
    }
    void place_barrier(){
        Random random = new Random();

        int x = random.nextInt(20);//random value between 0 and 19
        int y = random.nextInt(20);
        //prevents barriers for beeing set on a field where one of the snakes starts
        if ((x == 5 || x == 15) && y <= 15 && y >= 9){
            x =+ 1;
        }

        pos_x = x;
        pos_y = y;
    }
//draws the Barrier on the barriercanvas, with given values * segmentsize
    void draw_barrier(GraphicsContext gc){
        gc.setFill(color);
        gc.fillRect(pos_x * segmentSize, pos_y * segmentSize, segmentSize, segmentSize);
    };

    boolean collision_barrier(int head_x, int head_y){
        return (pos_x == head_x && pos_y == head_y);
    }
}