/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package de.buw.i2p;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

//Game class
public class Slither extends Application {
//
    private static final int width_ = 20;
    private static final int height_ = 20;
    private static final int tileSize_ = 30;
    private int playerOneYPos_ = height_/2;
    private int playerTwoYPos_ = height_/2 ;
    private int playerOneXPos_ = width_/4 ;
    private int playerTwoXPos_ = width_ - width_/4;
    private int playerOneScore = 0;
    private int playerTwoScore = 0;
    private int anzahl_barriers = 8;
    private Barrier[] array_barrier = new Barrier[anzahl_barriers];

    //create snakes
    private Snake P1;
    private Snake P2;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Slither");

        StackPane stackPane = new StackPane();


        // create and fill Canvas for background
        Canvas can_background = new Canvas(width_ * tileSize_, height_ * tileSize_);
        GraphicsContext gc_background = can_background.getGraphicsContext2D();
        for(int i= 0; i < width_; i++){
            for(int j = 0; j < height_; j++){
                if(i % 2 == j %2){
                    gc_background.setFill(Color.DARKGRAY);
                }
                else{
                    gc_background.setFill(Color.GRAY);
                }
                gc_background.fillRect(tileSize_*j, tileSize_*i, tileSize_, tileSize_);
            }
        }
        stackPane.getChildren().add(can_background); // Add Canvas to StackPane

        // create Canvas for the Snakes
        Canvas can_game = new Canvas(width_ * tileSize_, height_ * tileSize_);
        can_game.setFocusTraversable(true);
        stackPane.getChildren().add(can_game); // Add Canvas to StackPane

        // create Canvas for the Barriers
        Canvas can_barrier = new Canvas(width_ * tileSize_, height_ * tileSize_);
        stackPane.getChildren().add(can_barrier); // Add Canvas to StackPane

        stage.setScene(new Scene(stackPane));
        stage.setResizable(false);
        stage.show();

        new_game(stackPane);

    }

    // starts new game
    public void new_game(StackPane stackPane){

        Canvas can_barrier = (Canvas) stackPane.getChildren().get(2);
        Canvas can_game = (Canvas) stackPane.getChildren().get(1);

        // instantiate and draw snakes
        P1 = new Snake(5, new SnakeSegment(playerOneXPos_, playerOneYPos_), 0, -1, tileSize_, Color.PURPLE);
        P2 = new Snake(5, new SnakeSegment(playerTwoXPos_, playerTwoYPos_), 0, -1, tileSize_, Color.ORANGERED);
        P1.draw(can_game.getGraphicsContext2D());
        P2.draw(can_game.getGraphicsContext2D());

        // instatiate and draw barriers
        can_barrier.getGraphicsContext2D().clearRect(0, 0, width_ * tileSize_, height_ * tileSize_);
        for(int i = 0; i < anzahl_barriers; i++){
            array_barrier[i] = new Barrier(tileSize_);
            array_barrier[i].place_barrier();
            array_barrier[i].draw_barrier(can_barrier.getGraphicsContext2D());
        }

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

        can_game.setOnKeyPressed(null);
        can_game.setOnKeyPressed(event -> timeLine.play());

    }

    //runs the actions of one frame
    private void run(StackPane stackPane, Timeline timeline){
        Canvas can_game = (Canvas) stackPane.getChildren().get(1);
        GraphicsContext gc = can_game.getGraphicsContext2D();
        gc.clearRect(0, 0, width_ * tileSize_, height_ * tileSize_); // clear snake-canvas to draw snakes in their new position

        // check whether both snakes sould still be alive
        P1.outOfBounds(width_, height_);
        P2.outOfBounds(width_, height_);
        P1.collision(P2, array_barrier);
        P2.collision(P1, array_barrier);


        if (P1.isAlive_() && P2.isAlive_()){
            can_game.setOnKeyPressed(event -> {
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

            // move and draw snakes in their new position
            P1.move();
            P1.draw(gc);
            P2.move();
            P2.draw(gc);

        }
        else{
            timeline.stop();
            VBox vBox = new VBox();

            ToggleButton toggleButton = new ToggleButton("Play again!");
            vBox.getChildren().add(toggleButton);

            Text text = new Text("");
            text.setFont(Font.font(50));

            if (!P1.isAlive_() && P2.isAlive_()){
                text.setFill(P2.getSnakecolor_());
                text.setText("P2 won!");
            }
            else if (!P2.isAlive_() && P1.isAlive_()){
                text.setFill(P1.getSnakecolor_());
                text.setText("P1 won!");
            }
            else{
                text.setFill(Color.PURPLE);
                text.setText("Draw!");
            }


            vBox.getChildren().add(text);
            vBox.setAlignment(Pos.CENTER);
            stackPane.getChildren().add(vBox);

            toggleButton.setOnAction(event -> {
                stackPane.getChildren().remove(vBox);
                new_game(stackPane); //start new game

            });
        }
    }


    public static void main(String[] args) {
    	test.testSnakeOutOfBounds();
    	test.testSnakeElongation();
    	launch();
    }


}