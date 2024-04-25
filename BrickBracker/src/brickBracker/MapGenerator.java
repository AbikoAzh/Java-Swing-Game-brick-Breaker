package brickBracker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class MapGenerator {
	// array: that conten the bricks
	public int map[][];
	
	public int brickwidth;
	public int brickheight;
	
	public int brickNum;
	
	// CONSTRUCTOR: hold parameter to dertermind which width and height
	public MapGenerator(int row, int col){
		
//		int brickNum= row*col;
		
		map = new int[row][col];
		
		for (int i=0; i< map.length ; i++){
			for(int j=0; j<map[1].length ; j++){
				//here we fill the array by value "1" when we intialled it first, this say that the  brick shown
				// to help us, which bricks that fired
				map[i][j]= 1;
			}
		}
		
		brickwidth = 540/col;
		brickheight = 150/row;
	}
	
	
	public void drawM(Graphics2D g){
		
		for (int i=0; i< map.length ; i++){
			for(int j=0; j<map[1].length ; j++){
				
				if(map[i][j]>0){
					g.setColor(Color.white);
					g.fillRect(j*brickwidth +80 , i*brickheight +50 , brickwidth, brickheight);


					g.setStroke(new BasicStroke(3));
					g.setColor(new Color(16,23,16));
					g.drawRect(j*brickwidth +80 , i*brickheight +50 , brickwidth, brickheight);
				}
			}
		}
	}
	
	public void setBrickvalue(int value, int row, int col){
		map[row][col]=value;
	}
	
}
