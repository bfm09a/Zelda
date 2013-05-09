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
	int foreSize = 400;
	int backSize = 600;
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
	private static int offsetFore = 20;
	private static int offsetBack = 30;
	private static int playerX;
	private static int playerY;
	public static void main(String args[]){
		Game zelda = new Game();
		zelda.openWindow("The Legend of Zelda",WIDTH,HEIGHT,REDRAW);
	}
	public Game(){
		Scanner read = null;
		char next = 'a';
		gridFore = new Entity[foreSize][foreSize];
		gridBack = new Entity[backSize][backSize];
		for(int i = 0; i < backSize; i++){
			for(int j = 0; j < backSize; j++){
				gridBack[i][j] = new Entity("overworld", i * 16, j * 16);
			}
		}
		/*for(int i = 0; i < foreSize; i++){
			for(int j = 0; j < foreSize; j++){
				gridFore[i][j] = null;
			}
		}*/
		String stuff;
		for(int i = 0; i < foreSize/offsetFore; i++){
			for(int j = 0; j < foreSize/offsetFore; j++){
				//System.out.print("offsetFore: " + offsetFore + "\n");
				if( i == 0 && j == 0){
					try{	
						read = new Scanner(new File("/Users/Fraser/Desktop/Programming/Java/Zelda/maps/map00.txt"));
					}
					catch(FileNotFoundException e){
						System.err.println("FileNotFoundException: " + e.getMessage());
					}
				}
				else{
					try{	
						read = new Scanner(new File("/Users/Fraser/Desktop/Programming/Java/Zelda/maps/map01.txt"));
					}
					catch(FileNotFoundException e){
						System.err.println("FileNotFoundException: " + e.getMessage());
					}
				}
				for(int gridX = offsetFore * i; gridX < offsetFore * (i + 1); gridX++){
					for(int gridY = offsetFore * j; gridY < offsetFore * (j + 1); gridY++){
						stuff = read.next();
						next = stuff.charAt(0);
						System.out.print(gridX + " " + gridY + " " + stuff + "\n");
						//System.out.print("i: " + i + " X: " + gridX + " fullX: " + (i * offsetFore * 24 + gridX * 24) + "\n");
						//System.out.print("j: " + j + " Y: " + gridY + " fullY: " + (j * offsetFore * 24 + gridY * 24) + "\n");
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
							gridFore[playerX][playerY] = new Being("link", playerX * 24, playerY * 24, 3);
						}
					}
				}
			}
		}
		for(int i = 0; i < playerX; i += offsetFore){
			foreLeftX = i;
		}
		for(int i = 0; i < playerY; i += offsetFore){
			foreLeftY = i;
		}
		
		backLeftX = foreLeftX / 20 * 30;
		backLeftY = backLeftY / 20 * 30;
		
		
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

   	}
	public void process(){//handle wall collisions
		//System.out.print("X: " + playerX + " Y: " + playerY + "\n");
		if(gridFore[playerX][playerY] != null){
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
			if(isKeyDown(KeyEvent.VK_DOWN)){
				if(playerY != foreSize - 1 && gridFore[playerX][playerY + 1] == null){
					if(((Being)gridFore[playerX][playerY]).south()){
						gridFore[playerX][playerY + 1] = gridFore[playerX][playerY];
	   					gridFore[playerX][playerY] = null;
						playerY++;
					}	
				}
			}
			if(playerX < foreLeftX){
				
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
				if(gridFore[i][j] instanceof Monster){
					if(!((Monster)gridFore[i][j]).isSighted())
						direction = ((Monster)gridFore[i][j]).AI();
					else
						direction = ((Monster)gridFore[i][j]).getDirection();
					if(direction == 0){
						for(int LOS = j; LOS > foreLeftX; LOS--){
							if(LOS == playerX){
								System.out.print("i " + i + " j " + j + " Player is sighted 0\n");
								((Monster)gridFore[i][j]).setSighted(true);
							}
						}
					}
					else if(direction == 1){
						for(int LOS = i; LOS < foreLeftY + offsetFore; LOS++){
							if(LOS == playerY){
								System.out.print("i " + i + " j " + j + " Player is sighted 1\n");
								((Monster)gridFore[i][j]).setSighted(true);
							}
						}
					}
					else if(direction == 2){
						for(int LOS = j; LOS < foreLeftX + offsetFore; LOS++){
							if(LOS == playerX){
								System.out.print("i " + i + " j " + j + " Player is sighted 2 \n");
								((Monster)gridFore[i][j]).setSighted(true);
							}
						}
					}
					else if(direction == 3){
						for(int LOS = i; LOS > foreLeftY; LOS--){
							if(LOS == playerY){
								System.out.print("i " + i + " j " + j + " Player is sighted 3\n");
								((Monster)gridFore[i][j]).setSighted(true);
							}
						}
					}
					else ((Monster)gridFore[i][j]).setSighted(false);

					if(((Monster)gridFore[i][j]).isSighted()){
						((Monster)gridFore[i][j]).move();
						if(direction == 0 && j != foreLeftY && gridFore[i][j - 1] == null){
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
