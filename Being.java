
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Being extends Entity{//refers to anything that can move
	int health;
	boolean isAttacking = false;
	protected int direction = 0;
	private ImageIcon walking[] = new ImageIcon[4];
	private ImageIcon attacking[];
	long attackTime;

	Being(String type, int startX, int startY, int h){
		super(startX,startY);
		for (int i=0; i<4; i++) {
			//blastingIcons[i] = new ImageIcon("ship"+i+"t.gif");
			walking[i] = new ImageIcon(type + i + ".png");
			/*if(type+i == "link1"){
				System.out.print("here\n");
				walking[i] = new ImageIcon("LinkRunShieldMoving.gif");
			}*/
		}
		health = h;
		iconWidth = walking[0].getIconWidth();
		iconHeight = walking[0].getIconHeight();
		//x = startX;
		//y = startY;
	}

	public void draw(Graphics page, int leftX, int leftY){
		/*if (attack = true){

			attack = false;
		}*/
		/*else{
			super.draw(page);
		}*/
		int localX = x - leftX * 24;
		int localY = y - leftY * 24;
		//System.out.print("X: " + localX + " Y: " + localY + "\n");
		//System.out.print("x: " + localX + "\n");
		//System.out.print("y: " + localY + "\n");
		page.drawImage(walking[direction].getImage(), (int)localX, (int)localY, null);
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

	public boolean north(){
		if(direction != 0){
			direction = 0;
			return false;
		}
		else
			move();
		return true;
	}
	public boolean east(){
		if(direction != 1){
			direction = 1;
			return false;
		}
		else
			move();
		return true;
	}
	public boolean south(){
		if(direction != 2){
			direction = 2;
			return false;
		}
		else
			move();
		return true;
	}
	public boolean west(){
		if(direction != 3){
			direction = 3;
			return false;
		}
		else
			move();
		return true;
	}

	public boolean isDead(){
		return health <= 0;
	}

	public int getHealth(){
		return health;
	}

	public int getDirection(){
		return direction;
	}

	public void setHealth(int num){
		health += num;
	}

	public void attack(Being other){
		if(other != null &&  new Date().getTime() - attackTime >= 500){
			attackTime = new Date().getTime();
			other.setHealth(-1);
		}
	}
}
