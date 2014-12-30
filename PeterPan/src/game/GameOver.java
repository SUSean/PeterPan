package game;

import processing.core.PApplet;
import processing.core.PImage;
import server.Client;

public class GameOver extends PApplet{
	PImage background;
	PImage textGameOver;
	String[] topTenName;
	int[] topTenScore;
	private Client client;
	public GameOver(Client client){
		this.client=client;
		//load background
		background = new PImage();
		this.background = loadImage(this.getClass().getResource("/res/Background/background_1.jpg").getPath());
	
		//load "Game Over" text
		textGameOver = new PImage();
		this.textGameOver = loadImage(this.getClass().getResource("/res/Shop/textGameOver.png").getPath());
		
		//set topTenName 
		topTenName = new String[11];	
		for(int i=1;i<11;i++){
			topTenName[i]=this.client.topTen[i-1];
		}
		//set topTenScore
		//topTenScore = new int[11];

	}
	public void setup(){
		size(500, 700);
	}
	public void draw(){
		
		//set background and "Game Over" text images
		image(this.background, 0, 0);
		image(this.textGameOver, 40, 20);
		
		//list top ten score
		for (int i=1; i < 11; i++){
			text(topTenName[i], 20, 160 + 45*(i-1));
			textSize(35);
		}
	}
}
