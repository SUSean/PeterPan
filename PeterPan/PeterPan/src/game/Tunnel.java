package game;

import javax.swing.JLabel;

import processing.core.PApplet;

public class Tunnel {
	private String string;
	private PApplet parent;
	private GameScene gamescene;
	float x,y,w,h;
	Tunnel(PApplet parent,GameScene gameScene,int x,int y){
		this.parent=parent;
		this.gamescene=gameScene;
		this.string="happy";
		this.x=x;
		this.y=y;
		this.w=160;
		this.h=30;
		
	}
	public void display(){
		this.y+=(float)0.2;
		this.parent.fill(0, 102, 153);
		this.parent.textSize(32);
		this.parent.text(string,x,y,w,h);
		if(y==gamescene.displayHeight){
			y=0;
			gamescene.tunnelMode=false;
		}
		this.parent.rect(x,y,w,h);
	}
	public float getX(){
		return this.x;
	}
	public float getY(){
		return this.y;
	}
	public void setY(float y){
		this.y=y;
	}
}
