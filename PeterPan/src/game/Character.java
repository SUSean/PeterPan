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
	public static final int[] tunnel={80,250,420};
	private int chosenCharacter;
	
	private int bgheight;
	private int bgwidth;
	private int tunnelNum=1;
	private int fireFrame=0;
	private GameScene parent;
	private PImage[] images;
	private PImage chooseMessage;
	private PImage[] fire;
	private int x, y, w, h,fw,fh;
	private int frame, movement;
	public Character(GameScene parent,PImage[] images) {
		
		this.parent = parent;
		this.images = images;
		this.chooseMessage = loadImage(this.getClass().getResource("/res/ChooseMessage.png").getPath());
		this.chosenCharacter =parent.getChosenCharater();
		this.fire=new PImage[3];
		this.fire[0] = loadImage(this.getClass().getResource("/res/Fire/fire1.png").getPath());
		this.fire[1] = loadImage(this.getClass().getResource("/res/Fire/fire2.png").getPath());
		this.fire[2] = loadImage(this.getClass().getResource("/res/Fire/fire3.png").getPath());
		this.bgwidth=parent.width;
		this.bgheight=parent.height;
		this.w = images[chosenCharacter].width;
		this.h = images[chosenCharacter].height;
		this.x = parent.width/2-w/2;
		this.y = parent.height/2;
		this.fw = fire[0].width;
		this.fh = fire[0].height;
		
	}
	
	public void display() {
		
		switch(this.movement){
			case LEFT:
				if(x+w*1/3>0)
					this.x -= 5;
				//this.frame=(frame+1)%3;
				break;
			case RIGHT:
				if(x+w*2/3<bgwidth)
					this.x += 5;
				//this.frame=(frame+1)%3;
				break;
			case UP:
				if(y>0)this.y -=5;
				break;
			case DOWN:
				if(y<this.parent.height-this.h)this.y +=5;
				break;
			default:
				break;
		}
		this.parent.image(images[chosenCharacter+frame], x, y, w, h);
		
	}
	public void tunnelModeDisplay(){
		
		switch(this.movement){
		case LEFT:
			if(tunnelNum>0)
				this.x = tunnel[--tunnelNum]-w/2;
			//this.frame=(frame+1)%3;
			break;
		case RIGHT:
			if(tunnelNum<2)
				this.x = tunnel[++tunnelNum]-w/2;
			//this.frame=(frame+1)%3;
			break;
		case UP:
			if(y>0)this.y -=5;
			break;
		default:
			break;
	}
	this.parent.image(images[chosenCharacter+frame], x, y, w, h);
	}
	public void setMovement(int m){
		this.movement = m;
	}
	
	public void moveToTunnelStartMode(){
		if(x+w/2<this.bgwidth/2)x+=5;
		else if(x+w/2>this.bgwidth/2)x-=5;
		
		if(this.y>this.bgheight/2)y-=5;
		else if(this.y<this.bgheight/2)y+=5;
		
		this.parent.image(chooseMessage,50,100,400,100);
		this.parent.image(images[chosenCharacter+frame], x, y, w, h);
	}
	
	public void levelUpAnimation(){
		
		if(y<bgheight-320)y+=5;
		if(x+w/2<bgwidth/2)x+=5;
		if(x+w/2>bgwidth/2)x-=5;
		this.parent.image(images[chosenCharacter+frame], x, y, w, h);
		this.parent.image(fire[fireFrame++],x+w/2,y+h,fw,fh);
		if(fireFrame==2)fireFrame=0;
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
	
	public void setX(int x){
		this.x=x;
	}
}
