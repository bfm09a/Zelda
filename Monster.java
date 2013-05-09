import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Monster extends Being{
	Monster(String type, int startX, int startY, int h){
		super(type, startX, startY, h);
		sighted = false;
	}

	public int AI(){
		if(new Date().getTime() - time >= 2000){
			time = new Date().getTime();
			Random chooser = new Random();
			direction = chooser.nextInt(4);
		}
		return direction;
	}

	public void setSighted(boolean in){
		sighted = in;
	}

	public boolean isSighted(){
		return sighted;
	}

	/*public void move(){
		if(sighted){
			super.move();
		}
	}*/

	private boolean sighted;
	public long time;
}