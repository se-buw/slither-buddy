/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.buw.i2p;

import com.google.common.graph.Graph;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.awt.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

//Game class
public class Slither extends Application {

    private static final int width_ = 20;
    private static final int height_ = 20;
    private static final int tileSize_ = 30;
    private int playerOneYPos_ = height_/2;
    private int playerTwoYPos_ = height_/2 ;
    private int playerOneXPos_ = width_/4 ;
    private int playerTwoXPos_ = width_ - width_/4;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private boolean gameStarted_ = false;
    //create snakes
    private Snake P1 = new Snake(5, new SnakeSegment(playerOneXPos_, playerOneYPos_), 0, -1, tileSize_, Color.RED);
    private Snake P2 = new Snake(5, new SnakeSegment(playerTwoXPos_, playerTwoYPos_), 0, -1, tileSize_, Color.BLUE);



    @Override
    public void start(Stage stage) {
        stage.setTitle("Slither");

        StackPane stackPane = new StackPane();

        // create background tiles
        GridPane grid = new GridPane();
        for (int i = 0; i < width_; i++){
            for (int j = 0; j < height_; j++){
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
        stackPane.getChildren().add(grid);

        //the snakes will be drawn on graphics context of the canvas
        Canvas can = new Canvas(width_ * tileSize_, height_ * tileSize_);
        can.setFocusTraversable(true);
        can.setOnKeyPressed(event -> {gameStarted_ = true;});
        stackPane.getChildren().add(can);

        //setup timeline
        Timeline timeLine = new Timeline();
        timeLine.setCycleCount(Timeline.INDEFINITE);

        Duration cycleDur = Duration.millis(500);
        AtomicInteger executionCount = new AtomicInteger(0);
        KeyFrame keyframe = new KeyFrame(cycleDur, event -> {
            run(stackPane, timeLine);
            int count = executionCount.incrementAndGet();
            if (count % 5 == 0) {
                P1.elongate();
                P2.elongate();
            }
        });
        timeLine.getKeyFrames().add(keyframe);


        stage.setScene(new Scene(stackPane));
        stage.setResizable(false);
        stage.show();
        timeLine.play();

    }

    //runs the game
    private void run(StackPane stackPane, Timeline timeline){
        Canvas can = (Canvas) stackPane.getChildren().get(1);
        GraphicsContext gc = can.getGraphicsContext2D();

        gc.clearRect(0, 0, width_ * tileSize_, height_ * tileSize_);

        if(gameStarted_) {

            if (P1.isAlive_() && P2.isAlive_()){
                can.setOnKeyPressed(event -> {
                    switch(event.getCode()){

                        case UP:
                            P2.updateDirection(0, -1);
                            break;
                        case DOWN:
                            P2.updateDirection(0, 1);
                            break;
                        case LEFT:
                            P2.updateDirection(-1, 0);
                            break;
                        case RIGHT:
                            P2.updateDirection(1, 0);
                            break;
                        case W:
                            P1.updateDirection(0, -1);
                            break;
                        case S:
                            P1.updateDirection(0, 1);
                            break;
                        case A:
                            P1.updateDirection(-1, 0);
                            break;
                        case D:
                            P1.updateDirection(1, 0);
                            break;

                    }
                });

                P1.outOfBounds(width_, height_);
                P2.outOfBounds(width_, height_);
                P1.collision(P2);
                P2.collision(P1);

                P1.move();
                P1.draw(gc);
                P2.move();
                P2.draw(gc);
            }
            else{
                timeline.stop();
                VBox vBox = new VBox();
                final VBox[] finalVBox = {vBox};
                ToggleButton toggleButton = new ToggleButton("Play again!");
                toggleButton.setOnAction(event -> {
                    P1 = new Snake(5, new SnakeSegment(playerOneXPos_, playerOneYPos_), 0, -1, tileSize_, Color.RED);
                    P2 = new Snake(5, new SnakeSegment(playerTwoXPos_, playerTwoYPos_), 0, -1, tileSize_, Color.BLUE);
                    stackPane.getChildren().remove(finalVBox[0]);
                    finalVBox[0] = null;
                    timeline.play();
                    //gameStarted_ = false;
                });
                vBox.getChildren().add(toggleButton);

                if (!P1.isAlive_() && P2.isAlive_()){

                    Text text = new Text("P2 won!");
                    text.setFill(P2.getSnakecolor_());
                    text.setFont(Font.font(50));

                    vBox.getChildren().add(text);
                    vBox.setAlignment(Pos.CENTER);
                    stackPane.getChildren().add(finalVBox[0]);

                }
                else if (!P2.isAlive_() && P1.isAlive_()){

                    Text text = new Text("P1 won!");
                    text.setFill(P1.getSnakecolor_());
                    text.setFont(Font.font(50));

                    vBox.getChildren().add(text);
                    vBox.setAlignment(Pos.CENTER);
                    stackPane.getChildren().add(finalVBox[0]);
                }
                else{

                    Text text = new Text("Draw!");
                    text.setFill(Color.PURPLE);
                    text.setFont(Font.font(50));

                    vBox.getChildren().add(text);
                    vBox.setAlignment(Pos.CENTER);
                    stackPane.getChildren().add(finalVBox[0]);
                }
            }

        }
        else {
            P1.draw(gc);
            P2.draw(gc);
        }
    }


    public static void main(String[] args) {
        launch();
    }


}