package de.buw.i2p;

import javafx.scene.paint.Color;

public class test
{
	private static final int width_ = 20;
    private static final int height_ = 20;
    private static final int tileSize_ = 30;
    private static int playerOneYPos_ = height_/2;
    private static int playerTwoYPos_ = height_/2 ;
    private static int playerOneXPos_ = width_/4 ;
    private static int playerTwoXPos_ = width_ - width_/4;
    private static int playerOneScore = 0;
    private static int playerTwoScore = 0;
    private static int anzahl_barriers = 7;
    private static Barrier[] array_barrier = new Barrier[anzahl_barriers];
    
    
	public static void testSnakeOutOfBounds()
	{
		Snake snake = new Snake(5, new SnakeSegment(playerOneXPos_, playerOneYPos_), 0, -1, tileSize_, Color.PURPLE);
		snake.outOfBounds(width_, height_);
		if(snake.isAlive_()) {
			System.out.println("Test passed");
		}
		else {
			System.out.println("Test failed");
		}
		for(int i = 0; i < 10; ++i)
		{
			snake.move();
		}
		snake.outOfBounds(width_, height_);
		if(!snake.isAlive_()) {
			System.out.println("Test passed");
		}
		else {
			System.out.println("Test failed");
		}
	}
	
	public static void testSnakeElongation()
	{
		Snake snake = new Snake(5, new SnakeSegment(playerOneXPos_, playerOneYPos_), 0, -1, tileSize_, Color.PURPLE);
		if(snake.getSnakeSegments_().size() == 5) {
			System.out.println("Test passed");
		}
		else {
			System.out.println("Test failed");
		}
		for(int i = 0; i < 20; i++)
		{
			snake.elongate();
		}
		if(snake.getSnakeSegments_().size() == 25) {
			System.out.println("Test passed");
		}
		else {
			System.out.println("Test failed");
		}
	}
}