package game;

import processing.core.PApplet;
import processing.core.PImage;


public class Coin {
	
	private PApplet parent;
	private PImage[] image;
	private int x,y;
	private int speed=3;
	private int w, h;
	private int picCount;
	
	public Coin(PApplet parent, PImage[] image, int x, int y) {
		this.picCount = 0;
		this.parent = parent;
		this.image = image;
		this.x = x;
		this.y = y;
		this.w = image[0].width;
		this.h = image[0].height;
	}
	

	public void display(){
		picCount++;
		picCount%=4;
		if(y>0){
			this.x-=speed;
			this.y=(int)(0.0025*(x-500)*(x-500));
			this.parent.image(this.image[0+picCount%4], this.x, this.y, this.w, this.h);
		}
		else y++;
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	
}