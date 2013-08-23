import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Monster extends Being{// refers to bad guys
	Monster(String type, int startX, int startY, int h){
		super(type, startX, startY, h);
		sighted = false;
	}

	public int AI(){//turns the monster randomly
		if(new Date().getTime() - turnTime >= 2000){
			turnTime = new Date().getTime();
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

	public boolean isMoveTime(){
		if(new Date().getTime() - moveTime >= 200){
			moveTime = new Date().getTime();
			return true;
		}
		return false;
	}

	/*public void move(){
		if(sighted){
			super.move();
		}
	}*/

	private boolean sighted;
	public long turnTime;//timing makes it so the monster does not move too fast
	public long moveTime;
}