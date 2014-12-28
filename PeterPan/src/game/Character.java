package game;

import processing.core.PApplet;
import processing.core.PImage;

public class Character extends PApplet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int STAY = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int DOWN=3;
	public static final int UP=4;
	private int chosenCharacter;
	
	private int bgheight;
	private int bgwidth;
	private GameScene parent;
	private PImage[] images;
	private int x, y, w, h;
	private int frame, movement;
	public Character(GameScene parent,PImage[] images) {
		
		this.parent = parent;
		this.images = images;
		this.chosenCharacter =parent.getChosenCharater();
		this.bgwidth=parent.width;
		this.bgheight=parent.height;
		this.x = parent.width/2;
		this.y = parent.height/2;
		this.w = images[chosenCharacter].width;
		this.h = images[chosenCharacter].height;
		
	}
	
	public void display() {
		
		switch(this.movement){
			case LEFT:
				if(x+w<0)x=bgwidth;
					this.x -= 5;
				//this.frame=(frame+1)%3;
				break;
			case RIGHT:
				if(x-w>bgwidth)x=0;
					this.x += 5;
				//this.frame=(frame+1)%3;
				break;
			case UP:
				if(y>0)this.y -=5;
				break;
			case DOWN:
				if(y<this.parent.height-this.h/2)this.y +=5;
				break;
			default:
				break;
		}
		this.parent.image(images[chosenCharacter+frame], x, y, w, h);
		
	}
	
	public void setMovement(int m){
		this.movement = m;
	}
	
	public int getX(){
		return this.x+this.w/2;
	}
	
	public int getY(){
		return this.y+this.h/2;
	}
	
	public int getW(){
		return this.w;
	}
	
	public int getH(){
		return this.h;
	}
	
	public float getHight(){
		return ((float)parent.height-(float)this.y)/(float)parent.height*10000;
	}
}
