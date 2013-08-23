
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Entity{ //refers to any inanimate object
	protected int x;
	protected int y;
	private ImageIcon image;
	protected int iconWidth, iconHeight;
	protected int width, height;

	public Entity(String type, int startX, int startY){
		image = new ImageIcon(type+".png");
		iconWidth = image.getIconWidth();
		iconHeight = image.getIconHeight();
		x = startX;
		y = startY;
	}

	public Entity(int startX, int startY){
		x = startX;
		y = startY;
	}

	public void draw(Graphics page, int leftX, int leftY, int cellSize){
		/*if (attack = true){

			attack = false;
		}*/
		/*else{
			super.draw(page);
		}*/
		int localX = x - leftX * cellSize;
		int localY = y - leftY * cellSize;
		//System.out.print("x: " + localX + "\n");
		//System.out.print("y: " + localY + "\n");
		page.drawImage(image.getImage(), (int)localX, (int)localY, null);
	}
	
}