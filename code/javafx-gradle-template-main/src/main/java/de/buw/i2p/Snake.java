package de.buw.i2p;
import java.awt.Point;
import java.util.ArrayList;

public class Snake {
    private int speed_;
    private int xdirection_;
    private int ydirection_;
    private ArrayList<Point> snakeSegments_;

    public Snake(int speed, Point head, int x_direction, int ydirection){
        this.speed_ = speed;
        this.xdirection_ = x_direction;
        this.ydirection_ = ydirection;
        this.snakeSegments_ = new ArrayList<>();
        snakeSegments_.add(head);
    }

    public void update_direction(int x_direction, int y_direction){

    }
    public void update_speed(int speed) {
    	
    }



}