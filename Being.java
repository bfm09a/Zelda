
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Being extends Entity{
	int health;
	boolean isAttacking = false;
	private int direction = 0;
	private ImageIcon walking[] = new ImageIcon[4];
	private ImageIcon attacking[];

	public Being(String type, int startX, int startY, int h){
		super(startX,startY);
		 for (int i=0; i<4; i++) {
			//blastingIcons[i] = new ImageIcon("ship"+i+"t.gif");
			walking[i] = new ImageIcon(type + i + ".png");
		}
		health = h;
		iconWidth = walking[0].getIconWidth();
		iconHeight = walking[0].getIconHeight();
	}

	public void draw(Graphics page, int leftX, int leftY){
		/*if (attack = true){

			attack = false;
		}*/
		/*else{
			super.draw(page);
		}*/
		int localX = x - leftX;
		int localY = y - leftY;
		//System.out.print("x: " + localX + "\n");
		//System.out.print("y: " + localY + "\n");
		page.drawImage(walking[direction].getImage(), (int)localX-iconWidth/2, (int)localY-iconHeight/2, null);
	}
 

	public void move(){
		if (direction == 0){
			y -= 24;
		}
		else if (direction == 1){ 
			x += 24;
		}
		else if (direction == 2){ 
			y += 24;
		}
		else if(direction == 3){
			x -= 24;
		}
	}

	boolean north(){
		if(direction != 0){
			direction = 0;
			return false;
		}
		else
			move();
		return true;
	}
	boolean east(){
		if(direction != 1){
			direction = 1;
			return false;
		}
		else
			move();
		return true;
	}
	boolean south(){
		if(direction != 2){
			direction = 2;
			return false;
		}
		else
			move();
		return true;
	}
	boolean west(){
		if(direction != 3){
			direction = 3;
			return false;
		}
		else
			move();
		return true;
	}
	/*public void attack(){
		attack = true;
	}*/
}
