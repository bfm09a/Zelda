import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Game extends GraphicsApp{
	// Game Variables
	private static final int WIDTH = 480;	// initial game screen width
	private static final int HEIGHT = 480;	// initial game screen height
	private static final int REDRAW = 60;	// screen refresh
	//private ImageIcon splashscreen, splashscreen2, level, gOverIcon;
	//private static int hearts = 3;
	private static boolean showControls = false;
	private static boolean gameStart = true;
	private static boolean gameOver = false;
	private static boolean gamePause = false;
	private static boolean buttonPressed = false;
	private static Entity[][] gridFore;
	private static Entity[][] gridBack;
		//public static Being player;
	private static int foreLeftX = 0;
	private static int foreLeftY = 0;
	private static int backLeftX = 0;
	private static int backLeftY = 0;
	private static int offsetFore = 5;
	private static int offsetBack = 8;
	private static int playerX;
	private static int playerY;
	public static void main(String args[]){
		Game zelda = new Game();
		zelda.openWindow("The Legend of Zelda",WIDTH,HEIGHT,REDRAW);
	}
	public Game(){
		gridFore = new Entity[20][20];
		gridBack = new Entity[32][32];
		for(int i = 0; i < 32; i++){
			for(int j = 0; j < 32; j++){
				gridBack[i][j] = new Entity("overworld", i * 16, j * 16);
			}
		}
		for(int i = 0; i < 20; i++){
			for(int j = 0; j < 20; j++){
				gridFore[i][j] = null;
			}
		}
		gridFore[playerX][playerY] = new Being("link", playerX * 24 ,playerY * 24, 3);
		//Entity[][] grid = new Entity;
	}

	public void draw(Graphics page){
   		//setBackground (Color.black);
   		for(int i = backLeftX; i < backLeftX + offsetBack; i++){
   			for(int j = backLeftY; j < backLeftY + offsetBack; j++){
   				if(gridBack[i][j] != null)
   					gridBack[i][j].draw(page, backLeftX, backLeftY);
   			}
   		}
   		for(int i = foreLeftX; i < foreLeftX + offsetFore; i++){
   			for(int j = foreLeftY; j < foreLeftY + offsetFore; j++){
   				if(gridFore[i][j] != null)
   					((Being)gridFore[i][j]).draw(page, foreLeftX, foreLeftY);
   			}
   		}

   	}
	public void process(){//handle wall collisions
		if (isKeyDown(KeyEvent.VK_LEFT)){
			if(playerX != 0 && gridFore[playerX - 1][playerY] == null){
   				if(((Being)gridFore[playerX][playerY]).west()){
   					gridFore[playerX-1][playerY] = gridFore[playerX][playerY];
   					gridFore[playerX][playerY] = null;
   					playerX--;
   				}
   			}
   		}
   			
		if (isKeyDown(KeyEvent.VK_RIGHT)){
			if(playerX != 19 && gridFore[playerX + 1][playerY] == null){
				if(((Being)gridFore[playerX][playerY]).east()){
					gridFore[playerX+1][playerY] = gridFore[playerX][playerY];
   					gridFore[playerX][playerY] = null;
					playerX++;
				}
			}
		}

		if(isKeyDown(KeyEvent.VK_UP)){
			if(playerY != 0 && gridFore[playerX ][playerY - 1] == null){
				if(((Being)gridFore[playerX][playerY]).north()){
					gridFore[playerX][playerY-1] = gridFore[playerX][playerY];
   					gridFore[playerX][playerY] = null;
					playerY--;
				}	
			}
		}
		if(isKeyDown(KeyEvent.VK_DOWN)){
			if(playerY != 19 && gridFore[playerX][playerY + 1] == null){
				if(((Being)gridFore[playerX][playerY]).south()){
					gridFore[playerX][playerY + 1] = gridFore[playerX][playerY];
   					gridFore[playerX][playerY] = null;
					playerY++;
				}	
			}
		}
		if(playerX < foreLeftX){
			playerX -= offsetFore;
			foreLeftX -= offsetFore;
			backLeftX -= offsetFore;
		}
		else if(playerX > foreLeftX + offsetFore){
			playerX += offsetFore;
			foreLeftX += offsetFore;
			backLeftX += offsetFore;
		}
		if(playerY < foreLeftY){
			playerY -= offsetFore;
			foreLeftY -= offsetFore;
			backLeftY -= offsetFore;
		}
		else if(playerY > foreLeftY + offsetFore){
			playerY += offsetFore;
			foreLeftY += offsetFore;
			backLeftY += offsetFore;
		}

	}
}
