package de.buw.i2p;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.Point;
import java.util.ArrayList;

public class Snake {
    private final int size_;
    private int xDirection_;
    private int yDirection_;
    private int segmentSize_;
    private Color snakecolor_;
    private boolean alive_ = true;
    private ArrayList<SnakeSegment> snakeSegments_;

    public Snake(int size, SnakeSegment head, int x_direction, int y_direction, int segmentSize, Color snakecolor) {
        this.size_ = size;
        this.xDirection_ = x_direction;
        this.yDirection_ = y_direction;
        this.segmentSize_ = segmentSize;
        this.snakeSegments_ = new ArrayList<>();
        snakeSegments_.add(head);
        for (int i = 1; i < size ; i++){
            snakeSegments_.add(new SnakeSegment(head.getX() - xDirection_  * i, head.getY() - yDirection_  * i));
        }
        this.snakecolor_ = snakecolor;
    }


    // buffers next move because snakes have to move on the tiles
    // no edge cases because input is tightly controlled
    public void updateDirection(int nextXDirection, int nextYDirection) {
        if (xDirection_ == 0 && nextXDirection != 0){
            xDirection_ = nextXDirection;
            yDirection_ = 0;
        }
        if (yDirection_ == 0 && nextYDirection != 0){
            yDirection_ = nextYDirection;
            xDirection_ = 0;
        }
    }

    public void move(){
        for (int i = snakeSegments_.size() - 1; i > 0; i--) {
            SnakeSegment prev = snakeSegments_.get(i - 1);
            SnakeSegment newCurr = new SnakeSegment(prev.getX(), prev.getY());
            snakeSegments_.set(i, newCurr);
        }
        SnakeSegment head = snakeSegments_.get(0);
        head.setX(head.getX() + xDirection_);
        head.setY(head.getY() + yDirection_);
        //System.out.println("test");
    }

    public void outOfBounds(int width, int height){
        SnakeSegment head = snakeSegments_.get(0);
        if (head.getX() <= 0 || head.getX() >= width || head.getY() <= 0 || head.getY() >= height){
            alive_ = false;
        }
    }

    public void collision(Snake otherSnake, Barrier[] array_barrier){
        SnakeSegment head = snakeSegments_.get(0);
        //collision with other snake
        for (SnakeSegment segment : otherSnake.snakeSegments_){
            if ((head.getX() == segment.getX()) && (head.getY() == segment.getY())){
                alive_ = false;
            }
        }
        //collision with self
        for (int i = 1; i < snakeSegments_.size(); i++){
            if ((head.getX() == snakeSegments_.get(i).getX()) && (head.getY() == snakeSegments_.get(i).getY())){
                alive_ = false;
            }
        }
        //collision with barrier
        for(Barrier bar : array_barrier){
            //System.out.println(head.getX() + ";"+ head.getY() + "/n");
            if (bar.collision_barrier((int)(head.getX()), (int)head.getY())) {
                alive_ = false;
                break;
            }
        }
    }

    public void draw(GraphicsContext gc){
        gc.setFill(snakecolor_);
        for(SnakeSegment segment : getSnakeSegments_()){
            gc.fillRect(segment.getX() * segmentSize_,segment.getY() * segmentSize_, segmentSize_,segmentSize_);
        }

        SnakeSegment head = snakeSegments_.get(0);
        //gc.setFill(Color.WHITE);
        //gc.fillRect(head.getX() * segmentSize_,head.getY() * segmentSize_, segmentSize_,segmentSize_);
        gc.setFill(Color.BLACK);
        gc.fillOval((head.getX() * segmentSize_ + segmentSize_/3.0) - 1.5, (head.getY()*segmentSize_+segmentSize_/2.0)-1.5, 3,3);
        gc.fillOval((head.getX() * segmentSize_ + segmentSize_* (2.0/3.0))-1.5, (head.getY()*segmentSize_+segmentSize_/2.0)-1.5, 3,3);

    }

    public void elongate(){
        snakeSegments_.add(new SnakeSegment(-100, -100));
    }
//getter methods
    public ArrayList<SnakeSegment> getSnakeSegments_() {
        return snakeSegments_;
    }
    public  Color getSnakecolor_(){
        return snakecolor_;
    }
    //for testing remove later
    public boolean isAlive_(){return alive_;}
 //setter methods
}
