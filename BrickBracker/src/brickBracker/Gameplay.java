package brickBracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	// to start the game
	private boolean gamestart = false;
	boolean pause = false;
	private int score =0;
	private int totalbricks =45;
	
	//for the ball speed - how can moved
	private Timer timer;
	private int delay =8;
	
	//starting postion
	private int playerx=310;
	
//	private int ballposx =120;
//	private int ballposy= 350;
	private int ballposx =350;
	private int ballposy= 530;
	
	// direction
	private int ballxdir = -1;
	private int ballydir = -2;
	
	//me: added xmove
//	private int xmove;
	
	private MapGenerator mapGen;
	
	public Gameplay(){
		
		// here we : 7 columns and 3 rows 
		mapGen = new MapGenerator(5,9);
		
//		totalbricks = mapGen.brickNum;
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay,this);
		timer.start();
	}
	
	
	public void paint(Graphics g){
		// background
		g.setColor(new Color(16,23,16));
		g.fillRect(2, 2, 690 , 600);
		
		//drawing map
		mapGen.drawM((Graphics2D)g);
		
		
		// boder
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 3, 600);
		g.fillRect(0, 0, 700, 3);
		g.fillRect(691, 0, 3, 600);
		
		//scores
		if(ballposy < 570 && totalbricks > 0){
			g.setColor(Color.white);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("Score: "+score, 590, 30);
		}
		
		
		// paddle
		g.setColor(Color.CYAN);
		g.fillRect((int)playerx, 550, 100, 10);
		
		//ball
		g.setColor(new Color(250,140,60));
		g.fillOval(ballposx	, ballposy, 20, 20);
		
		//start
		if(!gamestart && !pause && ballposy <570 && totalbricks > 0){
			g.setColor(Color.WHITE);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("To Start The Game Press Enter... ", 210,310 );
			
			g.setColor(new Color(250,140,60));
			g.setFont(new Font("serif",Font.BOLD,14));
			g.drawString("Developed by: Abubaker Azhari", 490,560 );
		}
				
		// won 
		if(totalbricks <= 0){
			pause = false;
			gamestart = false;
			ballxdir = 0;
			ballydir = 0;
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif",Font.BOLD,22));
			g.drawString("Congratulations!, You Won..  Scores: "+ score, 170,280 );
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("To Restart Press Enter... ", 250,310 );
			
			g.setColor(new Color(250,140,60));
			g.setFont(new Font("serif",Font.PLAIN,20));
			g.drawString("Developed by: Abubaker Azhari", 230,440 );
		}
		
		// ball out of the paddle
		if(ballposy > 570){
			pause = false;
			gamestart = false;
			ballxdir = 0;
			ballydir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Game Over,  Scores: "+ score, 237,280 );
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("serif",Font.BOLD,20));
			g.drawString("To Restart Press Enter... ", 245,310 );
			
			g.setColor(new Color(250,140,60));
			g.setFont(new Font("serif",Font.PLAIN,20));
			g.drawString("Developed by: Abubaker Azhari", 230,440 );
			
		}
				
		g.dispose();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		timer.start();
		
		if(gamestart){
			
			if(new Rectangle(ballposx,ballposy,20,20).intersects(new Rectangle(playerx, 550, 100, 10))){
				ballydir = -ballydir;
			}
			
			// here the condition to hide the brick was touched by paddle, and convert his value to 0;
			A: for(int i=0; i<mapGen.map.length; i++){
				for(int j=0; j<mapGen.map[0].length; j++){
					if(mapGen.map[i][j] > 0){
						int brickx = j*mapGen.brickwidth+80;
						int bricky = i*mapGen.brickheight+50;
						int brickwidth = mapGen.brickwidth;
						int brickheight = mapGen.brickheight;
						
						Rectangle maprect = new Rectangle(brickx, bricky, brickwidth, brickheight);
						Rectangle ballrect = new Rectangle(ballposx, ballposy, 20,20);
						
						if(ballrect.intersects(maprect)){
							mapGen.setBrickvalue(0,i,j);
							totalbricks --;
							score +=5;
							
							if(ballposx + 19 <= maprect.x || ballposx +1 >= maprect.x + maprect.width ){
								ballxdir =-ballxdir;
							}else{
								ballydir = -ballydir;
							}
	
							// THIS for exit from the loop
							break A;
						}
					}
					
				}
			}
			
			ballposx += ballxdir;
			ballposy += ballydir;
			
			if(ballposx < 0  || ballposx > 670){
				ballxdir =-ballxdir;
			}
			
			if(ballposy < 0){
				ballydir = -ballydir;
			}
		}
		
		repaint();
	}

	

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(playerx >590){
				playerx =595;
			}else{
				moveright();
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(playerx < 10){
				playerx =0;
			}else{
				moveleft();
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!pause){
				if(!gamestart){
					pause = true;
					gamestart = true;
					ballposx =350;
					ballposy =530;
					ballxdir = -1;
					ballydir=-2;
					playerx = 310;
					score = 0;
					totalbricks = 45;
					mapGen = new MapGenerator(5, 9);
					
					repaint();
				}
			}else{
				gamestart=!gamestart;
			}
			
		}
	}

	private void moveleft() {
		playerx-=20;
	}

	private void moveright() {
		playerx+=20;
	}


	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}
}
