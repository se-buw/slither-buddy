/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.buw.i2p;

import com.google.common.graph.Graph;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.awt.*;
import java.util.ArrayList;

//Game class
public class Slither extends Application {

    private static final int width_ = 800;
    private static final int height_ = 600;
    private static final int tileSize_ = height_/12;
    private int playerOneYPos_ = height_/2;
    private int playerTwoYPos_ = height_/2;
    private int playerOneXPos_ = width_/4;
    private int playerTwoXPos_ = width_ - width_/4;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private boolean gameStarted_ = false;
    //create snakes
    private Snake P1 = new Snake(3, tileSize_, 1, new Point2D(playerOneXPos_, playerOneYPos_), 0, 1,Color.RED);
    private Snake P2 = new Snake(3, tileSize_, 1, new Point2D(playerTwoXPos_, playerTwoYPos_), 0, 1,Color.BLUE);



    @Override
    public void start(Stage stage) {
        stage.setTitle("Slither");
        //the snakes will be drawn on graphics context of the canvas
        Canvas can = new Canvas(width_, height_);
        GraphicsContext gc = can.getGraphicsContext2D();
        Duration cycleDur = Duration.millis(10);
        Timeline timeLine = new Timeline(new KeyFrame(cycleDur, event -> {run(gc);}));
        timeLine.setCycleCount(Timeline.INDEFINITE);


        can.setFocusTraversable(true);
        can.setOnKeyPressed(event -> {gameStarted_ = true;});

        // create background tiles
        GridPane grid = new GridPane();
        for (int i = 0; i < width_/tileSize_; i++){
            for (int j = 0; j < height_/tileSize_; j++){
                Pane pane = new Pane();
                pane.setPrefSize(tileSize_, tileSize_);
                if ((i+j) % 2 == 0){
                    pane.setStyle("-fx-background-color: lightgreen;");
                }
                else{
                    pane.setStyle("-fx-background-color: darkgreen;");
                }
                grid.add(pane, i, j);
            }
        }

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(grid);
        stackPane.getChildren().add(can);
        stage.setScene(new Scene(stackPane));
        stage.setResizable(false);
        stage.show();
        timeLine.play();

    }
    public void draw(GraphicsContext gc,Snake snake ){
        ArrayList<Point2D> snakeSegments = snake.getSnakeSegments_();
        for(Point2D segment : snakeSegments){
            gc.setFill(snake.getSnakecolor_());
            gc.fillRect(segment.getX(),segment.getY(),snake.getSegmentSize_(),snake.getSegmentSize_());

        }
    }
    //runs the game
    private void run(GraphicsContext gc){
        if(gameStarted_) {

        }
        else {
            draw(gc, P1);
            draw(gc, P2);
        }
    }


    public static void main(String[] args) {
        launch();
    }


}