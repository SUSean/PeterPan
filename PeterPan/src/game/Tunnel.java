package game;

import java.awt.Font;

import javax.swing.JLabel;

import processing.core.PApplet;
import processing.core.PFont;


public class Tunnel {
	public String string;
	private PApplet parent;
	private GameScene gamescene;
	float x,y,w,h;
	private Font font;
	Tunnel(PApplet parent,GameScene gameScene, String word,int x,int y){
		this.parent=parent;
		this.gamescene=gameScene;
		this.string=word;
		this.x=x;
		this.y=y;
		this.w=160;
		this.h=30;
		font = new Font(Font.DIALOG_INPUT, Font.BOLD, 40);
	}
	public void display(){
		this.y+=(float)0.2;
		if(y==gamescene.displayHeight){
			y=0;
			gamescene.tunnelMode=false;
		}
		this.parent.fill(0, 0, 0);
		this.parent.rect(x,y,w,h);
		this.parent.fill(255,255,255);
		this.parent.setFont(font);
		this.parent.textSize(32);
		this.parent.text(this.string , x, y+25);
		
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
