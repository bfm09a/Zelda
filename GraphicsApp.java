//  GApp.java
// 
//  Dwayne Towell
//
//  A simple graphics application

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;

abstract class GraphicsApp extends JPanel implements KeyListener, ActionListener
{
   //-----------------------------------------------------------------
   // Gets things going with defaults.
   //

   public void openWindow(String title)
   {
      openWindow(title,300,225,0);
   }

   public void openWindow(String title, int width,int height)
   {
      openWindow(title,width,height,0);
   }


   //-----------------------------------------------------------------
   // Really get things going.
   //
   public void openWindow(String title, int width,int height,int delay)
   {
      JFrame frame = new JFrame(title);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      setBackground(Color.black);
      setPreferredSize(new Dimension(width,height));
      frame.getContentPane().add(this);

      frame.pack();
      frame.setVisible(true);

      if (delay > 0) {
          addKeyListener(this);
          setFocusable(true);
          requestFocus();
	  
          timer = new Timer(delay,this);
          timer.start();
      }         
   }

   private Timer      timer;
   private final int  KEY_SIZE = 256;
   private boolean    keyBuffer[] = new boolean[KEY_SIZE];      

   //-----------------------------------------------------------------
   // Handle painting.
   //
   final public void paintComponent (Graphics page)
   {
      super.paintComponent(page);
      draw(page);
   }

   //-----------------------------------------------------------------
   // force derived class to implement draw function
   //
   abstract void draw(Graphics page);

   //-----------------------------------------------------------------
   // dummy process function in case derived class doesn't provide one
   //
   public void process()
   {
   }

   //-----------------------------------------------------------------
   // grab events and save them
   //
   final public void keyPressed(KeyEvent event)
   {
      if (event.getKeyCode() < KEY_SIZE)
         keyBuffer[event.getKeyCode()] = true;
   }

   final public void keyReleased(KeyEvent event)
   {
      if (event.getKeyCode() < KEY_SIZE)
         keyBuffer[event.getKeyCode()] = false;
   }

   final public void keyTyped(KeyEvent event)
   {
   }

   //-----------------------------------------------------------------
   // Return true if a specific key is currently pressed.
   //
   final protected boolean isKeyDown(int keyCode)
   {
      return keyCode<KEY_SIZE && keyBuffer[keyCode];
   }

   //-----------------------------------------------------------------
   // Return true is a specific key is currently unpressed.
   //
   final protected boolean isKeyUp(int keyCode)
   {
      return keyCode>=KEY_SIZE || !keyBuffer[keyCode];
   }

   //-----------------------------------------------------------------
   // "pump" things and keep it drawing new frames
   //
   final public void actionPerformed(ActionEvent event)
   {
      process();
      repaint();
   }
}
