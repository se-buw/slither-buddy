package de.buw.i2p;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;

public class Snake {
    private final int size_;
    private final int segmentSize_;
    private int speed_;
    private int xDirection_;
    private int yDirection_;
    // buffers next direction till snake reaches end of tile;
    private int nextYDirection_;
    private int nextXDirection_;
    private ArrayList<Point2D> snakeSegments_;
    private Color snakecolor_;

    public Snake(int size, int segmentSize, int speed, Point2D head, int x_direction, int y_direction, Color snakecolor) {
        this.size_ = size;
        segmentSize_ = segmentSize;
        this.speed_ = speed;
        this.xDirection_ = x_direction;
        this.yDirection_ = y_direction;
        this.snakeSegments_ = new ArrayList<>();
        snakeSegments_.add(head);
        for (int i = 1; i < size ; i++){
            snakeSegments_.add(new Point2D(head.getX() - xDirection_ * segmentSize_ * i, head.getY() - yDirection_ * segmentSize_ * i));
        }
        this.snakecolor_ = snakecolor;
    }


    // buffers next move because snakes have to move on the tiles
    // no edge cases because input is tightly controlled
    public void updateNextDirection(int xDirection, int yDirection) {
        if (xDirection == 0){
            nextXDirection_ = xDirection;
        }
        if (yDirection == 0){
            nextYDirection_ = yDirection;
        }
    }

    public void updateDirection(){
        xDirection_ = nextXDirection_;
        yDirection_ = nextYDirection_;
    }
//getter methods
    public ArrayList<Point2D> getSnakeSegments_() {
        return snakeSegments_;
    }
    public  Color getSnakecolor_(){
        return snakecolor_;
    }
    public  int getSegmentSize_(){
        return segmentSize_;
    }
 //setter methods
    public void setSpeed(int speed) {
        this.speed_ = speed;
    }
}
