import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Game extends GraphicsApp{
	// Game Variables
	private static final int WIDTH = 480;	// initial game screen width
	private static final int HEIGHT = 480;	// initial game screen height
	private static final int REDRAW = 60;	// screen refresh
	//private ImageIcon splashscreen, splashscreen2, level, gOverIcon;
	//private static int hearts = 3;
	int foreSize = 500; //number of squares in gridFore
	int backSize = 600;	//number of squares in gridBack
	private static boolean showControls = false;
	private static boolean gameStart = true;
	private static boolean gameOver = false;
	private static boolean gamePause = false;
	private static boolean buttonPressed = false;
	private static Entity[][] gridFore;//holds the level the character interacts with
	private static Entity[][] gridBack;//holds background
	private static int foreLeftX = 0;//left most square of onscreen portion of grid
	private static int foreLeftY = 0;
	private static int backLeftX = 0;
	private static int backLeftY = 0;
	private static int offsetFore = 20;//number of foreground cells on screen
	private static int offsetBack = 30;//number of background cells on screen
	private static int playerX;
	private static int playerY;
	private ImageIcon gOverIcon;
	private ImageIcon heartIcon;

	private static int gOverIconWidth, gOverIconHeight;
	private static int heartIconWidth, heartIconHeight;
	public static void main(String args[]){
		Game zelda = new Game();
		zelda.openWindow("The Legend of Zelda",WIDTH,HEIGHT,REDRAW);
	}
	public Game(){
		//String path;
		Random chooser = new Random();
		Scanner read = null;
		char next = 'a';
		gridFore = new Entity[foreSize][foreSize];
		gridBack = new Entity[backSize][backSize];
		for(int i = 0; i < backSize; i++){
			for(int j = 0; j < backSize; j++){
				gridBack[i][j] = new Entity("overworld", i * 16, j * 16);
			}
		}
		for(int i = 0; i < foreSize/offsetFore; i++){
			for(int j = 0; j < foreSize/offsetFore; j++){
				/*if( i == 0 && j == 0){//system for creating maps in text files 25 maps can be made. Check the maps folder for examples
					try{	
						path = "/Users/Fraser/Desktop/Programming/Java/Zelda/maps/map";
						path+= i.toString();
						path+= j.toString();
						path+=".txt";
						read = new Scanner(new File("/Users/Fraser/Desktop/Programming/Java/Zelda/maps/map00.txt"));
					}
					catch(FileNotFoundException e){
						System.err.println("FileNotFoundException: " + e.getMessage());
					}
				}
				*/
				for(int gridX = offsetFore * i; gridX < offsetFore * (i + 1); gridX++){
					for(int gridY = offsetFore * j; gridY < offsetFore * (j + 1); gridY++){
						//stuff = read.next();
						if(i == 0 && j == 0 && gridX == 0 && gridY == 0 ){//sets player to top left corner
							next = 'P';
						}
						else if(chooser.nextDouble() < .08){//randomly sets bushes
							next = 'B';
						}
						else if(chooser.nextDouble() < .01){//randomly sets enemies
							next = 'X';
						}
						else{
							next = '0';
						}
						if(next == '0'){
							gridFore[gridX][gridY] = null;
						}
						else if(next == 'X'){
							gridFore[gridX][gridY] = new Monster("monster", gridX * 24, gridY * 24, 2);
						}
						else if(next == 'B'){
							//System.out.print("X: " + (i * offsetFore) + gridX * 24 +  " Y: " + (j * offsetFore) + gridY + "\n");
							gridFore[gridX][gridY] = new Entity("bush", gridX * 24, gridY * 24);
						}
						else if(next == 'P'){
							playerX = gridX;
							playerY = gridY;
							//System.out.print("PX: " + playerX +  " PY: " + playerY + "\n");
							gridFore[playerX][playerY] = new Being("link", playerX * 24, playerY * 24, 5);
						}
					}
				}
			}
		}
		for(int i = 0; i < playerX; i += offsetFore){//finds what the leftmost space in the current grid will be
			foreLeftX = i;
		}
		for(int i = 0; i < playerY; i += offsetFore){
			foreLeftY = i;
		}
		
		backLeftX = foreLeftX / 20 * 30;
		backLeftY = backLeftY / 20 * 30;
		gOverIcon = new ImageIcon ("gameover.gif");
		gOverIconWidth = gOverIcon.getIconWidth();
		gOverIconHeight = gOverIcon.getIconHeight();
		heartIcon = new ImageIcon ("heart.gif");
		heartIconWidth = heartIcon.getIconWidth();
		heartIconHeight = heartIcon.getIconHeight();
		
		//Entity[][] grid = new Entity;
	}

	public void draw(Graphics page){
   		//setBackground (Color.black);
   		for(int i = backLeftX; i < backLeftX + offsetBack; i++){
   			for(int j = backLeftY; j < backLeftY + offsetBack; j++){
   				if(gridBack[i][j] != null)
   					gridBack[i][j].draw(page, backLeftX, backLeftY, 16);
   			}
   		}
   		for(int i = foreLeftX; i < foreLeftX + offsetFore; i++){
   			for(int j = foreLeftY; j < foreLeftY + offsetFore; j++){
   				if(gridFore[i][j] != null)
   					if(gridFore[i][j] instanceof Being)
   						((Being)gridFore[i][j]).draw(page, foreLeftX, foreLeftY);
   					else if(gridFore[i][j] instanceof Entity)
   						(gridFore[i][j]).draw(page, foreLeftX, foreLeftY, 24);
   					else if(gridFore[i][j] instanceof Monster)
   						((Monster)gridFore[i][j]).draw(page, foreLeftX, foreLeftY);
   			}
   		}
   		for(int i = 0; i < ((Being)gridFore[playerX][playerY]).getHealth(); i++){
   			page.drawImage(heartIcon.getImage(),(heartIconHeight + 4) * i , heartIconWidth,null);
   		}

   		if((((Being)gridFore[playerX][playerY]).isDead())){
   			page.drawImage(gOverIcon.getImage(),(WIDTH/3),(HEIGHT/2)-gOverIconHeight,null);
   		}

   	}
	public void process(){//handle wall collisions
		//System.out.print("X: " + playerX + " Y: " + playerY + "\n");
		//System.out.print("health = " + ((Being)gridFore[playerX][playerY]).getHealth() + "\n");
		if(!(((Being)gridFore[playerX][playerY]).isDead())){
			if(gridFore[playerX][playerY] != null){//controlls player movement
				if (isKeyDown(KeyEvent.VK_LEFT)){
					if(playerX != 0 && gridFore[playerX - 1][playerY] == null){
		   				if(((Being)gridFore[playerX][playerY]).west()){
		   					gridFore[playerX-1][playerY] = gridFore[playerX][playerY];
		   					gridFore[playerX][playerY] = null;
		   					playerX--;
		   				}
		   			}
		   		}
				else if (isKeyDown(KeyEvent.VK_RIGHT)){
					if(playerX != foreSize - 1 && gridFore[playerX + 1][playerY] == null){
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
				else if(isKeyDown(KeyEvent.VK_DOWN)){
					if(playerY != foreSize - 1 && gridFore[playerX][playerY + 1] == null){
						if(((Being)gridFore[playerX][playerY]).south()){
							gridFore[playerX][playerY + 1] = gridFore[playerX][playerY];
		   					gridFore[playerX][playerY] = null;
							playerY++;
						}	
					}
				}
				if(isKeyDown(KeyEvent.VK_SPACE)){//allows user to attack
					if(((Being)gridFore[playerX][playerY]).getDirection() == 0 && gridFore[playerX][playerY - 1] instanceof Being){
						((Being)gridFore[playerX][playerY]).attack((Being)gridFore[playerX][playerY - 1]);
					}
					else if(((Being)gridFore[playerX][playerY]).getDirection() == 1 && gridFore[playerX + 1][playerY] instanceof Being){
						((Being)gridFore[playerX][playerY]).attack((Being)gridFore[playerX + 1][playerY]);
					}
					else if(((Being)gridFore[playerX][playerY]).getDirection() == 2 && gridFore[playerX][playerY + 1] instanceof Being){
						((Being)gridFore[playerX][playerY]).attack((Being)gridFore[playerX][playerY + 1]);
					}
					else if(((Being)gridFore[playerX][playerY]).getDirection() == 3 && gridFore[playerX - 1][playerY] instanceof Being){
						((Being)gridFore[playerX][playerY]).attack((Being)gridFore[playerX - 1][playerY]);
					}
				}
				if(playerX < foreLeftX){//shifts the creen as needed
					
					//playerX -= offsetFore;
					foreLeftX -= offsetFore;
					backLeftX -= offsetBack;
					//System.out.print("1\n");
				}
				else if(playerX > foreLeftX + offsetFore){
				
					//playerX += offsetFore;
					foreLeftX += offsetFore;
					backLeftX += offsetBack;
					//System.out.print("2 foreLeftX: " + foreLeftX + "backLeftX: " + backLeftX + "\n");
				}
				if(playerY < foreLeftY){
					//System.out.print("3\n");
					//playerY -= offsetFore;
					foreLeftY -= offsetFore;
					backLeftY -= offsetBack;
				}
				else if(playerY > foreLeftY + offsetFore){
					//System.out.print("4\n");
					//playerY += offsetFore;
					foreLeftY += offsetFore;
					backLeftY += offsetBack;
				}
			}
			int direction;
			for(int i = foreLeftX; i < foreLeftX + offsetFore; i++){
				for(int j = foreLeftY; j < foreLeftY + offsetFore; j++){
					if(gridFore[i][j] instanceof Monster){//controls monster AI and movement
						if(((Monster)gridFore[i][j]).isDead()){// if the monster is dead it is removed
							gridFore[i][j] = null;
						}
						else{
							if(!((Monster)gridFore[i][j]).isSighted())//if the player is not found the monster will search by turning
								direction = ((Monster)gridFore[i][j]).AI();
							else{
								direction = ((Monster)gridFore[i][j]).getDirection();//otherwise it's direction stays the same
								//System.out.print("direction\n");
							}
							if(direction == 0){//checks if player is sighted
								if(i == playerX && j > playerY){
									((Monster)gridFore[i][j]).setSighted(true);
								}
								else {
									((Monster)gridFore[i][j]).setSighted(false);
								}
							}
							else if(direction == 1){
									if(j == playerY && i < playerX){
										((Monster)gridFore[i][j]).setSighted(true);
									}
									else {
										((Monster)gridFore[i][j]).setSighted(false);
									}
							}
							else if(direction == 2){
									if(i == playerX && j < playerY){
										((Monster)gridFore[i][j]).setSighted(true);
									}
									else {
										((Monster)gridFore[i][j]).setSighted(false);
									}
							}
							else if(direction == 3){
									if(j == playerY && i > playerX){
										((Monster)gridFore[i][j]).setSighted(true);
									}
									else {
										((Monster)gridFore[i][j]).setSighted(false);
									}
							}
							

							if(((Monster)gridFore[i][j]).isSighted() && ((Monster)gridFore[i][j]).isMoveTime()){//handles movement and monster attacks
								if(((Monster)gridFore[i][j]).getDirection() == 0 && j != 0 && i == playerX && j - 1 == playerY ){ //movement
									((Monster)gridFore[i][j]).attack(((Being)gridFore[i][j - 1]));
								}
								else if(((Monster)gridFore[i][j]).getDirection() == 1 && i != offsetFore && i + 1 == playerX && j == playerY ){
									((Monster)gridFore[i][j]).attack(((Being)gridFore[i + 1][j]));
								}
								else if(((Monster)gridFore[i][j]).getDirection() == 2 && j != offsetFore && i == playerX && j + 1 == playerY ){
									((Monster)gridFore[i][j]).attack(((Being)gridFore[i][j + 1]));
								}
								else if(((Monster)gridFore[i][j]).getDirection() == 3 && i != 0 && i-1 == playerX && playerY == j){
									((Monster)gridFore[i][j]).attack(((Being)gridFore[i - 1][j]));
								}

								if(direction == 0 && j != foreLeftY && gridFore[i][j - 1] == null){ //attacks
									((Monster)gridFore[i][j]).move();
									gridFore[i][j-1] = gridFore[i][j];
									gridFore[i][j] = null;
								} 
								else if(direction == 1 && i != foreLeftX + offsetFore && gridFore[i + 1][j] == null){
									((Monster)gridFore[i][j]).move();
									gridFore[i+1][j] = gridFore[i][j];
									gridFore[i][j] = null;
								} 
								else if(direction == 2 && j != foreLeftY + offsetFore && gridFore[i][j + 1] == null){
									((Monster)gridFore[i][j]).move();
									gridFore[i][j+1] = gridFore[i][j];
									gridFore[i][j] = null;
								} 
								else if(direction == 3 && i != foreLeftX && gridFore[i - 1][j] == null){
									((Monster)gridFore[i][j]).move();
									gridFore[i-1][j] = gridFore[i][j];
									gridFore[i][j] = null;
								}
							}
						}
					}
				}
			}
		}

	}
}
