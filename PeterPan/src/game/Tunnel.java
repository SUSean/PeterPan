package game;

import java.awt.Color;
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
	private int[] color = {220,220,220};
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
		this.parent.fill(this.color[0], this.color[1], this.color[2]);
		this.parent.stroke(this.color[0], this.color[1], this.color[2]);
		this.parent.rect(x,y,w,h);
		this.parent.fill(0, 134, 139);
		this.parent.setFont(font);
		this.parent.textSize(30);
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
	
	public void tunnelHighlight(){
		this.color[0] = 255;
		this.color[1] = 228;
		this.color[2] = 181;
	}
	public void tunnelColorRecover(){
		this.color[0] = 220;
		this.color[1] = 220;
		this.color[2] = 220;
	}
}
